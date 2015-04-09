package sync;

import java.util.List;
import java.util.Random;

public class Node {
	public String name;
	private double driftRate;
	private double offset;
	public double time;
	private double interval;
	private List<Node> parent;
	private double[] location;
	private double commRange;

	public Node(String name, double driftRate, double offset, double interval) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
	}

	public Node(String name, double driftRate, double offset, double interval,
			List<Node> parent) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
		this.parent = parent;
	}

	public Node(String name, double driftRate, double offset, double interval,
			List<Node> parent, double[] location, double commRange) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
		this.parent = parent;
		this.location = location;
		this.commRange = commRange;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
	}

	public double getCommRange() {
		return commRange;
	}

	public void setCommRange(double commRange) {
		this.commRange = commRange;
	}

	public List<Node> getParent() {
		return parent;
	}

	public void setParent(List<Node> parent) {
		this.parent = parent;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public double getDriftRate() {
		return driftRate;
	}

	public void setDriftRate(double driftRate) {
		this.driftRate = driftRate / 1000000D;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void beforeSync() {
		this.time = this.time + (double) (driftRate * interval);
	}

	public void randomSync() {
		Random random = new Random();
		int s = random.nextInt(parent.size());
		this.time = parent.get(s).time;
	}

	public void syncWith(Node dest) {
		this.time = dest.time;
	}

	public void randomSyncWithLocation() {
	}
}
