package application;

public class HashTable {
	int size;
	SingleLinkedList[] table; 

	public HashTable(int capacity) {
		table = new SingleLinkedList[capacity];
		size = capacity;
		for (int i = 0; i < size; i++) {
			table[i] = new SingleLinkedList(); 
		}
	}

	public int size() {
		return size;
	}

	// Default constructor (you can implement if needed)
	public HashTable() {
		
		this(55);
	}

	// Add a TableEntry associated with a vertex name
	public void add(String vertexName, TableEntry entry) {
		int index = hash(vertexName); // Hash the vertex name to find the index
		table[index].addFirst(entry); // Add TableEntry to the linked list at the hashed index
	}


	public TableEntry get(String vertexName) {
		int index = hash(vertexName);
		Node current = table[index].getFirst();

		while (current != null) {
			TableEntry entry = (TableEntry) current.getData();

			if (entry.getHeader().contains(vertexName)) {
				return entry;
			}
			current = current.getNext();
		}
		return null;
	}



	public int hash(String vertexName) {
		return Math.abs(vertexName.hashCode()) % size; // Use hashCode and modulo operation
	}

	public void traverse() {
		for (int i = 0; i < size; i++) {
			System.out.print(i + "  |  ");
			table[i].printList(); // Print the contents of the linked list at each index
		}
	}
}
