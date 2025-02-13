package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString() {
        return "int";
    }

    public IntValue defaultValue()
    {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
