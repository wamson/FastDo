package org.fastdo.core;

public class FinishMeg {
	private static FinishMeg obj = null;

	public static FinishMeg get() {
		synchronized (FinishMeg.class) {
			if (obj == null) {
				obj = new FinishMeg();
			}
			return obj;
		}
	}
}
