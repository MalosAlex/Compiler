package Utils;

import java.util.Map;

public interface MyIHeap<K,V> {
    int put(V value);
    void put1(V value);
    void update(K key, V value);
    public Map<K, V> getContent();
    public void setContent(Map<K, V> newHeap);
    V lookUp(K key);
    K getKey(V value);
    boolean containsKey(K key);
}
