package main;

import java.util.HashMap;
import java.util.Map;

public class LRUCache implements Cache {
    private class Node {
        Node prev;
        Node next;
        Object value;

        public Node(Object value) {
            this.value = value;
            prev = next = null;
        }
    }

    private int capacity;
    private Map<Object, Node> cacheMap;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public Object get(Object key) {
        if (cacheMap.containsKey(key)) {
            Node cur = cacheMap.get(key);
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
            cur.prev = null;
            cur.next = null;
            moveToTail(cur);
            return cacheMap.get(key).value;
        } else {
            return -1;
        }
    }

    @Override
    public void put(Object value) {
        if (get(value) != Integer.valueOf(-1)) {
            cacheMap.get(value).value = value;
            return;
        }
        if (cacheMap.size() == capacity) {
            cacheMap.remove(head.next.value);
            head.next = head.next.next;
            head.next.prev = head;
        }
        Node newNode = new Node(value);
        cacheMap.put(value, newNode);
        moveToTail(newNode);
    }

    private void moveToTail(Node cur) {
        tail.prev.next = cur;
        cur.prev = tail.prev;
        cur.next = tail;
        tail.prev = cur;
    }

    public Object[] getElemFromLRUCache() {
        Object[] array = new Object[capacity];
        int i = 0;

        for (Map.Entry<Object, Node> entry : cacheMap.entrySet()) {
            array[i] = entry.getKey();
            i++;
        }
        return array;
    }
}
