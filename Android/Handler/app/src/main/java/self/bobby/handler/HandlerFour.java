package self.bobby.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import self.bobby.handler.R;

/**
 * Created by hehongli on 2018/8/6.
 *
 * Handler 主线程与子线程之间的信息交互
 */

public class HandlerFour extends Activity implements View.OnClickListener {

    private Button button1,button2;

    private boolean flag = true;

    /**主线程的handler**/
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Message message = new Message();
            System.out.println("main handle------------>"+Thread.currentThread());
            /**向子线程发送消息**/
            if(flag) {
                threadHandler.sendMessageDelayed(message, 1000);
            }
        }
    };

    /**子线程的handler**/
    private Handler threadHandler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_handler_four);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        /**用HandlerThread创建子线程**/
        HandlerThread thread = new HandlerThread("handlerThread");
        thread.start();
        /**传入子线程的Looper，创建子线程的handler**/
        threadHandler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Message message = new Message();
                System.out.println("thread Handler ------------>"+Thread.currentThread());
                /**向主线程发送消息**/
                handler.sendMessageDelayed(message,1000);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                handler.sendEmptyMessage(1);
                break;
            case R.id.button2:
                flag=false;
                break;
            default:
                break;
        }
    }
}
