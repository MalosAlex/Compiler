package Utils;

import java.util.List;

public interface MyIStack<T> {
    void push(T t);
    T pop() throws ADTException;
    public List<T> toListS();
    boolean isEmpty();
}
