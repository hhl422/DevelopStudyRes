import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CountDwonLatch��������������һ���̵߳ȵ����������߳�ִ�������ִ�еĳ���
 * 
 * CountDwonLatch����һ���̡߳��������̵߳ȴ���������߳̽���
 * CyclicBarrier��������̡߳���������߳�֮�以��ȴ���ֱ�������̴߳ﵽһ���ϰ���(Barrier point)
 * 
 * �����ȴ�һ�����߶���߳���ɲ��������������ڵ�ǰ�߳������join�����������õ�ǰ�̵߳ȴ�join�����������߳�ִ�������ִ�е�ǰ�߳�ʣ�µ��߼�.
 * ��CountDwonLatch�У�countDwon����������await������������ʹ�ò���������join���������ã�
 * ��1�����ȴ���һ��CountDwonLatch���󲢴���Ҫ�ȴ����̵߳����������Ǹ���������
 * ��2���ڱ��ȴ����̻߳��߲���ִ����Ϻ����countDwon���������ü�������1��countDwon����������һ���ȴ��ļ�������ÿ�ε���countDwon������������������1��ֱ��������Ϊ0��countDwon���������κεط���������һ�������һ���㣬Ҳ������һ���̡߳�
 * ��3���ڵȴ������̵߳����߳��У�����await�����������ȴ�����������countDwon�������̣߳�ֱ��������Ϊ0����ִ�и��߳̽��������߼�����Ȼ�����ĳ���߳�ִ�е�ʱ����ã���ǰ�̲߳�����һֱ�ȴ�����ô���Ե���await��long time, TimeUnit unit��������
 * ע�⣺����������0ʱ�Ż�������ǰ�̣߳�һ������������0�Ͳ�������������await�����ĵ�ǰ�̣߳�
 * �ڴ���CountDwonLatchʱ����������ĳ�ʼֵ�󣬼������Ͳ������³�ʼ���ˣ�
 */
public class CountDwonLatchTest {
    
	public static void main(String[] args) throws InterruptedException {
//       Test1();
	   /*���еĹ����߳���׼�������Ժ󣬲�����ֱ�����У����ǵȴ����̵߳��źź���ִ�о���Ĳ���*/
//       new Driver().main();  
       /*ͨ��Executor�����̣߳�*/
       new CountDownLatchDriver2().main();
   }
	
    private static void Test1() throws InterruptedException{
    	final CountDownLatch cdl = new CountDownLatch(2);   //���½�һ��CountDwonLatch���󲢴����������ֵ
    	new Thread(new Runnable() {
            public void run() {
                try{
                    System.out.println("thread 1: 1 ");
                    Thread.sleep(1000);
                    System.out.println("thread 1 : 2");
                    cdl.countDown();                   //���ڱ��ȴ����̻߳��߲���ִ����Ϻ����countDwon���������ü�������1
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
        
        cdl.await();                                 //���ڵȴ������߳��е���await���������ȴ������̣߳�֪��������Ϊ0 ��ִ�����߳̽��������߼�
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
        doPrepareWork();// ׼������   
        mStartSignal.countDown();// ������һΪ0�������߳����������������   
        doSomethingElse();//�����Լ�������   
        try{  
            mDoneSignal.await();// �ȴ����й����߳̽���   
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
            mStartSignal.await();// �������ȴ�mStartSignal����Ϊ0���к���Ĵ���   
                                              // ���еĹ����̶߳��ڵȴ�ͬһ������������   
            doWork();// �������   
            System.out.println("Thread " + mThreadIndex + " Done Now:"  
                    + System.currentTimeMillis());  
            mDoneSignal.countDown();// ����Ժ������һ   
        }catch (InterruptedException e){  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
    }  
  
    public void doWork(){  
        for (int i = 0; i < 1000000; i++){  
            ;// ��ʱ����   
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
        doPrepareWork();// ׼������   
  
        Executor executor = Executors.newFixedThreadPool(TOTAL_THREADS);  
        for (int i = 0; i < TOTAL_THREADS; i++)  
        {  
            // ͨ���ڽ����̳߳�ά���������߳�   
            executor.execute(new RunnableWorker(mDoneSignal, i));  
        }  
        doSomethingElse();// �����Լ�������   
        try  
        {  
            mDoneSignal.await();// �ȴ����й����߳̽���   
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
  
        doWork();// �������   
        System.out.println("Thread " + mThreadIndex + " Done Now:"  
                + System.currentTimeMillis());  
        mDoneSignal.countDown();// ����Ժ������һ   
                                                    // ����Ϊ0ʱ�����߳̽�������������ִ����������   
        try  
        {  
            // ���Լ����������������飬�����߳��޹���   
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
            ;// ��ʱ����   
        }  
        System.out.println("Thread " + mThreadIndex + ":do work");  
    }  
}  


