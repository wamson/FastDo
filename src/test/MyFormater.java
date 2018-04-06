package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class MyFormater extends Worker<File, String[]>{

	@Override
	public void work(File value, Context<String[]> context) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(value));
		String line;
		while((line=br.readLine())!=null) {
			String[] splits = line.split("\\|");
			if(splits.length==6) {
				context.write(splits);
			}
		}
		br.close();
	}
}
