package main;

public interface Cache {

    Object get(Object value);

    void put(Object value);

    Object[] getElemFromLRUCache();

}
