import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * CyclicBarrier��ͬ�����ϣ���������Ҫ���̵߳ļ�����������Щ������кϲ��ĳ���
 * 
 * ��һ���̵߳���һ�����ϣ�����ͬ���㣩ʱ��������ֱ�������̵߳���ͬ�����Ϻ�ͬ�����Ͽ��ţ������̼߳���ִ�С�
 * 
 * ��Ϊcb.await( )�������׳�InterruptedException��BrokenBarrierException�쳣����������߳���Ҫʹ��try-catch��������׽�������쳣��
 * CyclicBarrier�е��̼߳���������ʹ��reset�����������ã�
 * �������̵߳���ͬ�����Ϻ󣬲����������̡߳�ͬʱ����ʼִ�У�����ʹ�����̵߳�����ʱ�併����ͣ�
 * 
 * int getWaitingNumber�����������ر�������ͬ�����ϵ��߳�����
 * boolean  isBroken�������������Ƿ����������̱߳��ж�
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
    	/*������1��
    	 * ��1������һ��CyclicBarrier���󣬴���Ҫ������ͬ�����ϵ��߳�������
    	 * ��2����ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������*/
//        Test1();
        
        /* ������2��
         * ��1������һ��CyclicBarrier���󣬴���Ҫ������ͬ�����ϵ��߳���������barrierAction����
         * ��2��дһ��ʵ��Runnable�ӿڵ�class������ʵ�ֵ�һ���̵߳���ͬ������ǰ��ҵ���߼���
         * ��3����ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������*/
//        Test2();
        
        new WalkTarget();
    }
    
    private static void Test1(){
    	final CyclicBarrier cb = new CyclicBarrier(2);  //����һ��CyclicBarrier���󣬴���Ҫ������ͬ�����ϵ��߳�������
    	new Thread(new Runnable() {
          public void run() {
              try{
                  cb.await();    //��ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������
                  System.out.println(1);
              }
              catch(Exception e){
                  System.out.println("thread 1");
              }
          }
      }).start();
      try{
          cb.await();       //��ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������
          System.out.println(3);
      }
      catch(Exception e){
          System.out.println("main");
      }
    }
    
    public static void Test2(){
    	final CyclicBarrier cb = new CyclicBarrier(3,new DoSomeThing()); //����һ��CyclicBarrier���󣬴���Ҫ������ͬ�����ϵ��߳���������barrierAction����
    	 
    	new Thread(new Runnable() {
            public void run() {
                try{
                    cb.await();             // ��ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������
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
                    cb.await();             // ��ÿ��Ҫ������ͬ�����ϵ����߳��е���cb.await( )������
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
    
    public static class DoSomeThing implements Runnable{  //дһ��ʵ��Runnable�ӿڵ�class������ʵ�ֵ�һ���̵߳���ͬ������ǰ��ҵ���߼���
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
            System.out.println("�����̶߳��Ѿ��������,�����ﵽԤ��ֵ");  
            //mBarrier.reset();//�ָ�����ʼ��״̬          
        }  
    }  
  
    WalkTarget(){  
        //��ʼ��CyclicBarrier   
        mBarrier = new CyclicBarrier(mCount, new BarrierAction());  
        mExecutor = Executors.newFixedThreadPool(mCount);  
  
        for (int i = 0; i < mCount; i++){  
            //���������߳�   
            mExecutor.execute(new Walker(mBarrier, i));  
        }  
    }  
}  
  
//�����߳�   
class Walker implements Runnable{  
    private final CyclicBarrier mBarrier;  
    private final int mThreadIndex;  
  
    Walker(final CyclicBarrier barrier, final int threadIndex){  
        mBarrier = barrier;  
        mThreadIndex = threadIndex;  
    }  
  
    public void run(){  
        System.out.println("Thread " + mThreadIndex + " is running...");  
        // ִ������   
        try{  
            TimeUnit.MILLISECONDS.sleep(5000);  
            // do task   
        }catch (InterruptedException e){  
            e.printStackTrace();  
        }  
  
        // ��������Ժ󣬵ȴ������߳��������   
        try{  
            mBarrier.await();  
        }catch (InterruptedException e){  
            e.printStackTrace();  
        }catch (BrokenBarrierException e)   {  
            e.printStackTrace();  
        }  
        // �����߳���������Ժ�������������Լ���������������   
        System.out.println("Thread " + mThreadIndex + " do something else");  
    }  
  
}  
