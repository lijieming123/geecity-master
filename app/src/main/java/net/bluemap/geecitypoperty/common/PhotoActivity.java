package net.bluemap.geecitypoperty.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import net.bluemap.geecitypoperty.R;
import net.tsz.afinal.FinalBitmap;

/**
 * 显示大图页，需传image参数=图片url
 */
public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("查看图片");
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView ivPhoto = (ImageView) findViewById(R.id.ap_iv_photo);
        FinalBitmap fb = FinalBitmap.create(this);
        fb.display(ivPhoto, getIntent().getStringExtra("image"));
    }
}
