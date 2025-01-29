package Utils;

import Model.Value.Value;

import java.util.Map;

public interface MyIDictionary<K,V> {
    void put(K key, V value);
    void update(K key, V value);
    void delete(K key);
    V lookUp(K key);
    boolean containsKey(K key);
    public Map<K,V> getContent();
    public MyIDictionary<K, V> deepCopy();

}
