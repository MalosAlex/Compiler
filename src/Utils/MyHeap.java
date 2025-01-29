package Utils;

import Model.Value.RefValue;
import Model.Value.Value;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MyHeap<Value> implements MyIHeap<Integer,Value>{
    private Map<Integer, Value> heap;
    private int counter;
    public MyHeap()
    {
        heap = new HashMap<Integer, Value>();
        counter = 1;
    }

    @Override
    public int put(Value value) {
        heap.put(counter, value);
        int c = counter;
        while(this.containsKey(counter))
        {
            counter+=1;
        }
        return c;
    }

    public void put1(Value value){
        heap.put(counter, value);
        while(this.containsKey(counter))
        {
            counter+=1;
        }
    }



    public void update(Integer k,Value value){
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            if (entry.getKey().equals(k)) { // Use equals() to compare values
                entry.setValue(value);
            }
        }
    }

    /*public void delete(Value value){
        int c = -1;
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            if (entry.getValue().equals(value)) { // Use equals() to compare values
                c = entry.getKey();
            }
        }
        if(c == -1)
            throw new ADTException("Value isn't in the heap");
        heap.remove(c);
        if(c<counter)
            counter = c;
    }*/

    public Map<Integer, Value> getContent()
    {
        return heap;
    }

    public void setContent(Map<Integer, Value> newHeap)
    {
        heap = newHeap;
        List<Integer> heapAddresses = new ArrayList<>(newHeap.keySet());
        if (heapAddresses.isEmpty())
        {
            counter = 1;
            return;
        }
        counter = heapAddresses.getLast()+1;
    }

    @Override
    public Value lookUp(Integer key) {
        if(!containsKey(key))
            return null;
        return heap.get(key);
    }

    @Override
    public Integer getKey(Value value) {
        for (Map.Entry<Integer, Value> entry : heap.entrySet()) {
            if (entry.getValue().equals(value)) { // Use equals() to compare values
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Integer key) {
        return heap.containsKey(key);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer,Value> entry : heap.entrySet())
        {
            sb.append(entry.getKey().toString() + "->" + entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
