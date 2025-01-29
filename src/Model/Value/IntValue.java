package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value{
    private int val;

    public IntValue(int v)
    {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return Integer.toString(val);
    }

    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IntValue i)
        {
            return i.getVal() == this.val;
        }
        return false;
    }
}
