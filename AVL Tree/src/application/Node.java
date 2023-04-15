package application;


public class Node {
	Frequency data;
	Node next;
	public Node(Frequency data) {
		
		this.data = data;
		
	}
	@Override
	public String toString() {
		return "Node [data=" + data +"]";
	}
	
}
