package self.bobby.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hehongli on 2018/8/6.
 *
 * 课程链接——http://www.imooc.com/learn/267
 *
 * Handler的用法：
 *  sendMessage
 *  sendMessageDelayed
 *  post(Runnable)
 *  postDelayed(Runnable,long);
 *
 *  总结:
 *  Looper接受Handler sendMessage()来的消息，放入MessageQueue
 *  Looper从MessageQueue取出Message,交给Handler handleMessage()处理
 */
public class HandlerActivity extends Activity implements View.OnClickListener {

    private TextView textView;

    private Button button;

/**可以利用有返回值的handleMassage方法截获发送来的消息，
 * 当handleMassage方法的返回值为true时，后面无返回值的handleMassage就不会执行了。**/
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(getApplicationContext(),""+1,Toast.LENGTH_SHORT).show();

            return false;//false:继续走到handleMessage  true:截获消息 不转给handleMessage
        }
    }){
        @Override
        public void handleMessage(Message message){
//            textView.setText(""+message.arg1+"-"+message.arg2);
//            textView.setText(message.obj.toString());
            Toast.makeText(getApplicationContext(),""+2,Toast.LENGTH_SHORT).show();

        };
    };

    class Person{
        public int age;
        public String name;

        @Override
        public String toString() {
            return "name:"+name+" age:"+age;
        }
    }

    private ImageView imageView;

    private int images[] = {R.drawable.xiaoxin1,R.drawable.xiaoxin2,R.drawable.xiaoxin3,
            R.drawable.xiaoxin4,R.drawable.xiaoxin5};

    private int index;

    private MyRunnable myRunnable = new MyRunnable();

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            index++;
            index=index%5;

            imageView.setImageResource(images[index]);
            handler.postDelayed(myRunnable,1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        textView = (TextView)findViewById(R.id.textview);
        imageView = (ImageView)findViewById(R.id.imageView1);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        /**sendMessage sendToTarget  发送带参可拦截消息**/
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(2000);

                    Person person = new Person();
                    person.age = 23;
                    person.name = "lily";

                    //用法1
//                    Message message = new Message();
//                    message.arg1 = 88;
//                    message.arg2 = 100;
//                    message.obj = person;
//                    handler.sendMessage(message);

                    //用法2
                    Message message = handler.obtainMessage();
                    message.obj = person;
                    message.sendToTarget();

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();


        /**postDelayed 发送循环消息**/
        handler.postDelayed(myRunnable,1000);

        /**post  发送延迟消息**/
//        new Thread(){
//            public void run(){
//                try {
//                    Thread.sleep(1000);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("update thread");
//                        }
//                    });
//
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();

    }

    @Override
    public void onClick(View view) {
//        handler.removeCallbacks(myRunnable);

        handler.sendEmptyMessage(1);
    }
}
