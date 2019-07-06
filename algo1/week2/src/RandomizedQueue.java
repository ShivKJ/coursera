import static java.util.Objects.isNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<T> implements Iterable<T> {
    private T[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        this.items = (T[]) new Object[10];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;

    }

    public int size() {
        return size;
    }

    public void enqueue(T t) {
        if (isNull(t))
            throw new IllegalArgumentException();

        if (size == items.length)
            ensureSize(2 * size);

        items[size++] = t;
    }

    public T dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        if (size <= items.length / 4 && size > 10)
            ensureSize(2 * size);

        int index = StdRandom.uniform(size);

        T out = items[index];

        items[index] = items[size - 1];
        items[--size] = null;

        return out;

    }

    public T sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        return items[StdRandom.uniform(size)];
    }

    private void ensureSize(int capacity) {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
    }

    @Override
    public Iterator<T> iterator() {
        @SuppressWarnings("unchecked")
        T[] newItems = (T[]) new Object[size];
        System.arraycopy(items, 0, newItems, 0, size);

        StdRandom.shuffle(newItems);

        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < newItems.length;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                return newItems[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

}
