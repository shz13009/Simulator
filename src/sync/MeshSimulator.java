package sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class MeshSimulator {
	/**
	 * @param args
	 */

	static int sampleSize;
	static int sampleTimes;
	static int sampleLength;
	static double commuR;
	static double density;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		density = 1;
		sampleTimes = 10000;
		sampleLength = 50;

		Sample(100, 1.8);
		Sample(100, 1.9);
		Sample(100, 2);
		Sample(100, 2.5);
		Sample(100, 3);
		Sample(100, 4);
		Sample(100, 5);
		Sample(100, 6);
		Sample(100, 7);

	}

	public static void Sample(int sampleS, double commu) {

		commuR = commu;

		sampleSize = sampleS;

		List<Node> nodes;

		List<Integer> seq = new ArrayList<Integer>(sampleSize);

		double dimension = Math.sqrt(sampleSize / density);

		int count = 0;

		double maxTimeDiff = 0;

		double aveTimeDiff = 0;

		double aveTempMax = 0;

		int maxLength = 0;

		double aveTempLength = 0;

		Random random = new Random();

		for (int i = 1; i < sampleSize; i++) {
			seq.add(i);
		}

		while (count < sampleTimes) {

			/**
			 * try to initial one sample
			 */
			nodes = new ArrayList<Node>(sampleSize);
			for (int i = 0; i < sampleSize; i++) {

				double[] location = new double[] {
						random.nextDouble() * dimension,
						random.nextDouble() * dimension };

				nodes.add(new Node("n" + i, ClockDriftCreater
						.ExtremeClockDriftCreater(40D), 0, 1000000D / 40D,
						location));

				if (i == 0) {
					location = new double[] { dimension / 2, dimension / 2 };
					nodes.get(0).setLocation(location);
					nodes.get(i).setConnected(true);

				} else if (i > 0) {
					for (int j = 0; j < i; j++) {
						if (nodes.get(j).getDistanceFrom(nodes.get(i)) < commuR) {
							nodes.get(j).addNeighbour(nodes.get(i));
							nodes.get(i).addNeighbour(nodes.get(j));
							if (nodes.get(i).isConnected()) {
								nodes.get(j).setConnected(true);
							}
							if (nodes.get(j).isConnected()) {
								nodes.get(i).setConnected(true);
							}
						}
					}
				}
			}

			/**
			 * test the connectivity
			 * 
			 */
			boolean flag = true;
			for (int i = 0; i < sampleSize; i++) {
				if (!nodes.get(i).isConnected()) {
					flag = false;
				}
			}
			if (!flag) {
				continue;
			}

			/**
			 * sample is connected
			 */
			count++;

			/**
			 * 
			 */
			Queue<Node> q = new LinkedList<Node>();
			q.add(nodes.get(0));
			nodes.get(0).setLength(0);
			int tempLength = ParentFinder.parentFinder(q);
			maxLength = maxLength < tempLength ? tempLength : maxLength;
			aveTempLength += tempLength;

			aveTempMax = 0;
			double max = 0;
			double min = 0;

			for (int i = 0; i < sampleLength; i++) {

				nodes.get(0).beforeSync();
				min = nodes.get(0).time;
				max = nodes.get(0).time;
				for (int j = 1; j < nodes.size(); j++) {
					nodes.get(j).beforeSync();
					min = min < nodes.get(j).time ? min : nodes.get(j).time;
					max = max > nodes.get(j).time ? max : nodes.get(j).time;
				}

				double diff2 = max - min;
				if (maxTimeDiff < diff2) {
					maxTimeDiff = diff2;
				}
				if (aveTempMax < diff2) {
					aveTempMax = diff2;
				}

				Collections.shuffle(seq);

				for (int j = 0; j < nodes.size() - 1; j++) {
					nodes.get(seq.get(j)).randomSyncWithLocation();

					for (int k = 0; k < nodes.size(); k++) {
						double diff = 0;
						diff = nodes.get(seq.get(j)).time - nodes.get(k).time;
						diff = (diff > 0) ? diff : -diff;

						// System.out.println(seq.get(j) + "        " + k);
						if (maxTimeDiff < diff) {
							maxTimeDiff = diff;
						}
						if (aveTempMax < diff) {
							aveTempMax = diff;
						}
					}
				}
			}
			aveTimeDiff += aveTempMax;
			// System.out.println(count);
		}
		aveTimeDiff /= sampleTimes;
		aveTempLength /= sampleTimes;
		System.out.println(commuR);
		System.out.println(aveTimeDiff);
		System.out.println(maxTimeDiff);
		System.out.println(aveTempLength);
		System.out.println(maxLength);
		System.out.println();
	}
}
