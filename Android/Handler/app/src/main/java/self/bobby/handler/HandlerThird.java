package self.bobby.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by hehongli on 2018/8/6.
 *
 * HandlerThread
 */

public class HandlerThird extends Activity {

    private TextView text;

    private HandlerThread thread;

    private Handler handler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = new TextView(this);
        text.setText("handler Thread");
        setContentView(text);

        /**给每个线程一个单独的存储数组，避免多线程并发导致的问题（如空指针）**/
        thread = new HandlerThread("handler thread");
        thread.start();

        handler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("current thread------------>"+Thread.currentThread());
            }
        };

        handler.sendEmptyMessage(1);
    }
}
