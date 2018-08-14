import java.util.concurrent.Semaphore;


/**
 * Semaphore���ź�����������������Դ���޶������߳̽϶�ĳ���
 * 
 * Semaphore�����������ƣ����ڿ��Ʒ����ض���Դ���߳�������ͨ��Э�������̣߳��Ա�֤�����ʹ�ù�����Դ��
 * 
 * Semaphore��ʹ�÷�����
 * ��1������һ��Semaphore���󣬲�����һ��int���͵Ĳ�������ʼ��ͨ��֤������
 * ��2����Ҫռ�ù�����Դ�����߳�ҵ���߼�ǰ������s.acquire( )�������ͨ��֤����ʵ��ҵ���߼��󣬵���release���������ͷ�ͨ��֤��
 * 
 * boolean  tryAcquire�������Ի�ȡͨ��֤��
 * int  getQueueLength(  )��ȡ�ȴ����У��̣߳��ĳ��ȣ�
 * int  availablePermits������ȡʣ����õ�ͨ��֤������
 * boolean  QueuedThreads(  )�Ƿ����߳��ڵȴ���ȡͨ��֤��
 * void  reducePermits(int reduction)����ͨ��֤����������һ��protedcted������
 * Cillection  getQueuedThreads(  )��ȡ�ȴ���ȡͨ��֤���̼߳��ϣ����Ǹ�protected������
 */
public class SemaphoreTest {

	static Semaphore s = new Semaphore(5);  //����һ��Semaphore���󣬲�����һ��int���͵Ĳ�������ʼ��ͨ��֤������
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<10; i++){
            new Thread(new Runnable() {
                public void run() {
                    try{
                        s.acquire(); //��Ҫռ�ù�����Դ�����߳�ҵ���߼�ǰ������s.acquire( )�������ͨ��֤
                        System.out.println(Thread.currentThread() + "is saving data    " + "availablePermits:" + s.availablePermits() + "   getQueueLength:" + s.getQueueLength());
                        Thread.sleep(2000);
                        s.release();  //��ʵ��ҵ���߼��󣬵���release���������ͷ�ͨ��֤��
                    }catch(Exception e){
                        System.out.println("thread 1");
                    }
 
                }
            }).start();
        }
 
    }


}
