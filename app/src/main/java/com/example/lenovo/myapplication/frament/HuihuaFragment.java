package com.example.lenovo.myapplication.frament;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.lenovo.myapplication.LiaotianxiangqingActivity;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.base.BaseFragment;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class HuihuaFragment extends BaseFragment {
    private List<EMConversation> list=new ArrayList<EMConversation>();
    private List<String> liatname=new ArrayList<String>();
    private EaseConversationList conversationListView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_huihua;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        Set<String> strings = allConversations.keySet();
        for (String name:strings) {
            //这个是添加的key获取都的vlu..值的集合
            list.add(allConversations.get(name));
            //这是通过遍历添加所有会话的名字
            liatname.add(name);
        }
//会话列表控件
        conversationListView = (EaseConversationList) view.findViewById(R.id.list);
//初始化，参数为会话列表集合
        conversationListView.init(list);
//刷新列表
        conversationListView.refresh();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),LiaotianxiangqingActivity.class);
                intent.putExtra("name",liatname.get(i));
                getActivity().startActivity(intent);
            }
        });
    }
    @Override
    public void setBundle(Bundle bundle) {
    }
}
