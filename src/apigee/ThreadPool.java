package apigee;

class ThreadPool {
	 private ThreadPoolWithJobAffinity<Runnable> taskQueue;
	 private boolean poolShutDownInitiated = false;
	 public ThreadPool(int nThreads){
	        taskQueue = new ListThreadPoolWithJobAffinity<Runnable>(nThreads);
	        
	        
	        //Create and start nThreads number of threads.
	        for(int i=1; i<=nThreads; i++){
	        	ThreadFromPool threadPoolsThread=new ThreadFromPool(taskQueue,this);
	         threadPoolsThread.setName("Thread-"+i);
	        // System.out.println("Thread-"+i +" created in ThreadPool.");
	         threadPoolsThread.start();   //start thread
	        }
	       
    }
	 
	 public synchronized void  execute(Runnable task,String id) throws Exception{
	        if(this.poolShutDownInitiated)
	           throw new Exception("ThreadPool has been shutDown, no further tasks can be added");
	       
	        /*
	      * Add task in sharedQueue,
	      * and notify all waiting threads that task is available.  
	            */
	       // System.out.println("tasks has been added.");
	        this.taskQueue.submit(id,task);
	 } 
	 public boolean isPoolShutDownInitiated() {
         return poolShutDownInitiated;
     }
	 
	 public synchronized void shutdown(){
	       this.poolShutDownInitiated = true;
	       // System.out.println("ThreadPool SHUTDOWN initiated.");
	  }
	 
	 
}	 