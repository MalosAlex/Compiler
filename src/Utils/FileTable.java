package Utils;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K,V> implements IFileTable<K,V>{
    private Map<K,V> table;

    public FileTable()
    {
        table = new HashMap<K,V>();
    }

    public void update(K key, V value)
    {
        table.replace(key, value);
    }

    public void delete(K key)
    {
        table.remove(key);
    }

    @Override
    public void put(K key,V value)
    {
        table.put(key,value);
    }

    @Override
    public V lookUp(K key)
    {
        if(!this.containsKey(key))
            return null;
        return table.get(key);
    }

    @Override
    public boolean containsKey(K key)
    {
        return table.containsKey(key);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<K,V> entry : table.entrySet())
        {
            sb.append(entry.getKey().toString()).append("\n");
        }
        return sb.toString();
    }

    public Map<K,V> getTable(){
        return table;
    }
}
