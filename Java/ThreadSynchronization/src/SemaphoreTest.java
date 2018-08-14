import java.util.concurrent.Semaphore;


/**
 * Semaphore（信号量）、、、公共资源有限而并发线程较多的场景
 * 
 * Semaphore用于流量控制，用于控制访问特定资源的线程数量，通过协调各个线程，以保证合理的使用公共资源。
 * 
 * Semaphore的使用方法：
 * （1）创建一个Semaphore对象，并传入一个int类型的参数，初始化通行证数量；
 * （2）在要占用公共资源的子线程业务逻辑前，调用s.acquire( )方法获得通行证，在实现业务逻辑后，调用release（）方法释放通行证；
 * 
 * boolean  tryAcquire（）尝试获取通行证；
 * int  getQueueLength(  )获取等待队列（线程）的长度；
 * int  availablePermits（）获取剩余可用的通行证数量；
 * boolean  QueuedThreads(  )是否有线程在等待获取通行证；
 * void  reducePermits(int reduction)减少通行证数量，这是一个protedcted方法；
 * Cillection  getQueuedThreads(  )获取等待获取通行证的线程集合，这是个protected方法；
 */
public class SemaphoreTest {

	static Semaphore s = new Semaphore(5);  //创建一个Semaphore对象，并传入一个int类型的参数，初始化通行证数量；
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<10; i++){
            new Thread(new Runnable() {
                public void run() {
                    try{
                        s.acquire(); //在要占用公共资源的子线程业务逻辑前，调用s.acquire( )方法获得通行证
                        System.out.println(Thread.currentThread() + "is saving data    " + "availablePermits:" + s.availablePermits() + "   getQueueLength:" + s.getQueueLength());
                        Thread.sleep(2000);
                        s.release();  //在实现业务逻辑后，调用release（）方法释放通行证；
                    }catch(Exception e){
                        System.out.println("thread 1");
                    }
 
                }
            }).start();
        }
 
    }


}
