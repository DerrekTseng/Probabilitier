package pbb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProbabilitierBuilder<T> {

	private final List<T> objs = new ArrayList<>();
	private final List<Double> probabilities = new ArrayList<>();

	private ProbabilitierBuilder() {

	}

	public static <T> ProbabilitierBuilder<T> create(Class<T> clazz) {
		return new ProbabilitierBuilder<T>();
	}

	public ProbabilitierBuilder<T> add(T t, double probabiliy) {
		if (probabiliy > 100) {
			throw new RuntimeException("probability is greater than 100");
		} else if (probabiliy <= 0) {
			throw new RuntimeException("probability is less than or equal to 0");
		}

		objs.add(t);
		probabilities.add(probabiliy);
		return this;
	}

	public ProbabilitierBuilder<T> fillRemaining(T t) {
		double sum = probabilities.stream().collect(Collectors.summingDouble(Double::doubleValue));
		if (sum > 100) {
			throw new RuntimeException("total probability is greater than or equal to 100, value=" + Double.toString(sum));
		} else if (sum == 100) {
			return this;
		}
		return add(t, BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(sum)).doubleValue());
	}

	public Probabilitier<T> build(Random random) {

		double sum = probabilities.stream().collect(Collectors.summingDouble(Double::doubleValue));

		if (sum > 100) {
			throw new RuntimeException("total probability is greater than 100, value=" + Double.toString(sum));
		} else if (sum < 100) {
			throw new RuntimeException("total probability is less 100, value=" + Double.toString(sum));
		}

		AtomicLong conter = new AtomicLong(0);

		List<Long[]> ranges = new ArrayList<>();

		int decimalPlaces = 0;
		for (Double d : probabilities) {
			String[] splited = d.toString().split("[.]");
			if (splited.length == 2) {
				int places = splited[1].length();
				if (places > decimalPlaces) {
					decimalPlaces = places;
				}
			}
		}

		int multiplier = 1;

		for (int i = 0; i < decimalPlaces; i++) {
			multiplier *= 10;
		}
		final int finalMultiplier = multiplier;

		probabilities.stream().map(d -> {
			return (long) (d * finalMultiplier);
		}).forEach(i -> {
			Long[] range = new Long[2];
			range[0] = conter.get();
			range[1] = conter.addAndGet(i);
			ranges.add(range);
		});

		return new Probabilitier<T>(random, conter.get(), ranges, objs);
	}

}
