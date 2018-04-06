package test;

import java.io.File;

import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class MyHeader extends Worker<String, File> {

	@Override
	public void work(String value, Context<File> context) throws Exception {
		File file = new File("/Users/seven/Downloads/FastDoTest");
		for (File f : file.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".bcp")) {
				context.write(f);
			}
		}
	}
}
