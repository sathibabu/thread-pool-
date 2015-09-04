package apigee;


public interface ThreadPoolWithJobAffinity <E>{
	int poolSize();
	void submit(String jobId, E job) throws InterruptedException ;
	hashTab<E> take()  throws InterruptedException;
	//void shutdown();

}
