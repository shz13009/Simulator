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

		sampleSize = 20;
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

//			for (int i = 0; i < sampleSize; i++) {
//
//				if (i == 0) {
//					nodes.get(i).setDriftRate(-40D);
//				} else {
//					nodes.get(i).setDriftRate(40D);
//				}
//			}

			count++;

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
						System.out.println(diff + "     " + count);
					}
				}
			}
		}
		System.out.println(maxTimeDiff);
	}
}
