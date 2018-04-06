package org.fastdo.core;

public final class Context<T> {
	// 下一个工序的Context
	private Context nextContext = null;

	private WorkersPool<T> workersPool = null;
	
	public Context(Context nextContext, WorkersPool workersPool) {
		this.nextContext = nextContext;
		this.workersPool = workersPool;
	}
	
	public void setNextContext(Context context) {
		nextContext = context;
	}
	
	public void write(T t) throws Exception {
		workersPool.put(t);
	}
}
