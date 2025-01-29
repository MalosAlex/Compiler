package Model.Value;

import Model.Type.*;

public class BoolValue implements Value{
    private boolean val;
    public BoolValue(boolean b)
    {
        val = b;
    }
    public boolean getVal()
    {
        return val;
    }
    public String toString()
    {
        return Boolean.toString(val);
    }

    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoolValue b)
        {
            return b.getVal() == this.val;
        }
        return false;
    }
}
