package org.fastdo.core;

public class Factory {
	private Factory() {
	}

	// private static LinkedList<StreamLine> streamLineList = new
	// LinkedList<StreamLine>();
//	private static Class<StreamLine> clazzStreamLine = null;
	private static StreamLine streamLine = null;

	private static boolean initCompleted = false;

	public static boolean init() {
		// 初始化streamline
		if (!initCompleted) {
			try {
				streamLine.init();
			} catch (Exception e) {
				System.err.println("init failed!!!");
				e.printStackTrace();
				System.exit(-1);
			}
			initCompleted = true;
		}

		return initCompleted;
	}

	public static void setStreamline(StreamLine sLine) {
		streamLine = sLine;
	}

	public static void start() throws Exception {
		if (!initCompleted) {
			init();
		}
		streamLine.start();
	}

	public static void waitComplete() throws InterruptedException {
		// 等待所有的任务都完成了才能返回
		System.out.println("waiting...");
		synchronized (Factory.class) {
			Factory.class.wait();
		}
		System.out.println("finished!!!");
	}
}
