package apigee;

//import api_gee_tests2.Task;
//import api_gee_tests2.ThreadPool;

public class ThredPoolTest {
	public static void main(String[] args) throws Exception {
        ThreadPool threadPool=new ThreadPool(2); //create 2 threads in ThreadPool 
        Runnable task=new Task();
        
        threadPool.execute(task,"1");
        threadPool.execute(task,"2");
        threadPool.execute(task,"2");
        threadPool.execute(task,"2");
        threadPool.execute(task,"1");
        
        threadPool.shutdown();
        
 }
}
