class LinkedList {
    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head; 

    public void addNode(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public void createCycle(int index) {
        Node temp = head;
        Node cycleNode = null;
        int count = 0;
        
        while (temp != null) {
            if (count == index) {
                cycleNode = temp; 
            }
            temp = temp.next;
            count++;
        }
        
        if (cycleNode != null) {
            temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = cycleNode; 
        }
    }

    public boolean hasCycle(Node head) {
        Node slow = head; 
        Node fast = head; 

        while (fast != null && fast.next != null) {
            slow = slow.next; 
            fast = fast.next.next; 

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.createCycle(1);
        boolean result = list.hasCycle(list.head);
        System.out.println("Does the linked list have a cycle? " + result);
    }
}
