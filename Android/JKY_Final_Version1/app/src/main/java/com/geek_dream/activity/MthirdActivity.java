package com.geek_dream.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.view.LoginActivity;
import com.geek_dream.view.RegisterActivity;

public class MthirdActivity extends Activity {
    TextView tv_denglu;
    TextView tv_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mthird);
        // 查找控件
        tv_denglu = (TextView) findViewById(R.id.btn_tel);
        tv_register = (TextView) findViewById(R.id.btn_register);


        // 为前面两个textview添加点击事件
        tv_denglu.setOnClickListener(new View.OnClickListener() {
            //点登录,跳到专门的登录界面Login
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MthirdActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                // TODO Auto-generated method stub

            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            //点注册,跳到专门的注册界面Register

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(MthirdActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
