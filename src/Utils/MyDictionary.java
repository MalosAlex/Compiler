package Utils;

import Model.Type.Type;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    private Map<K,V> table;

    public MyDictionary()
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

    public Map<K,V> getContent()
    {
        return table;
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
            sb.append(entry.getKey().toString() + " --> " + entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public MyIDictionary<K,V> deepCopy()
    {

        MyIDictionary<K,V> cpy = new MyDictionary<>();
        for(Map.Entry<K,V> entry : table.entrySet())
        {
            if (entry.getValue() instanceof Value)
            {
                cpy.put(entry.getKey(), (V)((Value)entry.getValue()).deepCopy());
            }
            if (entry.getValue() instanceof Type)
            {
                cpy.put(entry.getKey(), (V)((Type)entry.getValue()).deepCopy());
            }
        }
        return cpy;
    }
}
