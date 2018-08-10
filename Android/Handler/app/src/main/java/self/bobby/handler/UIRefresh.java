package self.bobby.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by hehongli on 2018/8/6.
 *
 * Android中更新UI的几种方式
 *  runOnUiThread
 *  handler post
 *  handler sendMessage
 *  view post
 *
 * 非UI线程更新UI
 */

public class UIRefresh extends Activity {

    private TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText("ok");
        }
    };

    /**
     * handler post 通知主线程更新UI
     *
     *  post内部封装的就是sendMessageDelayed(getPostMessage,0)方法
     */
    private void handler1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("ok");
            }
        });
    }

    /***
     * handler sendMessage 通知主线程更新UI
     *
     * 【最终实现】
     */
    private void handler2(){
        handler.sendEmptyMessage(1);
    }

    /**
     * runOnUiThread UI线程更新
     *
     * 内部实现：
     *  if(Thread.currentThread()!= mUiThread){
     *      mHandler.post(action);
     *  }else{
     *      action.run();
     *  }
     */
    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("ok");
            }
        });
    }

    /**
     * view post 控件自己更新
     *
     * 内部实现：
     * final AttachInfo attachInfo = mAttachInfo;
     * if(attachInfo != null){
     *     return attachInfo.mHandler.post(action);
     * }
     * //Assume that post will succeed later
     * ViewRootImpl.getRunQueue().post(action);
     * return true;
     */
    private void viewUI(){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("ok");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**创建ViewRootImpl的地方，在onCreate之后**/
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_ui);
        textView = findViewById(R.id.textViewUI);

        new Thread(){
            @Override
            public void run() {
                /**非UI线程更新UI
                 *
                 * 休眠之后更新UI抛出异常：
                 * view.invalidate();
                 *  ——ViewParent//extends ViewRootImpl
                 *  ——ViewParent.invalidateChild(this,null);
                 *  ——checkThread();//判断了是否在主线程
                 * **/
                try {
                    Thread.sleep(2000);
                    textView.setText("ok");
                    //error:
                    // android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /**非UI线程更新UI
                 *
                 * works:
                 * 未休眠时，因为没有走到onResume,当前Activity的ViewRootImpl还没有被创建
                 * **/
//                textView.setText("ok");


                /**Android中更新UI的几种方式**/
//                try {
//                    Thread.sleep(2000);
//
////                    handler1();
////                    handler2();
////                    updateUI();
//                    viewUI();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }.start();

    }
}
