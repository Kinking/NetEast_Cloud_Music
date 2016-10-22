package com.geek_dream.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.huangzhiyuan.jky_final_version1.R;


public class MpriorActivity extends Activity {
    //第一个界面，
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mprior);
        //线程控制自动跳转，睡眠两秒钟
        Thread thread = new Thread(){
            @Override
            public void run() {//run表示方法
                // TODO Auto-generated method stub
                super.run();
                //上面的super报错点击修改自动弹出try...catch
                try {
                    Thread.sleep(2000);//睡眠两秒钟
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //intent相当于跳转操作
                Intent i = new Intent();
                i.setClass(MpriorActivity.this, MsecondActivity.class);
                startActivity(i);
                finish();//销毁操作
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
