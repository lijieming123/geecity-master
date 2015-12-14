package net.bluemap.geecitypoperty.receive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.KeyValueAdapter;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.bluemap.geecitypoperty.receive.network.AddReceiveHPI;
import net.bluemap.geecitypoperty.receive.network.GetReceiveReactionHPI;
import net.bluemap.geecitypoperty.receive.network.GetReceiveTypesHPI;
import net.bluemap.geecitypoperty.room.RoomSelectActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class AddReceiveActivity extends AppCompatActivity {

    @ViewInject(id = R.id.aar_ll_room)
    LinearLayout llRoom;
    @ViewInject(id = R.id.aar_tv_room)
    TextView tvRoom;

    @ViewInject(id = R.id.aar_ll_complaint)
    LinearLayout llComplaint;
    @ViewInject(id = R.id.aar_tv_complaint)
    TextView tvComplaint;
    @ViewInject(id = R.id.aar_et_complaint)
    EditText etComplaint;

    @ViewInject(id = R.id.aar_et_contact)
    EditText etContact;
    @ViewInject(id = R.id.aar_et_tel)
    EditText etTel;

    @ViewInject(id = R.id.aar_spn_reaction)
    Spinner spnReaction;
    @ViewInject(id = R.id.aar_spn_type)
    Spinner spnType;

    @ViewInject(id = R.id.aar_cb_two)
    CheckBox cbTwo;
    @ViewInject(id = R.id.aar_dtp_time)
    DateTimePicker dtpTime;
    @ViewInject(id = R.id.aar_et_content)
    EditText etContent;

    @ViewInject(id = R.id.aar_rv_photo)
    RecyclerView rvPhoto;

    //是否选择房间的标记
    private boolean mRoomFlag = false;
    private boolean mComplintFlag = false;

    //接口
    GetReceiveTypesHPI getReceiveTypesHPI;
    GetReceiveReactionHPI getReceiveReactionHPI;

    //提交接口
    AddReceiveHPI addReceiveHPI;

    //图片adapter
    PhotoAdapter photoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receive);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新增接待");
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);

        addReceiveHPI = new AddReceiveHPI(this);

        //初始化图片选择器
        rvPhoto.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhoto.setLayoutManager(llm);
        photoAdapter = new PhotoAdapter();
        photoAdapter.setEditable(true);
        rvPhoto.setAdapter(photoAdapter);
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData(){
        getReceiveReactionHPI = new GetReceiveReactionHPI(this);
        getReceiveReactionHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                KeyValueAdapter adapter = new KeyValueAdapter();
                adapter.getList().addAll(getReceiveReactionHPI.getList());
                spnReaction.setAdapter(adapter);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                //弹出提示
                DialogUtil.getInstance(AddReceiveActivity.this).showShortToast(errMessage);
            }
        });
        getReceiveReactionHPI.doConnectInBackground();

        getReceiveTypesHPI = new GetReceiveTypesHPI(this);
        getReceiveTypesHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                KeyValueAdapter adapter = new KeyValueAdapter();
                adapter.getList().addAll(getReceiveTypesHPI.getList());
                spnType.setAdapter(adapter);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                //弹出提示
                DialogUtil.getInstance(AddReceiveActivity.this).showShortToast(errMessage);
            }
        });
        getReceiveTypesHPI.doConnectInBackground();

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
     * 选择房间点击事件
     * @param view
     */
    public void selectRoom(View view){
        Intent intent = new Intent(this, RoomSelectActivity.class);
        intent.putExtra("isFilter", true);
        int requestCode;
        if(view.getId() == R.id.aar_ll_room){
            requestCode = 1;
        }else{
            requestCode = 2;
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                addReceiveHPI.setCourtId(data.getStringExtra("court"));
                addReceiveHPI.setBuildingId(data.getStringExtra("building"));
                addReceiveHPI.setUnitId(data.getStringExtra("unit"));
                addReceiveHPI.setRoomId(data.getStringExtra("room"));
                addReceiveHPI.setRoomName(data.getStringExtra("roomFullName"));
                tvRoom.setText(data.getStringExtra("roomFullName"));
                etContact.setText(data.getStringExtra("contact"));
                etTel.setText(data.getStringExtra("tel"));
                mRoomFlag = true;
            }else if(requestCode == 2){
                addReceiveHPI.setComplintCourtId(data.getStringExtra("court"));
                addReceiveHPI.setComplintBuildingId(data.getStringExtra("building"));
                addReceiveHPI.setComplintUnitId(data.getStringExtra("unit"));
                addReceiveHPI.setComplintRoomId(data.getStringExtra("room"));
                addReceiveHPI.setComplintRoomName(data.getStringExtra("roomFullName"));
                tvComplaint.setText(data.getStringExtra("roomFullName"));
                etComplaint.setText(data.getStringExtra("roomFullName"));
                mComplintFlag = true;
            }

            //相机回调
            else if(requestCode == 3){
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
     * 提交
     * @param view
     */
    public void submit(View view){
        final DialogUtil dialogUtil = DialogUtil.getInstance(this);
        //校验
        if(!mRoomFlag){
            dialogUtil.showCommitDialog("提示","请选择房间");
            return;
        }
        if(etContact.getText().toString().equals("")){
            dialogUtil.showCommitDialog("提示","请填写联系人");
            return;
        }
        if(etTel.getText().toString().equals("")){
            dialogUtil.showCommitDialog("提示","请填写联系电话");
            return;
        }
        if(etComplaint.getText().toString().equals("")){
            dialogUtil.showCommitDialog("提示","请填投诉对象");
            return;
        }
        if(etContent.getText().toString().equals("")){
            dialogUtil.showCommitDialog("提示","请填写接待内容");
            return;
        }
        dialogUtil.showProgressDialog("", "正在提交", false);
        addReceiveHPI.setUserName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        addReceiveHPI.setContact(etContact.getText().toString());
        addReceiveHPI.setTel(etTel.getText().toString());
        addReceiveHPI.setContent(etContent.getText().toString());
        KeyValueBean reaction = (KeyValueBean) spnReaction.getSelectedItem();
        KeyValueBean receiveType = (KeyValueBean) spnType.getSelectedItem();
        addReceiveHPI.setReaction(reaction.getKey());
        addReceiveHPI.setType(receiveType.getKey());
        addReceiveHPI.setTwos(cbTwo.isChecked() ? "1" : "0");
        addReceiveHPI.setTime(dtpTime.getDateText("yyyy-MM-dd HH:mm:ss"));
        addReceiveHPI.setImages(photoAdapter.getPhotos());
        addReceiveHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast("新增接待成功");
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast(errMessage);
            }
        });
        addReceiveHPI.doConnectInBackground();
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
