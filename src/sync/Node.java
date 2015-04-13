package sync;

import java.util.ArrayList;
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
	private List<Node> neighbour;
	private boolean connected;
	private Node parentNode;
	private int length;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public List<Node> getNeighbour() {
		return neighbour;
	}

	public void setNeighbour(List<Node> neighbour) {
		this.neighbour = neighbour;
	}

	public Node(String name, double driftRate, double offset, double interval) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
	}

	public Node(String name, double driftRate, double offset, double interval,
			double[] location) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
		this.location = location;
		this.neighbour = new ArrayList<Node>();
		this.connected = false;
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
			List<Node> parent, double[] location) {
		this.name = name;
		this.driftRate = driftRate / 1000000D;
		this.offset = offset;
		this.interval = interval;
		this.parent = parent;
		this.location = location;
		this.connected = false;
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
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
		this.time = this.parentNode.time;
	}

	public double getDistanceFrom(Node node) {
		return Math.sqrt(Math.pow(this.location[0] - node.location[0], 2)
				+ Math.pow(this.location[1] - node.location[1], 2));
	}

	public void addNeighbour(Node node) {
		this.neighbour.add(node);
	}
}
