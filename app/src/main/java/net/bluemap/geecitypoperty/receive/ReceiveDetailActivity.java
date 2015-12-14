package net.bluemap.geecitypoperty.receive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.bluemap.geecitypoperty.receive.model.ReceiveBean;
import net.bluemap.geecitypoperty.receive.network.GetReceiveDetailHPI;
import net.bluemap.geecitypoperty.task.RelevantTaskListActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import hz.toollib.util.IntentUtil;
import hz.toollib.widget.OnLoadingView;

public class ReceiveDetailActivity extends AppCompatActivity {

    //UIs
    @ViewInject(id = R.id.onLoadingView)
    OnLoadingView onLoadingView;
    @ViewInject(id = R.id.ard_tv_title)
    TextView tvTitle;
    @ViewInject(id = R.id.ard_tv_receiverstate)
    TextView tvReceiver;
    @ViewInject(id = R.id.ard_tv_content)
    TextView tvContent;
    @ViewInject(id = R.id.ard_rv_photos)
    RecyclerView rvPhotos;
    @ViewInject(id = R.id.ard_tv_photo)
    TextView tvPhoto;
    @ViewInject(id = R.id.ard_tv_contact)
    TextView tvContact;
    @ViewInject(id = R.id.ard_tv_tel)
    TextView tvTel;
    @ViewInject(id = R.id.ard_tv_reaction)
    TextView tvReaction;
    @ViewInject(id = R.id.ard_tv_twos)
    TextView tvTwos;
    @ViewInject(id = R.id.ard_tv_orderTime)
    TextView tvOrderTime;
    @ViewInject(id = R.id.ard_tv_accepter)
    TextView tvAccepter;
    @ViewInject(id = R.id.ard_tv_acceptecomp)
    TextView tvAccepteComp;
    @ViewInject(id = R.id.v_divider)
    View vDivider;
    //接口
    GetReceiveDetailHPI getReceiveDetailHPI;

    PhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_detail);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("接待处理");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onLoadingView.setOnReloadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        //初始化图片列表
        rvPhotos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhotos.setLayoutManager(llm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        onLoadingView.showOnloading();
        getReceiveDetailHPI = new GetReceiveDetailHPI(this);
        getReceiveDetailHPI.setReceiveId(getIntent().getStringExtra("id"));
        getReceiveDetailHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillData();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoadingView.showError();
                DialogUtil.getInstance(ReceiveDetailActivity.this).showShortToast(errMessage);
            }
        });
        getReceiveDetailHPI.doConnectInBackground();
    }

    private void fillData(){
        final ReceiveBean receive = getReceiveDetailHPI.getReceive();
        tvTitle.setText("["+receive.getSource()+"]["+receive.getType()+"]"+receive.getTime());
        //？？？？
        tvReceiver.setText("接待人状态:");
        tvContent.setText(receive.getContent());
        tvContact.setText("报事对象：" + receive.getAddress());
        tvTel.setText("报  事  人："+receive.getContact()+"\n联系电话："+receive.getTel());
        tvReaction.setText("客户反应：" + receive.getReaction());
        tvTwos.setVisibility(receive.getTwos().equals("0") ? View.GONE : View.VISIBLE);
        vDivider.setVisibility(receive.getTwos().equals("0") ? View.GONE : View.VISIBLE);
        tvOrderTime.setText("预约时间：" + receive.getOrderTime());
        //分为两个textview调整间距
        tvAccepter.setText("受  理  人："+receive.getAccepter());
        tvAccepteComp.setText("受理公司："+receive.getAccepterCom());
        //拨打电话
        tvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil iu = new IntentUtil(ReceiveDetailActivity.this);
                iu.makePhoneCall(receive.getTel());
            }
        });

        // 图片
        if(receive.getImages().size() == 0){
            tvPhoto.setText("相关图片：无");
            rvPhotos.setVisibility(View.GONE);
        }else{
            mPhotoAdapter = new PhotoAdapter();
            mPhotoAdapter.getPhotos().addAll(receive.getImages());
            rvPhotos.setAdapter(mPhotoAdapter);
        }

        onLoadingView.hide();
    }

    public void relevantTask(View view){
        Intent intent = new Intent(this, RelevantTaskListActivity.class);
        intent.putExtra("receiveId", getReceiveDetailHPI.getReceive().getId());
        LoginInfo loginInfo = ShareUtil.getInstance(this).getLoginInfo();
        intent.putExtra("closed", getReceiveDetailHPI.getReceive().getState().equals("已关闭"));
        intent.putExtra("canAdd", getReceiveDetailHPI.getReceive().getAccepter().equals(loginInfo.getUserName()));

        startActivity(intent);
    }

}
