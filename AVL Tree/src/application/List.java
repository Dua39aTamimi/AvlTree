package application;

public class List {
	Node first;
	Node last;
	int size;

	public List() {
		this.last = null;
		this.first = null;
		this.size = 0;
	}

	public void addFirst(Frequency data) {
		Node node = new Node(data);
		if (first == null) {
			first = node;
			size++;
		} else {

			node.next = first;
			first = node;
			size++;
		}
	}

	public void addLast(Frequency data) {
		Node node = new Node(data);
		if (first == null) {
			first = node;
			size++;
		} else {
			last = first;
			while (last.next != null) {

				last = last.next;
			}
			last.next = node;
			size++;
		}
	}

	public Frequency removeFirst() {
		Frequency data = first.data;
		first = first.next;
		size--;
		return data;

	}

	public Frequency removeLast() {
		last = first;
		Frequency data;
		if (first == null)
			return null;
		else if (first.next == null) {
			data = first.data;
			first = null;
			size--;
			return data;
		}

		else {
			while (last.next.next != null) {

				last = last.next;
			}
			data = last.next.data;
			last.next = null;
			size--;

			return data;
		}
	}

	public String print() {

		Node temp = first;
		String s = " ";
		for (int i = 0; i < size; i++) {
			// System.out.println(temp.data);
			s += temp.data.year + " " + temp.data.fre + ", ";

			// s.append();

			temp = temp.next;
		}
		return s;
	}
	public Node search(int year) {
		Node temp = first;
		Node result=null;
		for (int i = 0; i < size; i++) {
			if(temp.data.year==year) {
				result=temp;
				break;
			}
			temp = temp.next;
		}
		return result;
	}
	
	public int printFre() {

		Node temp = first;
		int total = 0;
		for (int i = 0; i < size; i++) {
			// System.out.println(temp.data);
			total+=temp.data.fre;

			// s.append();

			temp = temp.next;
		}
		return total;
	}
}
