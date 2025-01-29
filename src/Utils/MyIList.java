package Utils;

import java.util.List;

public interface MyIList<T> {
    public void add(T t);
    T get(int pos);
    List<T> getList();
}
