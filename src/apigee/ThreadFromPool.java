package apigee;

//import java.awt.List;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;



public class ThreadFromPool extends Thread {
	private ThreadPoolWithJobAffinity<Runnable> taskQueue;
	private ThreadPool threadPool;
	public static Map<String, String> threadSafeMap = null;
	public static Hashtable<String, Queue<hashTab>> thread_holder= null;
	//public static List<Hashtable<String, String>> outer= null;
	
    public ThreadFromPool(ThreadPoolWithJobAffinity<Runnable> queue,
                  ThreadPool threadPool){
        taskQueue = queue;
        this.threadPool=threadPool;
        this.threadSafeMap = new Hashtable<String, String>();
        this.thread_holder = new Hashtable<String, Queue<hashTab>>();
       
    }
    public void run() {
        try {
             
               while (true) {    
                     hashTab ht = taskQueue.take();
                     Runnable runnable = (Runnable) ht.getR();
                     String id =  ht.getId();
                     
                     
                     if(threadSafeMap.containsKey(id) ){
                     	if((Thread.currentThread().getName()).equals(threadSafeMap.get(id))){
                     	//	System.out.println("found");
                       	 runnable.run();
                     	 System.out.println("job id: "+id +" finished by"+ Thread.currentThread().getName());
                     	}else{
                     		taskQueue.submit(id,runnable);
                     	}
                     }else{
                     	threadSafeMap.put(id, Thread.currentThread().getName());
                     	 runnable.run();
                     	 System.out.println("job id: "+id +" finished by"+ Thread.currentThread().getName());
                     }
                     
                     if(this.threadPool.isPoolShutDownInitiated()
                                   &&  this.taskQueue.poolSize()==0){
                            this.interrupt();
                            Thread.sleep(1);  
                     }   
               }
        } catch (Exception e) {
               //System.out.println(Thread.currentThread().getName()+" has been STOPPED.");
        }
 }
	
}

class Task implements Runnable{  
    @Override
    public void run() {
           try {
                  Thread.sleep(2000);
              
           } catch (InterruptedException e) {
                  e.printStackTrace();
           }
    }
};
