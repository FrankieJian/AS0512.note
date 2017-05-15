package com.example.g572_528r.as0512note;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPhone;
    private EditText edtCode;
    private TextView getCode;
    private Button btnLog;
    private Handler handler;
    private Runnable runnable;
    private int num = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                getCode.setText(String.valueOf(num));
                getCode.setClickable(false);
                num--;
                if(num>0){
                    handler.postDelayed(runnable, 1000);
                }else {
                    getCode.setText("获取验证码");
                    getCode.setClickable(true);
                }
            }
        };
    }

    private void initViews() {
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtCode = (EditText) findViewById(R.id.edt_code);
        getCode = (TextView) findViewById(R.id.txt_getCode);
        btnLog = (Button) findViewById(R.id.btn_login);

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString();
                if(!isMobileNO(phone)){
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobSMS.requestSMSCode(phone, "bombTest", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Log.i("smile", "短信id:"+smsId);//用于查询本次短信发送情况
                        }
                    }
                });

                coundDown();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone =edtPhone.getText().toString();
                String code = edtCode.getText().toString();
                BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if(user!=null){
                            Log.i("smile", "用户登录成功");
                        }
                    }
                });
            }
        });
    }

    private void coundDown() {
        handler.post(runnable);
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}




















