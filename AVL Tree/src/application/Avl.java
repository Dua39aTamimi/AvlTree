package application;

import java.util.LinkedList;
import java.util.Queue;

public class Avl {
	AvlNode root;

	public Avl() {
		root = null;
	}

	public void insert(AvlNode node) {

		root = insert(node, root);
	}

	private int getHeight(AvlNode t) {
		if (t == null) {
			return -1;
		} else {
			return t.height;
		}

	}

	private AvlNode insert(AvlNode node, AvlNode t) {
		if (t == null)
			t = new AvlNode(node.name, node.fre);
		else if (node.name.name.compareTo(t.name.name) < 0) {

			t.left = insert(node, t.left);
			if (getHeight(t.left) - getHeight(t.right) == 2)
				if (node.name.name.compareTo(t.left.name.name) < 0)
					t = singleLeft(t);
				else
					t = doubleLeft(t);
		} else if (node.name.name.compareTo(t.name.name) > 0) {

			t.right = insert(node, t.right);
			if (getHeight(t.right) - getHeight(t.left) == 2)
				if (node.name.name.compareTo(t.right.name.name) > 0)
					t = singleRight(t);
				else
					t = doubleRight(t);
		} else if (node.name.name.compareTo(t.name.name) == 0 && node.name.gender == t.name.gender) {
			t.list.addLast(node.fre);
		} else if (node.name.name.compareTo(t.name.name) == 0 && node.name.gender != t.name.gender) {
			if (Character.compare(t.name.gender, node.name.gender) > 0) {
				t.left = insert(node, t.left);
				if (getHeight(t.left) - getHeight(t.right) == 2)
					if (node.name.name.compareTo(t.left.name.name) < 0)
						t = singleLeft(t);
					else
						t = doubleLeft(t);
			} else {
				t.right = insert(node, t.right);
				if (getHeight(t.right) - getHeight(t.left) == 2)
					if (node.name.name.compareTo(t.right.name.name) > 0)
						t = singleRight(t);
					else
						t = doubleRight(t);
			}
		}

		t.height = Math.max(getHeight(t.left), getHeight(t.right)) + 1;
		return t;
	}

	private AvlNode singleLeft(AvlNode k2) {
		AvlNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(getHeight(k2.left), getHeight(k2.right)) + 1;
		k1.height = Math.max(getHeight(k1.left), k2.height) + 1;
		return k1;
	}

	private AvlNode singleRight(AvlNode k1) {
		AvlNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(getHeight(k1.left), getHeight(k1.right)) + 1;
		k2.height = Math.max(getHeight(k2.right), k1.height) + 1;
		return k2;
	}

	private AvlNode doubleLeft(AvlNode k3) {
		k3.left = singleRight(k3.left);
		return singleLeft(k3);
	}

	private AvlNode doubleRight(AvlNode k1) {
		k1.right = singleLeft(k1.right);
		return singleRight(k1);
	}

	public AvlNode search(String name, char g) {
		return search(root, name, g);
	}

	private AvlNode search(AvlNode r, String name, char g) {
		while ((r != null)) {
			if (name.compareTo(r.name.name) < 0) {
				r = r.left;
			} else if (name.compareTo(r.name.name) > 0)
				r = r.right;
			else {
				if (Character.compare(g, r.name.gender) < 0) {
					r = r.left;
				} else if (Character.compare(g, r.name.gender) > 0) {
					r = r.right;
				} else {
					break;
				}
			}
			r = search(r, name, g);
		}
		return r;
	}

	public StringBuilder levelOrder() {

		Queue<AvlNode> queue = new LinkedList<>();
		StringBuilder lines =new StringBuilder();
		if (this.root == null) {

			return null;
		}
		queue.add(this.root);
		while (!queue.isEmpty()) {

			AvlNode temp = queue.remove();

			lines .append( temp.name.name+", "+temp.name.gender+", " +temp.list.printFre());
			//lines.append("\n");
			lines.append(System.getProperty("line.separator"));

			if (temp.left != null) {
				queue.add(temp.left);
			}
			if (temp.right != null) {
				queue.add(temp.right);
			}

		}
		
		return lines;
	}

}
