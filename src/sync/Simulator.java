/**
 * 
 */
package sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Shaobo Zheng
 * 
 */
public class Simulator {

	/**
	 * @param args
	 */

	static int sampleSize;
	static int sampleTimes;
	static int sampleLength;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sample(5);
		Sample(6);
		Sample(7);
		Sample(8);
		Sample(9);
		Sample(10);
		Sample(20);
		Sample(30);
		Sample(40);
		Sample(50);

		Sample(60);
		Sample(70);
		Sample(80);
		Sample(90);
		Sample(100);

	}

	public static void Sample(int sampleS) {

		sampleSize = sampleS;
		sampleTimes = 1000000;
		sampleLength = 500;

		List<Node> nodes;

		List<Node> parent;

		List<Integer> seq = new ArrayList<Integer>(sampleSize);
		for (int i = 1; i < sampleSize; i++) {
			seq.add(i);
		}

		int count = 0;
		double maxTimeDiff = 0;
		double aveTimeDiff = 0;
		double aveTempMax = 0;

		while (count < sampleTimes) {

			nodes = new ArrayList<Node>(sampleSize);

			for (int i = 0; i < sampleSize; i++) {
				nodes.add(new Node("n" + i, ClockDriftCreater
						.RandomClockDriftCreater(40D), 0, 1000000D / 40D));

				if (i > 0) {
					parent = new ArrayList<Node>();
					parent.add(nodes.get(i - 1));
					nodes.get(i).setParent(parent);
				}
			}

			// for (int i = 0; i < sampleSize; i++) {
			//
			// if (i == 0) {
			// nodes.get(i).setDriftRate(-40D);
			// } else {
			// nodes.get(i).setDriftRate(40D);
			// }
			// }

			count++;

			aveTempMax = 0;

			int s = 0;

			for (int i = 0; i < sampleLength; i++) {

				for (int j = 0; j < nodes.size(); j++) {
					nodes.get(j).beforeSync();
				}

				Collections.shuffle(seq);

				for (int j = 0; j < nodes.size() - 1; j++) {
					nodes.get(seq.get(j)).randomSync();
					double diff = nodes.get(nodes.size() - 2).time
							- nodes.get(nodes.size() - 1).time;
					diff = (diff > 0) ? diff : -diff;
					if (maxTimeDiff < diff) {
						maxTimeDiff = diff;
						// System.out.println(diff + "     " + count);
					}
					if (aveTempMax < diff) {

						aveTempMax = diff;
					}
				}
			}

			aveTimeDiff += aveTempMax;
		}
		aveTimeDiff /= 1000000D;
		System.out.println(aveTimeDiff);
		System.out.println(maxTimeDiff);
		System.out.println();
	}
}
