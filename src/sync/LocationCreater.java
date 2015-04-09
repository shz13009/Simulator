package sync;

import java.util.Random;

public class LocationCreater {

	public static double[] getLocation(int sampleSize, double density) {
		double[] location = new double[2];
		Random random = new Random();
		location[0] = random.nextDouble()
				* Math.sqrt(((double) sampleSize / density));
		location[1] = random.nextDouble()
				* Math.sqrt(((double) sampleSize / density));
		return location;
	}
}
