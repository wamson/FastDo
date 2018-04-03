package org.fastdo.core;

import java.util.LinkedList;

/**
 * 流水线类，根据存储的worker顺序来配置工作流水线
 * 
 * @author seven
 *
 */
public final class StreamLine {
	private boolean initCompleted = false;
	
	private int coreThreadSize = 3;
	private int tailWorkerSize = 1;

	private LinkedList<Class<? extends Worker>> workerClassList = new LinkedList<Class<? extends Worker>>();
	private LinkedList<Context> contextList = new LinkedList<Context>();

	private Worker header = null;
	
	public StreamLine() {
		this(3);
	}
	
	public StreamLine(int coreThreadSize) {
		this.coreThreadSize = coreThreadSize;
	}
	
	public void setTailWorkerSize(int size) {
		this.tailWorkerSize = size;
	}

	/**
	 * 记录下每个工序所有用到的worker worker存储的顺序即为工序的顺序，按照顺序存储，第一个为header，最后一个是tailer
	 * 
	 * @param workerClass
	 */
	public void setWorkers(Class<? extends Worker> workerClass) {
		workerClassList.add(workerClass);
	}

	/**
	 * 记录下每个工序所有用到的worker worker存储的顺序即为工序的顺序，按照顺序存储，第一个为header，最后一个是tailer
	 * 
	 * @param workerClasses
	 */
	public void setWorkers(Class<? extends Worker>... workerClasses) {
		for (Class<? extends Worker> workerClass : workerClasses) {
			workerClassList.add(workerClass);
		}
	}

	/**
	 * 流水线初始化
	 * 
	 * @throws Exception
	 */
	public boolean init() throws Exception {
		if (!initCompleted) {
			if (workerClassList.size() <= 1) {
				throw new Exception("初始化错误，少于两个worker!");
			} else {
				WorkersPool tailWorkersPool = new WorkersPool<>(tailWorkerSize, null, workerClassList.getLast());
				Context tailContext = new Context(null, tailWorkersPool);
				contextList.push(tailContext);
				Context nextContext = tailContext;
				// 创建context，并为每个context创建workerPool
				for (int i = workerClassList.size()-2; i > 0; i--) {
					// 创建workersPool
					WorkersPool workersPool = new WorkersPool(coreThreadSize, nextContext, workerClassList.get(i));
					// 创建context
					Context context = new Context<>(nextContext, workersPool);
					contextList.push(context);
					
					nextContext = context;
				}
				
				// 创建worker实例
				header = workerClassList.get(0).newInstance();
			}
			initCompleted = true;
		}
		return initCompleted;
	}

	/**
	 * 启动流水线进行工作
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		try {
			if (!initCompleted) {
				init();
			}
			Context context = contextList.get(0);
			header.work(null, context);
			context.write(FinishMeg.get());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
