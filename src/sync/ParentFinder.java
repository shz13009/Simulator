package sync;

import java.util.Queue;

public class ParentFinder {
	public static int parentFinder(Queue<Node> q) {

		Node n = q.poll();

		for (Node child : n.getNeighbour()) {
			if (child.getParentNode() == null && !child.name.equals("n0")) {
				child.setParentNode(n);
				child.setLength(n.getLength() + 1);
				q.add(child);
				//System.out.print(child.getLength() + "     ");
				//System.out.print(child.getParentNode().name + "     ");
				//System.out.println(child.name);
			}
		}

		if (q.isEmpty()) {
			return n.getLength();
		}

		return parentFinder(q);
	}

}
