package com.example.lenovo.myapplication.frament;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.lenovo.myapplication.LiaotianxiangqingActivity;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.TianjiahaoyouActivity;
import com.example.lenovo.myapplication.base.BaseFragment;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TongxunluFragment extends BaseFragment implements EMValueCallBack<List<String>> {
    List<EaseUser> listEa = new ArrayList<EaseUser>();
    @BindView(R.id.tianjia)
    Button tianjia;
    Unbinder unbinder;
    private EaseContactList contactListLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tongxunlu;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        contactListLayout = (EaseContactList) view.findViewById(R.id.contact_list);
        //初始化时需要传入联系人list
        contactListLayout.init(listEa);
        contactListLayout.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),LiaotianxiangqingActivity.class);
                intent.putExtra("name",listEa.get(i).getUsername());
                getActivity().startActivity(intent);
            }
        });
    }
    @Override
    public void setBundle(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tianjia)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),TianjiahaoyouActivity.class);
        intent.putStringArrayListExtra("list",lista);
        startActivity(intent);
    }
    private ArrayList<String> lista=new ArrayList<>();
    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(this);
//            Log.e("TAGGGG",usernames.get(0));
    }

    @Override
    public void onSuccess(List<String> strings) {
        lista.clear();
        lista.addAll(strings);
        listEa.clear();
//        for (int i = 0; i < strings.size(); i++) {
//            String s = strings.get(i);
//            EaseUser easeUser = new EaseUser(s);
//            listEa.add(easeUser);
//        }
        for(String name:strings){
            listEa.add(new EaseUser(name));
        }
//刷新列表
        contactListLayout.refresh();
    }

    @Override
    public void onError(int i, String s) {

    }
}
