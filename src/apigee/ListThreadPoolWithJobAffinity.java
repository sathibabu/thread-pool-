package apigee;

import java.util.LinkedList;
import java.util.List;

class hashTab<E>{
	private String id;
	private E item;
	hashTab(E item, String id){
		this.id = id;
		this.item = item;
	}
	
	public E getR(){
		return item;
	}
	
	public String getId(){
		return id;
	}
	
} 

 class ListThreadPoolWithJobAffinity <E> implements ThreadPoolWithJobAffinity <E>{
	 private List<hashTab> queue; //Blocking queue
     private int  Size ; //maximum number of elements queue can hold at a time.
     
     public ListThreadPoolWithJobAffinity(int Size){
         this.Size = Size;
         queue = new LinkedList<hashTab>();
        
     }
     
     public synchronized int poolSize() {
         return queue.size();
     }
     
     public synchronized void submit(String jobId, E job)  throws InterruptedException  {
    	
   	  hashTab ht = new hashTab(job,jobId);
   	
       if (queue.size() == Size) {
          this.wait();
       }
       
       
          queue.add(ht);
          this.notifyAll();
     }
     
     public synchronized hashTab take()  throws InterruptedException{
               if (queue.size() == 0) {
                   this.wait();
               }
               this.notifyAll();
         return queue.remove(0);
            
      }

}
