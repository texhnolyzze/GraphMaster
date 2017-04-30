package graphmaster.utils.trie_map;

import graphmaster.utils.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TrieMap<V> implements Map<String, V> {

    public static final int OPTIMAL_KEYS_LENGTH = 10;
    public static final int OPTIMAL_ALPHA_LENGTH = 10;
    
    private final Alphabet alphabet;
    private final Node<V> root;
    
    private int size;
    
    public TrieMap(Alphabet alpha) {
        alphabet = alpha;
        root = new Node<>();
    }
    
    public TrieMap(char[] alpha) {
        this(new Alphabet(alpha));
    }
    
    public TrieMap(String alpha) {
        this(alpha.toCharArray());
    }
    
    @Override
    public int size() {
        return size;
    }
    
    public Alphabet getAlphabet() {
        return alphabet;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object k) {
        String key = (String) k;
        checkStringInAlpha(key);
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Stack<Node<V>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<V> node = stack.pop();
            if (node.value != null && node.value.equals(value)) return true;
            for (Node<V> child : node.childs) if (child != null) stack.push(child);
        }
        return false;
    }

    @Override
    public V get(Object k) {
        String key = (String) k;
        checkStringInAlpha(key);
        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            int alphaIndex = alphabet.toIndex(key.charAt(i));
            if (node.childs[alphaIndex] == null) return null;
            else node = node.childs[alphaIndex];
        }
        return node.value;
    }

    @Override
    public V put(String key, V value) {
        checkStringInAlpha(key);
        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            int alphaIndex = alphabet.toIndex(key.charAt(i));
            Node<V> child = node.childs[alphaIndex];
            if (child == null) {
                child = new Node<>();
                node.childs[alphaIndex] = child;
            }
            node = child;
        }
        V prevValue = node.value;
        node.value = value;
        size++;
        return prevValue;
    }

    @Override
    public V remove(Object k) {
        String key = (String) k;
        checkStringInAlpha(key);
        Node<V> node = root;
        Stack<Node<V>> stack = new Stack<>();
        stack.push(node);
        for (int i = 0; i < key.length(); i++) {
            int alphaIndex = alphabet.toIndex(key.charAt(i));
            if (node.childs[alphaIndex] == null) return null;
            else node = node.childs[alphaIndex];
            stack.push(node);
        }
        V value = node.value;
        node.value = null;
        int currentPositionInTheKey = key.length() - 1;
        while (!stack.isEmpty()) {
            Node<V> n = stack.pop();
            boolean deleteNode = true;
            for (Node<V> child : n.childs) {
                if (child != null) {
                    deleteNode = false;
                    break;
                }
            }
            if (!deleteNode) break;
            else {
                Node<V> parent = stack.peek();
                int alphaIndex = alphabet.toIndex(key.charAt(currentPositionInTheKey));
                parent.childs[alphaIndex] = null;
            }
            currentPositionInTheKey--;
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Map.Entry<? extends String, ? extends V> entry : m.entrySet()) 
            put(entry.getKey(), entry.getValue());
    }

    @Override
    public void clear() {
        Arrays.fill(root.childs, null);
        size = 0;
    }

    @Override
    public Set<String> keySet() {
        return keysWithPrefix("");
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>(size);
        Stack<Node<V>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<V> node = stack.pop();
            if (node.value != null) values.add(node.value);
            for (Node<V> child : node.childs) if (child != null) stack.push(child);
        }
        return values;
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        Set<Entry<String, V>> entries = new HashSet<>(size);
        Stack<Node<V>> nodeStack = new Stack<>();
        Stack<String> stringsStack = new Stack<>();
        nodeStack.push(root);
        stringsStack.push("");
        while (!nodeStack.isEmpty()) {
            Node<V> n = nodeStack.pop();
            String s = stringsStack.pop();
            System.out.println(s);
            if (n.value != null) entries.add(new TrieMapEntry(s, n.value));
            for (int i = 0; i < alphabet.length(); i++) {
                if (n.childs[i] != null) {
                    char alphaChar = alphabet.toChar(i);
                    nodeStack.push(n.childs[i]);
                    stringsStack.push(s + alphaChar);
                }
            }
        }
        return entries;
    }
    
    public Set<String> keysWithPrefix(String prefix) {
        checkStringInAlpha(prefix);
        Node<V> node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int alphaIndex = alphabet.toIndex(prefix.charAt(i));
            if (node.childs[alphaIndex] == null) return Collections.EMPTY_SET;
            else node = node.childs[alphaIndex];
        }
        Set<String> keys = new TreeSet<>();
        Stack<Node<V>> nodeStack = new Stack<>();
        Stack<String> stringsStack = new Stack<>();
        nodeStack.push(node);
        stringsStack.push(prefix);
        while (!nodeStack.isEmpty()) {
            Node<V> n = nodeStack.pop();
            String s = stringsStack.pop();
            if (n.value != null) keys.add(s);
            for (int i = 0; i < alphabet.length(); i++) {
                if (n.childs[i] != null) {
                    char alphaChar = alphabet.toChar(i);
                    nodeStack.push(n.childs[i]);
                    stringsStack.push(s + alphaChar);
                }
            }
        }
        return keys;
    }
    
    private void checkStringInAlpha(String s) {
        if (!alphabet.isStringInAlphabet(s))
            throw new IllegalArgumentException("One of characters of \"" + s + "\" is not in the given alphabet!");
    }
    
    final class Node<V> {
        
        V value;
        Node<V>[] childs;
        
        Node() {
            childs = (Node<V>[]) new Node[alphabet.length()];
        }
        
    }
    
    final class TrieMapEntry implements Entry<String, V> {
        
        V value;
        String key;
        
        TrieMapEntry(String k, V v) {
            key = k;
            value = v;
        }
        
        @Override
        public String getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return put(key, value);
        }
        
    }
    
}
