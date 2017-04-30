package graphmaster.utils;

import java.util.Iterator;
import java.util.LinkedList;

public class Stack<E> implements Iterable<E> {
    
    private final LinkedList<E> list;
    
    public Stack() {
        list = new LinkedList<>();
    }
    
    public E peek() {
        return list.getLast();
    }
    
    public E pop() {
        return list.pollLast();
    }
    
    public void push(E e) {
        list.add(e);
    }
    
    public int size() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
    @Override
    public String toString() {
        return list.toString();
    }
    
    
    
}
