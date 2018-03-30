package test;

import java.io.File;

import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class MyHeader extends Worker<String, String>{

	@Override
	public void work(String value, Context<String> context) throws Exception {
		File file = new File("/Users/seven/workspace");
		for(File f: file.listFiles()) {
			if(f.isDirectory()) {
				context.write(f.getName());
			}
		}
	}
}
