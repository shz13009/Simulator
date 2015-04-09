package sync;

import java.util.Random;

public class ClockDriftCreater {

	public static double RandomClockDriftCreater(double clockDrift) {
		Random random = new Random();
		return random.nextDouble() * clockDrift * 2 - clockDrift;

	}

	public static double ExtremeClockDriftCreater(double clockDrift) {
		Random random = new Random();

		return random.nextInt(2) == 1 ? clockDrift : -clockDrift;

	}

	public static double NormalClockDriftCreater(double clockDrift) {
		Random random = new Random();
		return random.nextGaussian() * clockDrift * 2 - clockDrift;

	}

}
