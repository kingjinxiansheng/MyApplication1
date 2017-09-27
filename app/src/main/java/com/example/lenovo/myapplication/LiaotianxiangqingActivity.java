package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class LiaotianxiangqingActivity extends AppCompatActivity {

    private FrameLayout liaotianframent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liaotianxiangqing);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
//        Log.e("TGGGG",name);
        initView();
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID,name);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.liaotianframent, chatFragment).commit();

    }

    private void initView() {
        liaotianframent = (FrameLayout) findViewById(R.id.liaotianframent);
    }
}
