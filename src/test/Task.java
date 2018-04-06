package test;

import org.fastdo.core.Factory;
import org.fastdo.core.StreamLine;
import org.wamson.utils.Timer;

public class Task {

	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();

		StreamLine sLine = new StreamLine(3);
		sLine.setWorkers(MyHeader.class);
		sLine.setWorkers(MyFormater.class);
		sLine.setWorkers(MyTailer.class);
		Factory.setStreamline(sLine);
		System.out.println("waiting...");
		Factory.start();
		
		Factory.waitComplete();
		System.out.println("耗时："+timer.elapseH());
	}
}
