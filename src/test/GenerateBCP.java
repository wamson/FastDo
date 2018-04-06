package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class GenerateBCP {
	public static void main(String[] args) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/seven/Downloads/FastDoTest/console.log", false), "UTF-8"));
		Random random = new Random();
		
		for(int i = 0; i < 500000; i++) {
			bw.write(new StringBuilder().append("sdfasifnaksdfhasdljfsa").append("|").append(13808899888L+random.nextInt(100000))
					.append("|").append(Math.round(System.currentTimeMillis()/1000-Math.random()*100000000)).append("|backup|test|广州市是都会发 i ID是烦去水淀粉阿水淀粉阿迪发货").toString());
			bw.newLine();
		}
		
		
		bw.close();
	}
}
