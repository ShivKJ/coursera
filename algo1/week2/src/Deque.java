import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {
    private Node<T> front, back;
    private int     size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T t) {
        validateData(t);

        Node<T> n = new Node<>(t);

        if (isEmpty())
            front = back = n;
        else {
            n.next = front;
            front.prev = n;
            front = n;
        }

        size++;
    }

    public void addLast(T t) {
        validateData(t);

        Node<T> n = new Node<>(t);

        if (isEmpty())
            front = back = n;
        else {
            n.prev = back;
            back.next = n;
            back = n;
        }

        size++;
    }

    private static void validateData(Object data) {
        if (isNull(data))
            throw new IllegalArgumentException();
    }

    private void validateRemoval() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    public T removeFirst() {
        validateRemoval();

        T out = front.data;
        Node<T> next = front.next;

        if (nonNull(next)) {
            next.prev = null;
            front.next = null;
        } else
            back = null;

        front = next;

        size--;

        return out;
    }

    public T removeLast() {
        validateRemoval();

        T out = back.data;

        Node<T> prev = back.prev;

        if (nonNull(prev)) {
            prev.next = null;
            back.prev = null;
        } else
            front = null;

        back = prev;

        size--;

        return out;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> curr = front;

            @Override
            public boolean hasNext() {
                return nonNull(curr);
            }

            @Override
            public T next() {
                if (isNull(curr))
                    throw new NoSuchElementException();

                T out = curr.data;
                curr = curr.next;
                return out;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private final static class Node<T> {
        T       data;
        Node<T> prev, next;

        Node(T data) {
            this.data = data;
        }

    }
}
