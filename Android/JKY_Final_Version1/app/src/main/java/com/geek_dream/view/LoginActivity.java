package com.geek_dream.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.example.huangzhiyuan.jky_final_version1.R;

import com.geek_dream.operation.Operation;
import com.geek_dream.player.MainActivity;


public class LoginActivity extends Activity {
    Button loginBtn;
    EditText etusername;
    EditText etpassword;
    String username;
    String password;

    //    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        etusername=(EditText) findViewById(R.id.etusername);
        etpassword=(EditText) findViewById(R.id.etpassword);

        loginBtn=(Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etusername.getText().toString().trim();
                password=etpassword.getText().toString().trim();

                if (username==null||"".equals(username))
                {
                    etusername.requestFocus();
                    etusername.setError("对不起，用户名不能为空");
                    return;
                }


                if (password==null||password.length()<=0)
                {
                    etpassword.requestFocus();
                    etpassword.setError("对不起，密码不能为空");
                    return;
                }

                new Thread(new Runnable() {
                    public void run() {
                        Operation operaton=new Operation();
                        String result=operaton.login("Login", username, password);
                        Message msg=new Message();
                        msg.obj=result;
                        handler.sendMessage(msg);
                    }
                }).start();

            }

            Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {

                    try{

                        String string=(String) msg.obj;
                        Toast.makeText(LoginActivity.this, string, Toast.LENGTH_LONG).show();
                        super.handleMessage(msg);
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            };
        });


    }













}
