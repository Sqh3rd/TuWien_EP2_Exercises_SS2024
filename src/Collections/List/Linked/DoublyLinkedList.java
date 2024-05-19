package Collections.List.Linked;

import Collections.Iterable;
import Collections.List.LinkedList;

import java.util.function.Consumer;

public class DoublyLinkedList<T> implements LinkedList<T>, Iterable<T>
{
    private DoublyLinkedEntry<T> head;
    private DoublyLinkedEntry<T> tail;
    private int size;

    @Override
    public void insert(int index, T element)
    {
        if (index > size)
            throw new IndexOutOfBoundsException();
        size++;
        if (index == 0)
            insertFirst(element);
        else if (index == size - 2)
            insertLast(element);
        else if (index <= (size - 1) / 2)
            insertInFirstHalf(index, element);
        else
            insertInLastHalf(index, element);
    }

    private void insertFirst(T element)
    {
        if (head != null && tail != null)
        {
            head.setPrevious(new DoublyLinkedEntry<>(element));
            head = head.getPrevious();
        }
        else
        {
            head = tail = new DoublyLinkedEntry<>(element);
        }
    }

    private void insertLast(T element)
    {
        if (head != null && tail != null)
        {
            tail.setNext(new DoublyLinkedEntry<>(element));
            tail = tail.getNext();
        }
        else
        {
            head = tail = new DoublyLinkedEntry<>(element);
        }
    }

    private void insertInFirstHalf(int index, T element)
    {
        DoublyLinkedEntry<T> current = head;
        for (int i = 1; i < index; i++)
            current = current.getNext();
        DoublyLinkedEntry<T> old = current.getNext();
        current.setNext(new DoublyLinkedEntry<>(element));
        current.getNext().setNext(old);
    }

    private void insertInLastHalf(int index, T element)
    {
        DoublyLinkedEntry<T> current = tail;
        for (int i = size - 2; i > index; i--)
            current = current.getPrevious();
        DoublyLinkedEntry<T> old = current.getPrevious();
        current.setPrevious(new DoublyLinkedEntry<>(element));
        current.getPrevious().setPrevious(old);
    }

    @Override
    public void push(T element)
    {
        size++;
        insertLast(element);
    }

    @Override
    public T remove(int index)
    {
        if (index >= size)
            throw new IndexOutOfBoundsException();
        size--;
        if (index == 0)
            return removeFirst();
        else if (index == size)
            return removeLast();
        else if (index <= (size + 1) / 2)
            return removeInFirstHalf(index);
        else
            return removeInLastHalf(index);
    }

    private T removeFirst()
    {
        T value = head.getValue();
        head = head.getNext();
        if (head != null)
            head.setPrevious(null);
        else
            tail = null;
        return value;
    }

    private T removeLast()
    {
        T value = tail.getValue();
        tail = tail.getPrevious();
        if (tail != null)
            tail.setNext(null);
        else
            head = null;
        return value;
    }

    private T removeInFirstHalf(int index)
    {
        DoublyLinkedEntry<T> current = head;
        for (int i = 1; i <= index; i++)
            current = current.getNext();
        T value = current.getValue();
        current.getNext().setPrevious(current.getPrevious());
        return value;
    }

    private T removeInLastHalf(int index)
    {
        DoublyLinkedEntry<T> current = tail;
        for (int i = size; i >= index; i--)
            current = current.getPrevious();
        T value = current.getValue();
        current.getNext().setPrevious(current.getPrevious());
        return value;
    }

    @Override
    public T pop()
    {
        size--;
        return removeLast();
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public int indexOf(T element)
    {
        int count = 0;
        DoublyLinkedEntry<T> current = head;
        while (current.getValue() != element)
        {
            current = current.getNext();
            if (current == null)
                return -1;
            count++;
        }
        return count;
    }

    @Override
    public int lastIndexOf(T element)
    {
        int count = size() - 1;
        DoublyLinkedEntry<T> current = tail;
        while (current.getValue() != element) {
            current = current.getPrevious();
            if (current == null)
                return -1;
            count--;
        }
        return count;
    }

    @Override
    public boolean contains(T element)
    {
        return indexOf(element) != -1;
    }

    protected LinkedEntry<T> getEntry(int index) {
        if (index <= size / 2)
        {
            DoublyLinkedEntry<T> current = head;
            for (int i = 1; i <= index; i++)
                current = current.getNext();
            return current;
        }
        else
        {
            DoublyLinkedEntry<T> current = tail;
            for (int i = size - 2; i >= index; i++)
                current = current.getPrevious();
            return current;
        }
    }

    @Override
    public T get(int index)
    {
        return getEntry(index).getValue();
    }

    @Override
    public T replace(int index, T value) {
        LinkedEntry<T> entry = getEntry(index);
        T old = entry.getValue();
        entry.setValue(value);
        return old;
    }

    @Override
    public LinkedEntryIterator<T> iterator() {
        return new LinkedEntryIterator<>(head);
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        DoublyLinkedEntry<T> current = head;
        while (current != null) {
            consumer.accept(current.getValue());
            current = current.getNext();
        }
    }
}
