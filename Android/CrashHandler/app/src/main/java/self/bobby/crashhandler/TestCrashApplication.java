package self.bobby.crashhandler;

import android.app.Application;

/**
 * Created by hehongli on 2018/8/8.
 */

public class TestCrashApplication extends Application {
    private static TestCrashApplication sInstance;

    public static TestCrashApplication getsInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        //为应用设置异常处理，然后程序才能获取未处理的异常
        CrashHandler crashHandler = CrashHandler.getsInstance();
        crashHandler.init(this);
    }

}
