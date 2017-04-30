package graphmaster.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_INIT_CAPACITY = 32;
    
    private int size;
    private int capacity;
    
    private int qBeginning;
    private int qEnding;
    
    private E[] arr;
    
    public ArrayQueue() {
        this(DEFAULT_INIT_CAPACITY);
    }
    
    public ArrayQueue(int initialCapacity) {
        arr = (E[]) new Object[initialCapacity];
        capacity = initialCapacity;
    }

    @Override
    public boolean add(E e) {
        if (qEnding + 1 == qBeginning) throw new IllegalStateException();
        return addElem(e);
    }

    @Override
    public boolean offer(E e) {
        return addElem(e);
    }

    @Override
    public E remove() {
        if (isEmpty()) throw new NoSuchElementException();
        return pollElem();
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        return pollElem();
    }

    @Override
    public E element() {
        if (isEmpty()) throw new NoSuchElementException();
        return peekElem();
    }

    @Override
    public E peek() {
        if (isEmpty()) return null;
        return peekElem();
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : arr) if (e.equals(o)) return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            
            private int qBegin = qBeginning;
            private int qEnd = qEnding;
            
            @Override
            public boolean hasNext() {
                return qBegin != qEnd;
            }

            @Override
            public E next() {
                E e = arr[qBegin++];
                if (qBegin == capacity) qBegin = 0;
                return e;
            }
            
        };
    }

    @Override
    public Object[] toArray() {
        int counter = 0;
        E[] res = (E[]) new Object[size];
        for (E e : this) res[counter++] = e;
        return res;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) throw new IllegalArgumentException();
        int counter = 0;
        for (E e : this) a[counter++] = (T) e;
        return a;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Is not supported by a queue."); 
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) 
            if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Is not supported by a queue."); 
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Is not supported by a queue.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Is not supported by a queue.");
    }

    @Override
    public void clear() {
        E[] newArr = (E[]) new Object[DEFAULT_INIT_CAPACITY];
        capacity = DEFAULT_INIT_CAPACITY;
        arr = newArr;
        size = 0;
        qBeginning = 0;
        qEnding = 0;
    }
    
    private void resize(int newCapacity, boolean needCopy) {
        E[] newArr = (E[]) new Object[newCapacity];        
        if (needCopy) {
            System.arraycopy(arr, qBeginning, newArr, 0, size - qEnding);
            System.arraycopy(arr, 0, newArr, size - qEnding, qEnding);
            qEnding = size;
        } else qEnding = 0;
        qBeginning = 0;
        capacity = newArr.length;
        arr = newArr;
    }
    
    private boolean addElem(E e) {
        arr[qEnding++] = e;
        size++;
        if (qEnding == capacity) qEnding = 0;
        if (qBeginning == qEnding) resize(capacity * 2, true);
        return true;
    }
    
    private E pollElem() {
        E e = arr[qBeginning]; 
        arr[qBeginning++] = null;
        size--;
        if (qBeginning == capacity) qBeginning = 0;
        if (qBeginning == qEnding) resize(capacity / 2, false);
        return e;
    }
    
    private E peekElem() {
        return arr[qBeginning];
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
    
}
