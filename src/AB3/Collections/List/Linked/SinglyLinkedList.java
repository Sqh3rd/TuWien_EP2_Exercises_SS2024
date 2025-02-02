package AB3.Collections.List.Linked;

import AB3.Collections.List.LinkedList;

public class SinglyLinkedList<T> implements LinkedList<T>
{
    private SinglyLinkedEntry<T> head;
    private SinglyLinkedEntry<T> tail;
    private int size;

    public SinglyLinkedList() {
        this.size = 0;
    }

    public SinglyLinkedList(SinglyLinkedList<T> other) {
        this.size = other.size;
        this.head = other.head.clone();
        this.tail = getLast();
    }

    @Override
    public void push(T element)
    {
        if (head == null && tail == null) {
            SinglyLinkedEntry<T> current = new SinglyLinkedEntry<>(element);
            head = current;
            tail = current;
        } else {
            tail.setNext(new SinglyLinkedEntry<>(element));
            tail = tail.getNext();
        }
        size++;
    }

    @Override
    public void insert(int index, T element)
    {
        if (index > size)
            throw new IndexOutOfBoundsException();
        SinglyLinkedEntry<T> current = head;
        size++;
        if (index == 0)
        {
           head = new SinglyLinkedEntry<>(element, current);
           if (tail == null) tail = head;
           return;
        }
        if (index == size - 1) {
            tail.setNext(new SinglyLinkedEntry<>(element));
            tail = tail.getNext();
            return;
        }
        for (int i = 1; i < index; i++) {
            current = current.getNext();
        }
        SinglyLinkedEntry<T> temp = current.getNext();
        current.setNext(new SinglyLinkedEntry<>(element, temp));
    }

    @Override
    public T pop()
    {
        if (size == 1) {
            T value = tail.getValue();
            tail = null;
            head = null;
            size--;
            return value;
        }
        SinglyLinkedEntry<T> current = head;
        while (current.getNext() != tail)
        {
            current = current.getNext();
        }
        tail = current;
        T value = tail.getNext().getValue();
        tail.setNext(null);
        size--;
        return value;
    }

    @Override
    public T remove(int index)
    {
        if (index >= size)
            return null;
        if (index == size - 1)
            return pop();
        size--;
        if (index == 0)
        {
            T value = head.getValue();
            head = head.getNext();
            return value;
        }
        SinglyLinkedEntry<T> current = head;
        for (int i = 1; i < index; i++)
        {
            current = current.getNext();
        }
        T value = current.getNext().getValue();
        current.setNext(null);
        return value;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public int indexOf(T element)
    {
        if (head.getValue() == element)
            return 0;
        if (tail.getValue() == element)
            return size - 1;
        SinglyLinkedEntry<T> current = head;
        int count = 0;
        while (current.getValue() != element)
        {
            if (current.getNext() == null)
                return -1;
            current = current.getNext();
            count++;
        }
        return count;
    }

    @Override
    public boolean contains(T element)
    {
        return indexOf(element) != -1;
    }

    @Override
    public T get(int index)
    {
        if (index >= size)
            return null;
        if (index == 0)
            return head.getValue();
        if (index == size - 1)
            return tail.getValue();
        SinglyLinkedEntry<T> current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.getNext();
        }
        return current.getValue();
    }

    private SinglyLinkedEntry<T> getLast()
    {
        SinglyLinkedEntry<T> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    public void retainEvenIndices() {
        int i = 0;
        SinglyLinkedEntry<T> current = head;
        while (current != null) {
            if (i%2==0){
                if (current.getNext() != null)
                    current.setNext(current.getNext().getNext());
                i++;
            }
            current = current.getNext();
            i++;
        }
        size = size/2;
    }
}
