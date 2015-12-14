package net.bluemap.geecitypoperty.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.bluemap.geecitypoperty.task.network.AddProgressHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddProgressActivity extends AppCompatActivity {

    //UIs
    @ViewInject(id = R.id.aap_rg_state)
    RadioGroup rgState;
    @ViewInject(id = R.id.aap_et_content)
    EditText etContent;
    @ViewInject(id = R.id.aap_et_remark1)
    EditText etRemark1;
    @ViewInject(id = R.id.aap_et_remark2)
    EditText etRemark2;
    @ViewInject(id = R.id.aap_et_remark3)
    EditText etRemark3;

    @ViewInject(id = R.id.aap_rv_photo)
    RecyclerView rvPhoto;

    private String receiveId;
    private String taskId;

    //图片adapter
    PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress);

        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新增进展");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskId = getIntent().getStringExtra("id");
        receiveId = getIntent().getStringExtra("receiveId");

        //初始化图片选择器
        rvPhoto.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhoto.setLayoutManager(llm);
        photoAdapter = new PhotoAdapter();
        photoAdapter.setEditable(true);
        rvPhoto.setAdapter(photoAdapter);
    }

    /**
     * 提交操作
     * @param view
     */
    public void onSubmit(View view){
        final DialogUtil dialogUtil = DialogUtil.getInstance(this);
        dialogUtil.showProgressDialog("", "正在提交", false);
        AddProgressHPI addProgressHPI = new AddProgressHPI(this);
        addProgressHPI.setTaskId(taskId);
        addProgressHPI.setCreateName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        addProgressHPI.setReceiveId(receiveId);
        addProgressHPI.setContent(etContent.getText().toString());
        addProgressHPI.setState(getState());
        addProgressHPI.setRemark1(etRemark1.getText().toString());
        addProgressHPI.setRemark2(etRemark2.getText().toString());
        addProgressHPI.setRemark3(etRemark3.getText().toString());
        addProgressHPI.setImages(photoAdapter.getPhotos());

        addProgressHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast("提交成功");
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("", errMessage);
            }
        });
        addProgressHPI.doConnectInBackground();
    }

    /**
     * 获取选择的状态
     * @return
     */
    private String getState(){
        switch (rgState.getCheckedRadioButtonId()){
            case R.id.aap_rb_1:
                return "处理中";
            case R.id.aap_rb_2:
                return "已完成";
            case R.id.aap_rb_3:
                return "暂不处理";
            default:
                return "处理中";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //相机回调
            if(requestCode == 3){
                List<String> images = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if(photoAdapter.getItemCount()+images.size()>5){
                    DialogUtil.getInstance(this).showCommitDialog("","最多上传5张图片");
                    return;
                }
                photoAdapter.getPhotos().addAll(images);
                photoAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 点击拍照
     */
    public void onPhoto(View view){
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,5);
        startActivityForResult(intent, 3);
    }
}
