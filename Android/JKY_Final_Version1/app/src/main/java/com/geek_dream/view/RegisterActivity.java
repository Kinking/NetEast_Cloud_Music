package com.geek_dream.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View;

import com.example.huangzhiyuan.jky_final_version1.R;
import com.geek_dream.activity.ExDialog;
import com.geek_dream.activity.MthirdActivity;
import com.geek_dream.bean.User;
import com.geek_dream.json.WriteJson;
import com.geek_dream.operation.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    //参考何亚芝MregisterActivity的逻辑


    EditText et_user;  //用户名
    EditText et_pwd1;  //密码
    EditText et_pwd2;  //确认密码
    EditText et_mail;  //邮箱
    RadioButton ckman;    //男按钮
    RadioButton ckwoman;  //女按钮
    EditText etage;       //年龄按钮
    Button submit;  //提交按钮



//    String str;
    String jsonString=null;
//    ProgressDialog dialog;
//    private static final int REQUEST_EX = 1;



    String username=null;//用户名
    String password=null;//密码
    String email=null;//邮箱
    String sex=null;//性别
    String age=null;//年龄


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /***
         * 从Honeycomb SDK（3.0）开始，google不再允许网络请求（HTTP、Socket）等相关操作直接在Main Thread类中，
         * 其实本来就不应该这样做，直接在UI线程进行网络操作，会阻塞UI、用户体验相当bad！即便google不禁止，一般情况下我们也不会这么做吧~
         * 所以，也就是说，在Honeycomb SDK（3.0）以下的版本，你还可以继续在Main Thread里这样做，在3.0以上，就不行了
         * 因此加上以下代码
         */

        ////////////////////////////////////////////////////////////////////////////////////////
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads().detectDiskWrites().detectNetwork()
//                .penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                .penaltyLog().penaltyDeath().build());
        ////////////////////////////////////////////////////////////////////////////////////////

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //绑定控件
        submit=(Button) findViewById(R.id.submit);
        et_user=(EditText) findViewById(R.id.etusername);  //用户名
        et_pwd1=(EditText) findViewById(R.id.etpassword);  //密码
        et_pwd2=(EditText) findViewById(R.id.et_pw2);  //密码
        et_mail=(EditText) findViewById(R.id.et_mail);  //邮箱
        ckman=(RadioButton) findViewById(R.id.ckman);         //性别男
        ckwoman=(RadioButton) findViewById(R.id.ckwoman);     //性别女
        etage=(EditText) findViewById(R.id.etage);            //年龄

//
//        dialog=new ProgressDialog(RegisterActivity.this);
//        dialog.setTitle("上传数据中");
//        dialog.setMessage("请稍等...");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //用户名设置
                username=et_user.getText().toString().trim();
                if (username==null||"".equals(username))
                {
                    et_user.requestFocus();
                    et_user.setError("用户名不能为空");
                    return ;
                }

                //密码设置&一致验证
                password=et_pwd1.getText().toString().trim();
                String password2 = et_pwd2.getText().toString().trim();
                if (!password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    return;
                }

                //邮箱
                email=et_mail.getText().toString().trim();
                if (email==null||"".equals(email))
                {
                    et_mail.requestFocus();
                    et_mail.setError("邮箱不能为空");
                    return ;
                }

                //年龄设置
                age=etage.getText().toString().trim();
                if (age==null||"".equals(age))
                {
                    etage.requestFocus();
                    etage.setError("年龄不能为空");
                    return ;
                }

                //性别筛选设置
                if (ckman.isChecked()) {
                    sex="male";
                }
                else {
                    sex="female";
                }

                if(username!=null&&password!=null&&email!=null&&age!=null&&sex!=null){


                    try {
                        User user=new User(username, password,email, sex, age);
                        //构造一个user对象
                        List<User> list=new ArrayList<User>();
                        list.add(user);

                        WriteJson writeJson=new WriteJson();
                        //将user对象写出json形式字符串
                        jsonString= writeJson.getJsonData(list);

                        System.out.println(jsonString);

                        new Thread(new Runnable() {
                            public void run() {
                                Operation operaton=new Operation();
                                String result= operaton.UpData("Register", jsonString);
                                Message msg=new Message();
                                msg.obj=result;
//                            handler.sendMessage(msg);
                                handler1.sendMessage(msg);
                            }
                        }).start();

                    }catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

//                    User user=new User(username, password,email, sex, age);
//                    //构造一个user对象
//                    List<User> list=new ArrayList<User>();
//                    list.add(user);
//
//                    WriteJson writeJson=new WriteJson();
//                    //将user对象写出json形式字符串
//                    jsonString= writeJson.getJsonData(list);
//
//                    System.out.println(jsonString);
//
//                    new Thread(new Runnable() {
//                        public void run() {
//                            Operation operaton=new Operation();
//                            String result= operaton.UpData("Register", jsonString);
//                            Message msg=new Message();
//                            msg.obj=result;
////                            handler.sendMessage(msg);
//                            handler1.sendMessage(msg);
//                        }
//                    }).start();



                }else{
                    Toast.makeText(RegisterActivity.this, "请将信息填写完整", Toast.LENGTH_LONG).show();
                }

               // dialog.show();
            }
        });
    }




    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            String string=msg.obj.toString();     //这一句有问题
            System.out.println(string);
            System.out.println(string.length());

            if (string.equals("t")) {
                et_user.requestFocus();
                et_user.setError("用户名"+username+"已存在");
            }
            else
            {
                et_pwd1.requestFocus();
            }
            super.handleMessage(msg);
        }
    };



    Handler handler1=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
//            dialog.dismiss();

            try {

                String msgobj=msg.obj.toString();
                if(msgobj.equals("t"))
                {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent();
                    intent.setClass(RegisterActivity.this, MthirdActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);

            }catch (Exception e){
                Toast.makeText(RegisterActivity.this, "服务器未打开", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
//            String msgobj=msg.obj.toString();
//            if(msgobj.equals("t"))
//            {
//                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//                Intent intent=new Intent();
//                intent.setClass(RegisterActivity.this, MthirdActivity.class);
//                startActivity(intent);
//                finish();
//            }
//            else {
//                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
//            }
//            super.handleMessage(msg);
        }
    };
}
