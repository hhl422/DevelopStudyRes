package self.bobby.crashhandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hehongli on 2018/8/8.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/CrashTest/log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler(){
    }

    public static CrashHandler getsInstance(){
        return sInstance;
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 关键函数，当程序中有违背捕获的异常时，系统将会自动调用uncaughtException方法
     * @param thread 出现捕获异常的线程
     * @param ex 未捕获的异常 可以得到异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try{
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer(ex);
        }catch (IOException e){
            e.printStackTrace();
        }

        ex.printStackTrace();
        /*如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就自己结束自己*/
        if(mDefaultCrashHandler != null){
            mDefaultCrashHandler.uncaughtException(thread,ex);
        }else {
            /*或者弹出crash提示框再退出*/
            Toast.makeText(mContext,"crashed", Toast.LENGTH_SHORT);
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 将异常信息写到SD卡中
     * @param ex
     * @throws IOException
     */
    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if(DEBUG){
                Log.w(TAG,"sdcard unmounted,skip dump exception");
            }
            return;
        }

        File dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH+FILE_NAME+time+FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG,"dump crash info failed:"+e.getMessage());
        }
    }

    /**
     * 获取手机信息
     * @param pw
     * @throws IOException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException{
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("APP Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        /*Android版本号*/
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        /*手机制造商*/
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

         /*手机型号*/
        pw.print("Model: ");
        pw.println(Build.MODEL);

         /*CPU架构*/
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 上传服务器
     * @param ex
     * @throws IOException
     */
    private void uploadExceptionToServer(Throwable ex) throws IOException {

    }
}
