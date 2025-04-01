package application;
class SingleLinkedList {
    private Node first;

    public SingleLinkedList() {
        first = null;
    }

    public void addFirst(Object data) {
        Node newNode = new Node(data);
        newNode.setNext(first);
        first = newNode;
    }

    public Node getFirst() {
        return first;  // This method allows access to the first node
    }

    public void remove(Node nodeToRemove) {
        if (first == null) return;

        if (first == nodeToRemove) {
            first = first.getNext();
            return;
        }

        Node current = first;
        while (current.getNext() != null && current.getNext() != nodeToRemove) {
            current = current.getNext();
        }
        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }

    public void printList() {
        Node current = first;
        while (current != null) {
            System.out.print(current.getData() + " -> ");
            current = current.getNext();
        }
        System.out.println("null");
    }
}
