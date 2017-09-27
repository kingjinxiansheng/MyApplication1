package com.example.lenovo.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.example.lenovo.myapplication.base.App;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import java.util.List;
//这个没有实现，因为一些冲突没有解决，
public class MyService extends Service {
    private boolean boo;

    public MyService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate","....");
        final StringBuffer sb=new StringBuffer();
        //消息监听
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {

                for (int i = 0; i <list.size() ; i++) {
                    EMMessage message = list.get(i);
                    String from = message.getFrom();
                    sb.append(from+"!");
                }
                String s = sb.toString();
                String[] split = s.split("!");
                for (int i = 0; i <split.length ; i++) {
                    if (split[0].equals(split[i])){
                        boo=true;
                    }else{
                        boo=false;
                    }
                }
                if (!boo){
                    Intent intent = new Intent(App.mycontext, LiaotianActivity.class);
                    PendingIntent activity = PendingIntent.getActivity(App.mycontext, 0, intent, 0);
                    NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification builder = new NotificationCompat.Builder(App.mycontext)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                            .setContentTitle("收到新的消息")
                            .setContentText("点击跳转")
                            .setContentIntent(activity)
                            .setAutoCancel(true)
                            .build();
                    Log.e("TAG","sssssssssssss");
                    systemService.notify(1,builder);
                }else if (boo){
                    Intent intent = new Intent(App.mycontext, LiaotianxiangqingActivity.class);
                    intent.putExtra("username",split[0]);
                    PendingIntent activity = PendingIntent.getActivity(App.mycontext, 0, intent, 0);
                    NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification builder = new NotificationCompat.Builder(App.mycontext)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                            .setContentTitle("收到新的消息")
                            .setContentText("点击跳转")
                            .setContentIntent(activity)
                            .setAutoCancel(true)
                            .build();
                    Log.e("TAG","sssssssssssss");
                    systemService.notify(1,builder);
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });

    }
}
