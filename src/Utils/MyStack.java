package Utils;

import java.util.*;

public class MyStack<T> implements MyIStack<T>{
    private Deque<T> d;

    public MyStack()
    {
        this.d = new ArrayDeque<T>();
    }

    @Override
    public void push(T t)
    {
        d.push(t);
    }

    @Override
    public T pop() throws ADTException {
        if(d.isEmpty())
            throw new ADTException("Stack is empty!");
        return d.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return d.isEmpty();
    }

    public List<T> toListS() {
        List<T> list = new LinkedList<>();
        for (T elem : d) {
            list.add(elem); // Add elements in stack order
        }
        return list;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (T el : d){
            sb.append(el.toString()).append("\n");
        }
        return sb.toString();
    }
}
