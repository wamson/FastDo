package org.wamson.utils;

public class Timer {
	private long ftime = -1L;

	public Timer() {
		ftime = System.currentTimeMillis();
	}

	public long elapse() {
		return (System.currentTimeMillis() - ftime) / 1000;
	}

	public String elapseH() {
		long ltime = System.currentTimeMillis();
		long elapseMiliSeconds = ltime - ftime;
		long hours = elapseMiliSeconds / 1000 / 60 / 60;
		elapseMiliSeconds = elapseMiliSeconds - hours * 1000 * 60 * 60;
		int mins = (int) (elapseMiliSeconds / 1000 / 60);
		elapseMiliSeconds = elapseMiliSeconds - mins * 1000 * 60;
		int secs = (int) (elapseMiliSeconds / 1000);
		elapseMiliSeconds = elapseMiliSeconds - secs * 1000;
		int milisecs = (int) elapseMiliSeconds;
		return new StringBuilder().append(hours).append("h").append(mins).append("m").append(secs).append("s")
				.append(milisecs).append("ms").toString();
	}
	
	public void reset() {
		ftime = System.currentTimeMillis();
	}

}
