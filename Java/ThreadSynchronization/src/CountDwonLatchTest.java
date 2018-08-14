import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CountDwonLatch（闭锁）、、、一个线程等到其它几个线程执行完才能执行的场景
 * 
 * CountDwonLatch阻塞一个线程、、、主线程等待多个工作线程结束
 * CyclicBarrier阻塞多个线程、、、多个线程之间互相等待，直到所有线程达到一个障碍点(Barrier point)
 * 
 * 用来等待一个或者多个线程完成操作，作用类似于当前线程里调用join（）方法，让当前线程等待join（）进来的线程执行完毕再执行当前线程剩下的逻辑.
 * 在CountDwonLatch中，countDwon（）方法和await（）方法搭配使用才能起到类似join（）的作用：
 * （1）首先创建一个CountDwonLatch对象并传入要等待的线程的数量，这是个计数器；
 * （2）在被等待的线程或者步骤执行完毕后调用countDwon（）方法让计数器减1，countDwon（）方法是一个等待的计数器，每次调用countDwon（）方法，计数器减1，直到计数器为0，countDwon可以用在任何地方，可以是一个步骤的一个点，也可以是一个线程。
 * （3）在等待其他线程的主线程中，调用await（）方法来等待其他调用了countDwon（）的线程，直到计数器为0，再执行该线程接下来的逻辑，当然，如果某个线程执行的时间过久，当前线程不可能一直等待，那么可以调用await（long time, TimeUnit unit）方法。
 * 注意：计数器大于0时才会阻塞当前线程，一旦计数器等于0就不会再阻塞调用await（）的当前线程；
 * 在创建CountDwonLatch时传入计数器的初始值后，计数器就不能重新初始化了；
 */
public class CountDwonLatchTest {
    
	public static void main(String[] args) throws InterruptedException {
//       Test1();
	   /*所有的工作线程中准备就绪以后，并不是直接运行，而是等待主线程的信号后再执行具体的操作*/
//       new Driver().main();  
       /*通过Executor启动线程：*/
       new CountDownLatchDriver2().main();
   }
	
    private static void Test1() throws InterruptedException{
    	final CountDownLatch cdl = new CountDownLatch(2);   //①新建一个CountDwonLatch对象并传入计数器的值
    	new Thread(new Runnable() {
            public void run() {
                try{
                    System.out.println("thread 1: 1 ");
                    Thread.sleep(1000);
                    System.out.println("thread 1 : 2");
                    cdl.countDown();                   //②在被等待的线程或者步骤执行完毕后调用countDwon（）方法让计数器减1
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
 
            }
        }).start();
        new Thread(new Runnable(){
            public void run(){
                try{
                    System.out.println("thread 2 : 1");
                    Thread.sleep(1000);
                    System.out.println("thread 2 : 2");
                    cdl.countDown();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
 
 
            }        }).start();
        
        cdl.await();                                 //③在等待的主线程中调用await（）方法等待其他线程，知道计数器为0 再执行主线程接下来的逻辑
        System.out.println("thread : main");
    }

}

class Driver  {  
    private static final int TOTAL_THREADS = 10;  
    private final CountDownLatch mStartSignal = new CountDownLatch(1);  
    private final CountDownLatch mDoneSignal = new CountDownLatch(TOTAL_THREADS);  
  
    void main(){  
        for (int i = 0; i < TOTAL_THREADS; i++){  
            new Thread(new Worker(mStartSignal, mDoneSignal, i)).start();  
        } 
        System.out.println("Main Thread Now:" + System.currentTimeMillis());  
        doPrepareWork();// 准备工作   
        mStartSignal.countDown();// 计数减一为0，工作线程真正启动具体操作   
        doSomethingElse();//做点自己的事情   
        try{  
            mDoneSignal.await();// 等待所有工作线程结束   
        } catch (InterruptedException e){  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
        System.out.println("All workers have finished now.");  
        System.out.println("Main Thread Now:" + System.currentTimeMillis());  
    }  
  
    void doPrepareWork(){  
        System.out.println("Ready,GO!");  
    }  
  
    void doSomethingElse(){  
        for (int i = 0; i < 100000; i++){  
            ;// delay   
        }  
        System.out.println("Main Thread Do something else.");  
    }  
}  
  
class Worker implements Runnable{  
    private final CountDownLatch mStartSignal;  
    private final CountDownLatch mDoneSignal;  
    private final int mThreadIndex;  
  
    Worker(final CountDownLatch startSignal, final CountDownLatch doneSignal,final int threadIndex){  
        this.mDoneSignal = doneSignal;  
        this.mStartSignal = startSignal;  
        this.mThreadIndex = threadIndex;  
    }  
  
    public void run(){  
        // TODO Auto-generated method stub   
        try{  
            mStartSignal.await();// 阻塞，等待mStartSignal计数为0运行后面的代码   
                                              // 所有的工作线程都在等待同一个启动的命令   
            doWork();// 具体操作   
            System.out.println("Thread " + mThreadIndex + " Done Now:"  
                    + System.currentTimeMillis());  
            mDoneSignal.countDown();// 完成以后计数减一   
        }catch (InterruptedException e){  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
    }  
  
    public void doWork(){  
        for (int i = 0; i < 1000000; i++){  
            ;// 耗时操作   
        }  
        System.out.println("Thread " + mThreadIndex + ":do work");  
    }  
}  


class CountDownLatchDriver2  
{  
    private static final int TOTAL_THREADS = 10;  
    private final CountDownLatch mDoneSignal = new CountDownLatch(TOTAL_THREADS);  
  
    void main()  
    {  
        System.out.println("Main Thread Now:" + System.currentTimeMillis());  
        doPrepareWork();// 准备工作   
  
        Executor executor = Executors.newFixedThreadPool(TOTAL_THREADS);  
        for (int i = 0; i < TOTAL_THREADS; i++)  
        {  
            // 通过内建的线程池维护创建的线程   
            executor.execute(new RunnableWorker(mDoneSignal, i));  
        }  
        doSomethingElse();// 做点自己的事情   
        try  
        {  
            mDoneSignal.await();// 等待所有工作线程结束   
        }  
        catch (InterruptedException e)  
        {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
        System.out.println("All workers have finished now.");  
        System.out.println("Main Thread Now:" + System.currentTimeMillis());  
    }  
  
    void doPrepareWork()  
    {  
        System.out.println("Ready,GO!");  
    }  
  
    void doSomethingElse()  
    {  
        for (int i = 0; i < 100000; i++)  
        {  
            ;// delay   
        }  
        System.out.println("Main Thread Do something else.");  
    }  
}  
  
class RunnableWorker implements Runnable  
{  
  
    private final CountDownLatch mDoneSignal;  
    private final int mThreadIndex;  
  
    RunnableWorker(final CountDownLatch doneSignal, final int threadIndex)  
    {  
        this.mDoneSignal = doneSignal;  
        this.mThreadIndex = threadIndex;  
    }  
  
    public void run()  
    {  
        // TODO Auto-generated method stub   
  
        doWork();// 具体操作   
        System.out.println("Thread " + mThreadIndex + " Done Now:"  
                + System.currentTimeMillis());  
        mDoneSignal.countDown();// 完成以后计数减一   
                                                    // 计数为0时，主线程结束阻塞，继续执行其他任务   
        try  
        {  
            // 可以继续做点其他的事情，与主线程无关了   
            Thread.sleep(5000);  
            System.out.println("Thread " + mThreadIndex  
                    + " Do something else after notifing main thread");  
  
        }  
        catch (InterruptedException e)  
        {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
  
    }  
  
    public void doWork()  
    {  
        for (int i = 0; i < 1000000; i++)  
        {  
            ;// 耗时操作   
        }  
        System.out.println("Thread " + mThreadIndex + ":do work");  
    }  
}  


