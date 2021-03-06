package org.fastdo.core;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WorkersPool<M> {
	private int coreWorkersSize = 1;
	private int megQueueSize = Integer.MAX_VALUE;
	private Class<? extends Worker> workerClazz;

	// 线程跳出循环，并停止的标志
	volatile boolean allCompleted = false;

	// 存储worker队列
	private ArrayList<Thread> workersList = null;
	// 存储消息队列
	private LinkedBlockingQueue<M> megQueue = null;

	// 下一个工序的context
	private Context nextContext;

	private WorkersPool() {
	}
	
	private Context getNextContext() {
		return nextContext;
	}

	public WorkersPool(Context nextContext, Class<? extends Worker> clazz) throws InterruptedException {
		this(1, nextContext, clazz);
	}

	public WorkersPool(int coreWorkersSize, Context nextContext, Class<? extends Worker> clazz)
			throws InterruptedException {
		this(coreWorkersSize, Integer.MAX_VALUE, nextContext, clazz);
	}

	public WorkersPool(int coreWorkersSize, int megQueueSize, final Context nextContext, Class<? extends Worker> clazz)
			throws InterruptedException {
		this.coreWorkersSize = coreWorkersSize;
		workersList = new ArrayList<Thread>(coreWorkersSize);
		megQueue = new LinkedBlockingQueue<M>(megQueueSize);
		this.nextContext = nextContext;
		this.workerClazz = clazz;

		// 初始化各个worker线程
		for (int i = 0; i < coreWorkersSize; i++) {
			workersList.add(new Thread() {
				@Override
				public void run() {
					try {
						Worker worker = workerClazz.newInstance();
						worker.init();
						for (;;) {
							M meg = null;
							while (!allCompleted) {
								meg = megQueue.poll(500, TimeUnit.MILLISECONDS);
								if (meg != null) {
									break;
								}
							}
							if (meg == FinishMeg.get()) {
								allCompleted = true;
								// 启动监测线程
								createSuperviseThread();
								// 跳出线程的循环。
								break;
							} else if (meg != null) {
								worker.work(meg, nextContext);
							// meg为空，allCompleted为真，该worker线程结束
							} else {
								break;
							}
						}
						worker.cleanUp();
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(-1);
					}
				}
			});
		}

		// 启动各个worker线程
		for (Thread t : workersList) {
			t.start();
		}
	}

	public void put(M meg) throws InterruptedException {
		megQueue.put(meg);
	}

	public boolean anyWorkerAlive() {
		boolean flag = workersList.get(0).isAlive();
		for (int i = 1; i < workersList.size(); i++) {
			flag = flag | workersList.get(i).isAlive();
		}
		return flag;
	}

	// 启动一个线程，当所有的线程都处理完数据后，发送finishMeg给下一个工序
	private void createSuperviseThread() {
		new Thread() {
			private WorkersPool workersPool;

			public Thread accept(WorkersPool w) {
				this.workersPool = w;
				return this;
			}

			public void run() {
				// 如果是最后一个工序，则告诉fatory线程已全部完成。
				if (this.workersPool.getNextContext() == null) {
					synchronized (Factory.class) {
						Factory.class.notify();
					}
				} else {
					while (this.workersPool.anyWorkerAlive()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 所有的线程都跑完了，发送结束信息给下一个工序
					try {
						this.workersPool.getNextContext().write(FinishMeg.get());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.accept(this).start();
	}
}
