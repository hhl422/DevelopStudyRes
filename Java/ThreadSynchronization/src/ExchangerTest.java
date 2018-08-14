import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Exchanger（交换者）、、、需要数据交换共享的场景，例如遗传算法中，需要选择两个人来交配，交换两人的数据并根据交换规则来得到交配结果，再例如用于校对工作，交换两个线程的数据，用于校对两个线程的数据是否相等
 *
 * Exchanger是一个线程间提供数据交换功能的写作工具，他提供了一个同步点，在这个同步点，两个线程可以交换彼此的数据。线程间通过调用excahange（）方法交换数据，如果第一个线程先到达同步点，执行exchange方法，那么他会一直在同步点等待第二个线程到达同步点，第二个线程也执行exchange方法，这时两个线程都到达同步点，可以交换彼此的数据。
 * 使用：
 * （1）创建一个Exchanger对象；
 * （2）在要交换（同步）数据的同步点调用excr.exchange(  )方法
 */
public class ExchangerTest {

	
    public static void main(String[] args) throws InterruptedException {
        
//    	Test1();
    	
    	new ExchangerDemo();
    }
    
    
    private static void Test1(){
    	final  Exchanger<String> exc = new Exchanger<String>();  //创建一个Exchanger对象；
    	new Thread(new Runnable() {
	        public void run() {
	            try{
	                String A= "银行流水A";
	                String B = exc.exchange(A); //在要交换（同步）数据的同步点调用excr.exchange(  )方法
	                System.out.println("A的视角： A、B流水是否一致：" + A.equals(B) + "   A录入的是：" + A + "   B录入的是：" + B);
	
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	        }
    	}).start();

	    new Thread(new Runnable() {
	        public void run() {
	            try{
	                String B= "银行流水B";
	                String A = exc.exchange(B);  //在要交换（同步）数据的同步点调用excr.exchange(  )方法
	                System.out.println("B的视角： A、B流水是否一致：" + A.equals(B) + "   A录入的是：" + A + "   B录入的是：" + B);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	        }
	    }).start();

	//    console：
	//    B的视角： A、B流水是否一致：false   A录入的是：银行流水A   B录入的是：银行流水B
	//    A的视角： A、B流水是否一致：false   A录入的是：银行流水A   B录入的是：银行流水B
    }
}


class ExchangerDemo {
	
	ExchangerDemo(){
ExecutorService executor = Executors.newCachedThreadPool();
        
        final Exchanger exchanger = new Exchanger();
        executor.execute(new Runnable() {
            String data1 = "克拉克森，小拉里南斯";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
 
        executor.execute(new Runnable() {
            String data1 = "格里芬";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.execute(new Runnable() {
            String data1 = "哈里斯";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.execute(new Runnable() {
            String data1 = "以赛亚托马斯，弗莱";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.shutdown();
	}
 
    private static void nbaTrade(String data1, Exchanger exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");
            Thread.sleep((long) (Math.random() * 1000));
 
            String data2 = (String) exchanger.exchange(data1);
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

