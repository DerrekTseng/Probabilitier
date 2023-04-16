package pbb;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	public static void main(String[] args) {

		Probabilitier<String> testRandomer = ProbabilitierBuilder //
				.create(String.class) //
				.add("A", 10) //
				.add("B", 10) //
				.add("C", 10) //
				.add("D", 10) //
				.add("E", 10) //
				.add("F", 10) //
				.add("G", 10) //
				.add("H", 10) //
				.add("I", 10) //
				.add("J", 10) //
				.build(new SecureRandom());

		Map<String, AtomicInteger> record = new HashMap<>();
		record.put("A", new AtomicInteger(0));
		record.put("B", new AtomicInteger(0));
		record.put("C", new AtomicInteger(0));
		record.put("D", new AtomicInteger(0));
		record.put("E", new AtomicInteger(0));
		record.put("F", new AtomicInteger(0));
		record.put("G", new AtomicInteger(0));
		record.put("H", new AtomicInteger(0));
		record.put("I", new AtomicInteger(0));
		record.put("J", new AtomicInteger(0));

		for (int i = 0; i < 100000000; i++) {
			record.get(testRandomer.next()).addAndGet(1);
		}

		record.entrySet().forEach(entry -> {
			System.out.println(String.format("%s:%s", entry.getKey(), entry.getValue().get()));
		});

	}

}
