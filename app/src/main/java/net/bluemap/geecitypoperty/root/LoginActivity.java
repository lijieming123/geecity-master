package net.bluemap.geecitypoperty.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.root.network.LoginHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 登录activity
 */
public class LoginActivity extends AppCompatActivity {

    //接口
    LoginHPI loginHPI;

    /**UIs*/
    @ViewInject(id = R.id.al_et_username) EditText etUserName;
    @ViewInject(id = R.id.al_et_password) EditText etPassword;
    @ViewInject(id = R.id.al_btn_login,click = "onLoginClick") Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FinalActivity.initInjectedView(this);

        initView();
    }

    private void initView(){
        LoginInfo li = ShareUtil.getInstance(this).getLoginInfo();
        etUserName.setText(li.getUserName());
        etPassword.setText(li.getPassword());
    }

    public void onLoginClick(View view){
        if(etUserName.getText().toString().equals("")
                ||etPassword.getText().toString().equals("")){
            DialogUtil.getInstance(this).showCommitDialog("错误","请填写用户名或密码");
            return;
        }
        loginHPI = new LoginHPI(this);
        loginHPI.setUsername(etUserName.getText().toString());
        loginHPI.setPassword(etPassword.getText().toString());
        loginHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(LoginActivity.this).dismissProgressDialog();
                //保存用户信息
                ShareUtil.getInstance(LoginActivity.this).saveLoginInfo(loginHPI.getLoginInfo());
                //跳转到主页面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(LoginActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(LoginActivity.this).showCommitDialog("错误", errMessage);
            }
        });
        DialogUtil.getInstance(this).showProgressDialog("", "正在登录", false);
        loginHPI.doConnectInBackground();
    }

    /**
     * 点击了设置
     * @param view
     */
    public void onSetting(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
