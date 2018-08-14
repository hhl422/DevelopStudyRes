import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Exchanger�������ߣ���������Ҫ���ݽ�������ĳ����������Ŵ��㷨�У���Ҫѡ�������������䣬�������˵����ݲ����ݽ����������õ�������������������У�Թ��������������̵߳����ݣ�����У�������̵߳������Ƿ����
 *
 * Exchanger��һ���̼߳��ṩ���ݽ������ܵ�д�����ߣ����ṩ��һ��ͬ���㣬�����ͬ���㣬�����߳̿��Խ����˴˵����ݡ��̼߳�ͨ������excahange���������������ݣ������һ���߳��ȵ���ͬ���㣬ִ��exchange��������ô����һֱ��ͬ����ȴ��ڶ����̵߳���ͬ���㣬�ڶ����߳�Ҳִ��exchange��������ʱ�����̶߳�����ͬ���㣬���Խ����˴˵����ݡ�
 * ʹ�ã�
 * ��1������һ��Exchanger����
 * ��2����Ҫ������ͬ�������ݵ�ͬ�������excr.exchange(  )����
 */
public class ExchangerTest {

	
    public static void main(String[] args) throws InterruptedException {
        
//    	Test1();
    	
    	new ExchangerDemo();
    }
    
    
    private static void Test1(){
    	final  Exchanger<String> exc = new Exchanger<String>();  //����һ��Exchanger����
    	new Thread(new Runnable() {
	        public void run() {
	            try{
	                String A= "������ˮA";
	                String B = exc.exchange(A); //��Ҫ������ͬ�������ݵ�ͬ�������excr.exchange(  )����
	                System.out.println("A���ӽǣ� A��B��ˮ�Ƿ�һ�£�" + A.equals(B) + "   A¼����ǣ�" + A + "   B¼����ǣ�" + B);
	
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	        }
    	}).start();

	    new Thread(new Runnable() {
	        public void run() {
	            try{
	                String B= "������ˮB";
	                String A = exc.exchange(B);  //��Ҫ������ͬ�������ݵ�ͬ�������excr.exchange(  )����
	                System.out.println("B���ӽǣ� A��B��ˮ�Ƿ�һ�£�" + A.equals(B) + "   A¼����ǣ�" + A + "   B¼����ǣ�" + B);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	        }
	    }).start();

	//    console��
	//    B���ӽǣ� A��B��ˮ�Ƿ�һ�£�false   A¼����ǣ�������ˮA   B¼����ǣ�������ˮB
	//    A���ӽǣ� A��B��ˮ�Ƿ�һ�£�false   A¼����ǣ�������ˮA   B¼����ǣ�������ˮB
    }
}


class ExchangerDemo {
	
	ExchangerDemo(){
ExecutorService executor = Executors.newCachedThreadPool();
        
        final Exchanger exchanger = new Exchanger();
        executor.execute(new Runnable() {
            String data1 = "������ɭ��С������˹";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
 
        executor.execute(new Runnable() {
            String data1 = "�����";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.execute(new Runnable() {
            String data1 = "����˹";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.execute(new Runnable() {
            String data1 = "����������˹������";
 
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        
        executor.shutdown();
	}
 
    private static void nbaTrade(String data1, Exchanger exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "�ڽ��׽�ֹ֮ǰ�� " + data1 + " ���׳�ȥ");
            Thread.sleep((long) (Math.random() * 1000));
 
            String data2 = (String) exchanger.exchange(data1);
            System.out.println(Thread.currentThread().getName() + "���׵õ�" + data2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

