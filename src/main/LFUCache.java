package main;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache implements Cache {

    private class Node {
        Object value;
        int count;

        public Node(Object value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    private int capacity;
    private Map<Object, Node> cache;

    private Map<Integer, LinkedHashSet<Object>> frequency;
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.frequency = new HashMap<>();
        frequency.put(0, new LinkedHashSet<>());
        minFreq = -1;
    }

    @Override
    public void put(Object value) {
        if (get(value) != Integer.valueOf(-1)) {
            Node node = cache.get(value);
            node.value = value;
            return;
        }

        if (cache.size() == capacity) {
            Object evict = frequency.get(minFreq).iterator().next();
            cache.remove(evict);
            frequency.get(minFreq).remove(evict);
        }

        minFreq = 0;
        Node newNode = new Node(value, 0);
        cache.put(value, newNode);
        frequency.get(0).add(value);
    }

    @Override
    public Object get(Object value) {
        if (capacity == 0) {
            return -1;
        }
        if (cache.containsKey(value)) {
            Node node = cache.get(value);
            node.count++;

            frequency.get(node.count - 1).remove(value);
            if (!frequency.containsKey(node.count)) {
                frequency.put(node.count, new LinkedHashSet<>());
            }
            frequency.get(node.count).add(value);

            if (minFreq == node.count - 1 && frequency.get(minFreq).isEmpty()) {
                minFreq = node.count;
            }
            return node.value;
        } else {
            return -1;

        }
    }

    public Object[] getElemFromLRUCache() {

        Object[] array = new Object[capacity];
        int i = 0;

        for (Map.Entry<Object, Node> entry : cache.entrySet()) {
            array[i] = entry.getKey();
            i++;
        }
        return array;
    }
}