import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * CyclicBarrier（同步屏障）、、、需要多线程的计算结果最后对这些结果进行合并的场景
 * 
 * 让一组线程到达一个屏障（或者同步点）时被阻塞，直到所有线程到达同步屏障后，同步屏障开门，所有线程继续执行。
 * 
 * 因为cb.await( )方法会抛出InterruptedException和BrokenBarrierException异常，因此在子线程中要使用try-catch方法来捕捉这两种异常；
 * CyclicBarrier中的线程计数器可以使用reset（）方法重置；
 * 在所有线程到达同步屏障后，并不是所有线程“同时”开始执行，而是使各个线程的启动时间降到最低；
 * 
 * int getWaitingNumber（）方法返回被阻塞在同步屏障的线程数，
 * boolean  isBroken（）方法返回是否有阻塞的线程被中断
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
    	/*构造器1：
    	 * （1）创建一个CyclicBarrier对象，传入要阻塞在同步屏障的线程数量；
    	 * （2）在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；*/
//        Test1();
        
        /* 构造器2：
         * （1）创建一个CyclicBarrier对象，传入要阻塞在同步屏障的线程数量，和barrierAction对象；
         * （2）写一个实现Runnable接口的class，用于实现第一个线程到达同步屏障前的业务逻辑；
         * （3）在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；*/
//        Test2();
        
        new WalkTarget();
    }
    
    private static void Test1(){
    	final CyclicBarrier cb = new CyclicBarrier(2);  //创建一个CyclicBarrier对象，传入要阻塞在同步屏障的线程数量；
    	new Thread(new Runnable() {
          public void run() {
              try{
                  cb.await();    //在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；
                  System.out.println(1);
              }
              catch(Exception e){
                  System.out.println("thread 1");
              }
          }
      }).start();
      try{
          cb.await();       //在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；
          System.out.println(3);
      }
      catch(Exception e){
          System.out.println("main");
      }
    }
    
    public static void Test2(){
    	final CyclicBarrier cb = new CyclicBarrier(3,new DoSomeThing()); //创建一个CyclicBarrier对象，传入要阻塞在同步屏障的线程数量，和barrierAction对象；
    	 
    	new Thread(new Runnable() {
            public void run() {
                try{
                    cb.await();             // 在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；
                    System.out.println(1);
                }
                catch(Exception e){
                    System.out.println("thread 1");
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            public void run() {
                try{
                    cb.await();             // 在每个要阻塞在同步屏障的子线程中调用cb.await( )方法；
                    System.out.println(2);
                }
                catch(Exception e){
                    System.out.println("thread 2");
                }
            }
        }).start();
        
        try{
            cb.await();
            System.out.println(3);
        }
        catch(Exception e){
            System.out.println("main");
        }
    }
    
    public static class DoSomeThing implements Runnable{  //写一个实现Runnable接口的class，用于实现第一个线程到达同步屏障前的业务逻辑；
        public void run(){
            System.out.println("happen-before CyclicBarrier");
        }
    }

}

class WalkTarget{  
    private final int mCount = 5;  
    private final CyclicBarrier mBarrier;  
    ExecutorService mExecutor;  
  
    class BarrierAction implements Runnable{  
        public void run(){  
            System.out.println("所有线程都已经完成任务,计数达到预设值");  
            //mBarrier.reset();//恢复到初始化状态          
        }  
    }  
  
    WalkTarget(){  
        //初始化CyclicBarrier   
        mBarrier = new CyclicBarrier(mCount, new BarrierAction());  
        mExecutor = Executors.newFixedThreadPool(mCount);  
  
        for (int i = 0; i < mCount; i++){  
            //启动工作线程   
            mExecutor.execute(new Walker(mBarrier, i));  
        }  
    }  
}  
  
//工作线程   
class Walker implements Runnable{  
    private final CyclicBarrier mBarrier;  
    private final int mThreadIndex;  
  
    Walker(final CyclicBarrier barrier, final int threadIndex){  
        mBarrier = barrier;  
        mThreadIndex = threadIndex;  
    }  
  
    public void run(){  
        System.out.println("Thread " + mThreadIndex + " is running...");  
        // 执行任务   
        try{  
            TimeUnit.MILLISECONDS.sleep(5000);  
            // do task   
        }catch (InterruptedException e){  
            e.printStackTrace();  
        }  
  
        // 完成任务以后，等待其他线程完成任务   
        try{  
            mBarrier.await();  
        }catch (InterruptedException e){  
            e.printStackTrace();  
        }catch (BrokenBarrierException e)   {  
            e.printStackTrace();  
        }  
        // 其他线程任务都完成以后，阻塞解除，可以继续接下来的任务   
        System.out.println("Thread " + mThreadIndex + " do something else");  
    }  
  
}  
