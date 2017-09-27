package com.example.lenovo.myapplication.base;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.baidu.platform.comapi.map.E;
import com.example.lenovo.myapplication.LiaotianActivity;
import com.example.lenovo.myapplication.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.Iterator;
import java.util.List;

/**
 * Created by chj on 2017/8/20.
 */

public class App extends  BaseApplication implements Thread.UncaughtExceptionHandler{
    public static Context mycontext;
    public static BaseActivity mBaseActivity;
    public static BaseFragment lastfragment;
    private App appContext;

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e("TAG", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(getApplicationContext(), options);
    }
    public static EMMessageListener tixing(final EaseConversationList list, final Context context){
        EMMessageListener emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list1) {
                Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(context, defaultUri);
                ringtone.play();
                Intent intent = new Intent(context, LiaotianActivity.class);
                intent.putExtra("A", list1.get(0).getFrom());
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
                getNotify(context, pendingIntent, "有来自+" + list1.get(0).getFrom() + "消息");
                list.refresh();
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
                list.refresh();
            }
        };
        return emMessageListener;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.setDefaultUncaughtExceptionHandler(this);


    }
    public static void getNotify(Context Context, PendingIntent pendingIntent, String ss) {
        NotificationManager mNotificationManager = (NotificationManager) Context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Context);
        mBuilder.setContentTitle(ss)//设置通知栏标题
                .setContentText("哈哈")
                .setContentIntent(pendingIntent) //设置通知栏点击意图
//  .setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
        mNotificationManager.notify(1, mBuilder.build());
    }
}
