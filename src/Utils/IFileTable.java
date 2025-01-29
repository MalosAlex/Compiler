package Utils;

import java.util.Map;

public interface IFileTable<K,V> {
    void put(K key, V value);
    void update(K key, V value);
    void delete(K key);
    V lookUp(K key);
    boolean containsKey(K key);
    Map<K,V> getTable();
}
