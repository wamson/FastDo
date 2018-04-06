package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.fastdo.core.Context;
import org.fastdo.core.Worker;

public class MyTailer extends Worker<String[], Object> {
	// private static BufferedWriter bw = null;
	private static File resultDir = new File("/Users/seven/Downloads/FastDoTest/result");
	private static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	private static HashMap<String, BufferedWriter> map = new HashMap<>();
	private static long date20180101 = 1514736000L;
	private static StringBuilder sb = new StringBuilder();

	// @Override
	// public void init() {
	// try {
	// bw = new BufferedWriter(new FileWriter(new
	// File("/Users/seven/Downloads/FastDoTest/result")));
	// } catch (IOException e) {
	// e.printStackTrace();
	// bw = null;
	// }
	// }

	@Override
	public void work(String[] value, Context<Object> context) throws Exception {
		// System.out.println("Tailer work(["+value+"])");
		// String[] splits = value.split(String.valueOf((char)1));

		sb.setLength(0);
		String[] splits = value;
		long nowDate = Long.valueOf(splits[2]);
		String date = SimpleDateFormat.format(new Date(nowDate * 1000));
		if (nowDate < date20180101) {
			date = "1111";
		}
		for (int i = 0; i < splits.length - 1; i++) {
			sb.append(splits[i]).append(String.valueOf((char) 1));
		}
		sb.append(splits[splits.length - 1]);

		BufferedWriter bw = map.get(date);

		if (bw == null) {
			bw = new BufferedWriter(
					new FileWriter(new File(resultDir.getAbsolutePath() + File.separator + date + ".bcp")));
			map.put(date, bw);
			bw.write(sb.toString());
		} else {
			bw.newLine();
			;
			bw.write(sb.toString());
		}

	};

	public void cleanUp() {
		try {
			for (BufferedWriter bw : map.values()) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
