package Model.Type;

import Model.Value.BoolValue;

public class BoolType implements Type{

    public boolean equals(Object another) {
        return another instanceof BoolType;
    }
    public String toString()
    {
        return "bool";
    }
    public BoolValue defaultValue()
    {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
