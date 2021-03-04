public class LinkedListDeque<T> {

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(T v, Node p, Node n) {
            value = v;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T i) {
        Node newNode = new Node(i, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    public void addLast(T i) {
        Node newNode = new Node(i, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;

    }

    public int size() {
        if (size < 0) {
            return 0;
        }
        return size;
    }

    public void printDeque() {
        Node n = sentinel.next;
        while (n != sentinel) {
            System.out.print(n.value + " ");
            n = n.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        size -= 1;
        if (isEmpty()) {
            return null;
        } else {
            Node first = sentinel.next;
            T x = first.value;
            sentinel.next = first.next;
            first.next.prev = sentinel;
            first.prev = first.next = null;
            return x;
        }
    }

    public T removeLast() {
        size -= 1;
        if (isEmpty()) {
            return null;
        } else {
            Node last = sentinel.prev;
            T x = last.value;
            sentinel.prev = last.prev;
            last.prev.next = sentinel;
            last.prev = last.next = null;
            return x;
        }
    }

    public T get(int index) {
        Node n = sentinel.next;
        while (index != 0) {
            n = n.next;
            index -= 1;
        }
        return n.value;
    }

    private T getHelper(Node n, int index) {
        if (index == 0) {
            return n.value;
        } else {
            return getHelper(n.next, index - 1);
        }
    }
    public T getRecursive(int index) {
        if (isEmpty()) {
            return null;
        } else {
            return getHelper(sentinel.next, index);
        }
    }
}
