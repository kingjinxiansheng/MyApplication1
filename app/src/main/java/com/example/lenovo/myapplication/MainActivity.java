package com.example.lenovo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText dengruname;
    private EditText dengrupass;
    private Button dengru;
    private Button zhuce;
    private LinearLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        dengruname = (EditText) findViewById(R.id.dengruname);
        dengrupass = (EditText) findViewById(R.id.dengrupass);
        dengru = (Button) findViewById(R.id.dengru);
        zhuce = (Button) findViewById(R.id.zhuce);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);

        dengru.setOnClickListener(this);
        zhuce.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dengru:
                submit();
                finish();
                break;
            case R.id.zhuce:
                Intent intent = new Intent(MainActivity.this,ZhuceActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void submit() {
        // validate
        String dengrunameString = dengruname.getText().toString().trim();
        if (TextUtils.isEmpty(dengrunameString)) {
            Toast.makeText(this, "请输入注册用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String dengrupassString = dengrupass.getText().toString().trim();
        if (TextUtils.isEmpty(dengrupassString)) {
            Toast.makeText(this, "请输入注册密码", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        EMClient.getInstance().login(dengrunameString,dengrupassString,new EMCallBack(){//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                Intent intent = new Intent(MainActivity.this,LiaotianActivity.class);
                startActivity(intent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this,LiaotianActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                });
            }
            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }
}
