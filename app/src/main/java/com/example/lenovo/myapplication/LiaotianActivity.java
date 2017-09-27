package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.base.App;
import com.example.lenovo.myapplication.base.BaseActivity;
import com.example.lenovo.myapplication.base.FragmentMager;
import com.example.lenovo.myapplication.frament.HuihuaFragment;
import com.example.lenovo.myapplication.frament.ShuZhiFragment;
import com.example.lenovo.myapplication.frament.TongxunluFragment;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.lenovo.myapplication.R.id.framelayout;

public class LiaotianActivity extends BaseActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getshu();
        }
    };
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.huihua)
    TextView huihua;
    @BindView(R.id.lianxiren)
    TextView lianxiren;
    @BindView(R.id.shezhi)
    TextView shezhi;
    @BindView(R.id.shuxiang)
    TextView shuxiang;
    private FragmentManager supportFragmentManager;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_liaotian;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.mycontext=this;
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        FragmentMager.getInstance().start(R.id.framelayout, TongxunluFragment.class, false).build();
    }

    @OnClick({R.id.huihua, R.id.lianxiren, R.id.shezhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.huihua:
                supportFragmentManager = App.mBaseActivity.getSupportFragmentManager();
                FragmentMager.getInstance().start(R.id.framelayout, HuihuaFragment.class, false).build();
                break;
            case R.id.lianxiren:
                supportFragmentManager = App.mBaseActivity.getSupportFragmentManager();
                FragmentMager.getInstance().start(R.id.framelayout, TongxunluFragment.class, false).build();
                break;
            case R.id.shezhi:
                supportFragmentManager = App.mBaseActivity.getSupportFragmentManager();
                FragmentMager.getInstance().start(R.id.framelayout, ShuZhiFragment.class, false).build();
                break;
        }
    }
    private void getshu(){
        int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        if(unreadMsgsCount>0){
            shuxiang.setVisibility(View.VISIBLE);
            shuxiang.setText(unreadMsgsCount+"");
        }else{
            shuxiang.setVisibility(View.GONE);
        }
        handler.sendEmptyMessage(1);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getshu();
        EMClient.getInstance().chatManager().addMessageListener(App.tixing(null,this));
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Intent intent = new Intent(LiaotianActivity.this,MyService.class);
//        startService(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
