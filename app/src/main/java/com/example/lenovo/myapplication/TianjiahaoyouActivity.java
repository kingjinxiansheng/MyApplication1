package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

public class TianjiahaoyouActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tianjiahaoyouid;
    private EditText tianjiaqingqiu;
    private Button quedingtianjia;
    private Button quxiaotianjia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianjiahaoyou);
        initView();
    }

    private void initView() {
        tianjiahaoyouid = (EditText) findViewById(R.id.tianjiahaoyouid);
        tianjiaqingqiu = (EditText) findViewById(R.id.tianjiaqingqiu);
        quedingtianjia = (Button) findViewById(R.id.quedingtianjia);
        quxiaotianjia = (Button) findViewById(R.id.quxiaotianjia);

        quedingtianjia.setOnClickListener(this);
        quxiaotianjia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quedingtianjia:
                submit();
                break;
            case R.id.quxiaotianjia:
            finish();
                break;
        }
    }

    private void submit() {
        // validate
        final String tianjiahaoyouidString = tianjiahaoyouid.getText().toString().trim();
        if (TextUtils.isEmpty(tianjiahaoyouidString)) {
            Toast.makeText(this, "请输入添加好友的账号", Toast.LENGTH_SHORT).show();
            return;
        }

        final String tianjiaqingqiuString = tianjiaqingqiu.getText().toString().trim();
        if (TextUtils.isEmpty(tianjiaqingqiuString)) {
            Toast.makeText(this, "请输入添加好友的理由", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("list");

        if(list.contains(tianjiahaoyouidString)){
            Toast.makeText(this, "他已经是你的好友了", Toast.LENGTH_SHORT).show();
            finish();
            }else {
            // TODO validate success, do something
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().addContact(tianjiahaoyouidString, tianjiaqingqiuString);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TianjiahaoyouActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TianjiahaoyouActivity.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
