package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText zhucename;
    private EditText zhucepass;
    private Button wanchengzhuce;
    private LinearLayout activity_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        initView();
    }
    private void initView() {
        zhucename = (EditText) findViewById(R.id.zhucename);
        zhucepass = (EditText) findViewById(R.id.zhucepass);
        wanchengzhuce = (Button) findViewById(R.id.wanchengzhuce);
        activity_zhuce = (LinearLayout) findViewById(R.id.activity_zhuce);

        wanchengzhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wanchengzhuce:
//                Log.e("TAG","3333333333333333");
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        final String zhucenameString = zhucename.getText().toString().trim();
        if (TextUtils.isEmpty(zhucenameString)) {
            Toast.makeText(this, "请输入注册用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        final String zhucepassString = zhucepass.getText().toString().trim();
        if (TextUtils.isEmpty(zhucepassString)) {
            Toast.makeText(this, "请输入注册密码", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(zhucenameString, zhucepassString);
                    Log.e("TAG","2222222222");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ZhuceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Log.e("TAG","注册成功");
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("TAG","注册失败了");
                    finish();
                }
            }
        }).start();

    }
}
