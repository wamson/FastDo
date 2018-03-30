package test;

import org.fastdo.core.Factory;
import org.fastdo.core.StreamLine;
import org.wamson.utils.Timer;

public class Task {

	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();
		StreamLine sLine = new StreamLine();
		sLine.setWorkers(MyHeader.class);
		sLine.setWorkers(MyWorker.class);
		sLine.setWorkers(Mytailer.class);
		Factory.setStreamline(sLine);
		Factory.start();
		
		Factory.waitComplete();
		System.out.println("耗时："+timer.elapseH());
	}
}
