package Model.Value;

import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringValue implements Value{
    private String val;

    public StringValue(String v)
    {
        val = v;
    }

    public String getVal() {
        return val;
    }

    public String toString() {
        return val;
    }

    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StringValue s)
        {
            return Objects.equals(s.getVal(), this.val);
        }
        return false;
    }
}
