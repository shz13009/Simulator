/**
 * 
 */
package sync;

import java.util.ArrayList;
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

		sampleSize = 5;
		sampleTimes = 100;
		sampleLength = 50;

		Random random = new Random();

		List<Node> nodes = new ArrayList<Node>(sampleSize);

		List<Node> parent = new ArrayList<Node>();

		for (int i = 0; i < sampleSize; i++) {
			nodes.add(new Node("n" + i, 40, 0, 1000000F / 40F));
			if (i > 0) {
				parent.add(nodes.get(i - 1));
				nodes.get(i).setParent(parent);
			}
		}

		Boolean[] flag = new Boolean[nodes.size()];

		int count = 0;
		double maxTimeDiff = 0;

		while (count < sampleTimes) {

			count++;

			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).time = 0;
			}

			int s = 0;

			for (int i = 0; i < sampleLength; i++) {

				for (int j = 0; j < nodes.size(); j++) {
					nodes.get(j).beforeSync();
					flag[j] = false;
				}

				for (int j = 0; j < nodes.size() - 1; j++) {
					s = random.nextInt(nodes.size() - 1) + 1;
					while (flag[s]) {
						s = random.nextInt(nodes.size() - 1) + 1;
					}
					flag[s] = true;
					nodes.get(s).randomSync();

					double diff = nodes.get(nodes.size() - 2).time
							- nodes.get(nodes.size()-1).time;
					diff = (diff > 0) ? diff : -diff;
					if (maxTimeDiff < diff)
						maxTimeDiff = diff;
				}

			}
		}
		System.out.println(maxTimeDiff);
	}
}
