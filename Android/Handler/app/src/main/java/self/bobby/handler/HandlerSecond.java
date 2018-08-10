package self.bobby.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by hehongli on 2018/8/6.
 *
 * 自定义与线程相关的Handler
 */

public class HandlerSecond extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("UI---------->:"+Thread.currentThread());
        }
    };

    class MyThread extends Thread{
        private Handler handler;
        private Looper looper;

        @Override
        public void run() {
            /**为子线程准备Looper
             *
             *  每一个handler必须要对应一个looper，主线程会自动创建Looper对象，不需要我们手动创建，所以主线程可以直接创建handler。
             *  在new handler的时候没有传入指定的looper就会默认绑定当前创建handler的线程的looper，如果没有looper就报错。
             *  如果自己开个子线程，得自己创建looper对象啊，或者用HandlerThread的getlooper。
             * **/
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    System.out.println("currentThread:"+Thread.currentThread());
                }
            };
            Looper.loop();

        }
    }

    private MyThread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("hello handler");
        setContentView(textView);


        thread = new MyThread();
        thread.start();
        /**自定义与线程相关的Handler**/
        try{
            Thread.sleep(500);

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        thread.handler.sendEmptyMessage(1);//子线程的重新定义的handler System.out: currentThread:Thread[Thread-170854,5,main]
        handler.sendEmptyMessage(1);//主线程的handler System.out: UI---------->:Thread[main,5,main]

        /**线程切换 looper还未初始化，空指针异常**/
//        handler = new Handler(thread.looper){
//            @Override
//            public void handleMessage(Message msg) {
//                System.out.println(msg);
//
//            }
//        };
//        handler.sendEmptyMessage(1);
        /****/
    }
}
