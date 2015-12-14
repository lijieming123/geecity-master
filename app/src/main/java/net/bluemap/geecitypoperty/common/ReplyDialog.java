package net.bluemap.geecitypoperty.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;

/**
 * 回复对话框，用于回复消息、填写相关信息时显示
 * Created by Liu Peng on 2015/7/26.
 */
public class ReplyDialog extends Dialog{

    private TextView tvTitle;
    private EditText etContent;
    private ImageButton ibtnSubmit;
    private ImageButton ibtnCancel;

    public ReplyDialog(Context context) {
        super(context);
        initDialog(context);
    }

    public ReplyDialog(Context context, int theme) {
        super(context, theme);
        initDialog(context);
    }

    protected ReplyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog(context);
    }

    private void initDialog(Context context){
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_reply);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = display.getWidth(); //设置宽度
        //lp.height = (int)(display.getHeight() * 0.6); //设置高度
        this.getWindow().setAttributes(lp);
        tvTitle = (TextView) findViewById(R.id.dr_tv_title);
        etContent = (EditText) findViewById(R.id.dr_et_content);
        ibtnCancel = (ImageButton) findViewById(R.id.dr_ibtn_cancel);
        ibtnSubmit = (ImageButton) findViewById(R.id.dr_ibtn_submit);

        ibtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setHint(String hint) {
        etContent.setHint(hint);
    }

    public void setOnSubmitListener(View.OnClickListener listener) {
        ibtnSubmit.setOnClickListener(listener);
    }
}
