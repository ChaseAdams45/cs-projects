public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private int lowerIndex(int index) {
        return (index - 1 + items.length) % items.length;
    }

    private int higherIndex(int index) {
        return (index + 1) % items.length;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];

        int current = higherIndex(nextFirst);
        for (int i = 0; i < size; i++) {
            a[i] = items[current];
            current = higherIndex(current);
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T i) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = i;
        size += 1;
        nextFirst = lowerIndex(nextFirst);
    }

    @Override
    public void addLast(T i) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = i;
        size += 1;
        nextLast = higherIndex((nextLast));
    }

    @Override
    public int size() {
        if (size < 0) {
            return 0;
        }
        return size;
    }

    @Override
    public void printDeque() {
        int i = higherIndex(nextFirst);
        while (i != nextLast) {
            System.out.print(items[i] + " ");
            i = higherIndex(i);
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int firstIndex = higherIndex(nextFirst);
        T first = items[firstIndex];
        items[firstIndex] = null;
        nextFirst = firstIndex;
        size -= 1;

        if (items.length >= 16 && size < items.length * 0.25) {
            resize(items.length / 2);
        }
        return first;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int lastIndex = lowerIndex(nextLast);
        T last = items[lastIndex];
        items[lastIndex] = null;
        nextLast = lastIndex;
        size -= 1;

        if (items.length >= 16 && size < items.length * 0.25) {
            resize(items.length / 2);
        }
        return last;
    }

    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        index = (higherIndex(nextFirst) + index) % items.length;
        return items[index];
    }
}
