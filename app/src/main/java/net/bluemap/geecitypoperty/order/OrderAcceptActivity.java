package net.bluemap.geecitypoperty.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.order.model.DepartAdapter;
import net.bluemap.geecitypoperty.order.model.DepartBean;
import net.bluemap.geecitypoperty.order.model.EmployeeAdapter;
import net.bluemap.geecitypoperty.order.model.EmployeeBean;
import net.bluemap.geecitypoperty.order.network.GetDepartmentHPI;
import net.bluemap.geecitypoperty.order.network.GetEmployeeHPI;
import net.bluemap.geecitypoperty.order.network.ScrambleOrderHPI;
import net.bluemap.geecitypoperty.order.network.TaskOrderHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 派单受理页
 * Created by LiuPeng on 2015/8/10.
 */
public class OrderAcceptActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;

    //抢单/派单RadioButton
    @ViewInject(id = R.id.aoa_rb_task) RadioButton rbTask;
    //@ViewInject(id = R.id.aoa_rb_scramble) RadioButton rbScramble;
    @ViewInject(id = R.id.aoa_ll_task) LinearLayout llTask;
    @ViewInject(id = R.id.aoa_spn_dept) Spinner spnDepart;
    @ViewInject(id = R.id.aoa_rv_employee) RecyclerView rvEmployee;

    //时间选择器
    @ViewInject(id = R.id.aoa_dtp_expect) DateTimePicker dtpExpect;

    //提交按钮
    @ViewInject(id = R.id.aoa_btn_commit, click = "commit") Button btnCommit;

    //要求完工时间
    @ViewInject(id = R.id.aoa_dtp_expect) DateTimePicker etExpectTime;

    //接口
    //获取部门
    GetDepartmentHPI getDepartmentHPI;
    //获取人员
    GetEmployeeHPI getEmployeeHPI;

    //派单
    TaskOrderHPI taskOrderHPI;
    //抢单
    ScrambleOrderHPI scrambleOrderHPI;


    //部门和人员数据
    List<DepartBean> departs = new ArrayList<>();
    List<EmployeeBean> employee = new ArrayList<>();

    RecyclerView.Adapter employeeAdapter;
    DepartAdapter departAdapter;

    //通用的组件
    LoginInfo li;
    DialogUtil dialogUtil;

    //传入参数
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accept);

        //接收参数
        orderId = getIntent().getStringExtra("id");

        FinalActivity.initInjectedView(this);

        mToolbar.setTitle("派单受理");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        li = ShareUtil.getInstance(this).getLoginInfo();
        dialogUtil = DialogUtil.getInstance(this);

        rbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llTask.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        employeeAdapter = new EmployeeAdapter(this,employee);
        rvEmployee.setLayoutManager(new LinearLayoutManager(this));
        rvEmployee.setHasFixedSize(true);
        rvEmployee.setAdapter(employeeAdapter);

        departAdapter = new DepartAdapter(this,departs);
        spnDepart.setAdapter(departAdapter);
        //开始时加载部门数据
        getDepartmentHPI = new GetDepartmentHPI(this);
        getDepartmentHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                departs.clear();
                departs.addAll(getDepartmentHPI.getDepartments());
                departAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(OrderAcceptActivity.this).showShortToast(errMessage);
            }
        });
        getDepartmentHPI.doConnectInBackground();

        getEmployeeHPI = new GetEmployeeHPI(this);
        getEmployeeHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                employee.clear();
                employee.addAll(getEmployeeHPI.getEmployee());
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(OrderAcceptActivity.this).showShortToast(errMessage);
            }
        });

        spnDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DepartBean db = (DepartBean)parent.getItemAtPosition(position);
                getEmployeeHPI.setDepartId(db.getId());
                getEmployeeHPI.doConnectInBackground();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取选择的员工id字符串
     */
    private String getSelectEmployee(){
        String select = "";
        for(EmployeeBean eb : employee){
            if(eb.isSelect()){
                select += eb.getId() + ",";
            }
        }
        if(select.length()>0){
            select = select.substring(0,select.length()-1);
        }
        return select;
    }

    /**
     * 提交时间
     * @param view View
     */
    public void commit(View view){
        //决定调用哪个接口
        HttpPostAPI httpPostAPI;
        if(rbTask.isChecked()){     //派单模式，需验证是否选择人员
            String selectE = getSelectEmployee();
            if(selectE.equals("")){     //员工不能为空
                dialogUtil.showCommitDialog("派单","请先选择处理人员");
                return;
            }
            taskOrderHPI = new TaskOrderHPI(this);
            taskOrderHPI.setUserId(li.getId());
            taskOrderHPI.setEmployee(selectE);
            taskOrderHPI.setOrderId(orderId);
            taskOrderHPI.setExpectTime(dtpExpect.getDateText("yyyy-MM-dd HH:mm:ss"));
            httpPostAPI = taskOrderHPI;
        }else{
            scrambleOrderHPI = new ScrambleOrderHPI(this);
            scrambleOrderHPI.setUserId(li.getId());
            scrambleOrderHPI.setOrderId(orderId);
            scrambleOrderHPI.setExpectTime(dtpExpect.getDateText("yyyy-MM-dd HH:mm:ss"));
            httpPostAPI = scrambleOrderHPI;
        }

        httpPostAPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialogWithFinish("","操作成功");
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("错误",errMessage);
            }
        });
        dialogUtil.showProgressDialog("","正在提交...",false);
        httpPostAPI.doConnectInBackground();
    }

}
