package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class Mytailer extends Worker<String, Object>{
	private static BufferedWriter bw = null;

	@Override
	public void init() {
		try {
			bw = new BufferedWriter(new FileWriter(new File("/Users/seven/workspace/console.log")));
		} catch (IOException e) {
			e.printStackTrace();
			bw = null;
		}
	}

	@Override
	public void work(String value, Context<Object> context) throws Exception {
		bw.write(value);
		bw.newLine();
	};

	public void cleanUp() {
		try {
			if (bw != null) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
