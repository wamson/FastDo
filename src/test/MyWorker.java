package test;


import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class MyWorker extends Worker<String, String>{

	@Override
	public void work(String value, Context<String> context) throws Exception {
		context.write(value+"_test");
	}
}
