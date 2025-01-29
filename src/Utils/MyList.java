package Utils;

import java.util.List;
import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList()
    {
        list = new ArrayList<T>();
    }

    @Override
    public void add(T t)
    {
        list.add(t);
    }

    public T get(int pos){
        return list.get(pos);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(T v : list)
        {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<T> getList() {
        return list;
    }
}
