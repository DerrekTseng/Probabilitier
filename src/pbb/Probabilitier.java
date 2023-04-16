package pbb;

import java.util.List;
import java.util.Random;

public class Probabilitier<T> {

	private final List<Long[]> ranges;
	private final List<T> objs;
	private final long maxValue;

	private volatile Random random;

	protected Probabilitier(Random random, long maxValue, List<Long[]> ranges, List<T> objs) {
		this.objs = objs;
		this.ranges = ranges;
		this.maxValue = maxValue;
		this.random = random;
	}

	public T next() {

		Long[] range;
		long min;
		long max;

		final long rlong = (long) (random.nextDouble() * maxValue);
		final int size = ranges.size();

		T obj = null;

		for (int i = 0; i < size; i++) {
			range = ranges.get(i);
			min = range[0];
			max = range[1];
			if (min <= rlong && rlong < max) {
				obj = objs.get(i);
				break;
			}
		}
		return obj;
	}

}
