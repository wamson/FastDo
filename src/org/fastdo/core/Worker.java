package org.fastdo.core;

public abstract class Worker<T,U> {
	public void init() throws Exception  {};
	public abstract void work(T value, Context<U> context) throws Exception;
	public void cleanUp() throws Exception {};
}
