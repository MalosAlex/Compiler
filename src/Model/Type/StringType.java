package Model.Type;

import Model.Value.IntValue;
import Model.Value.StringValue;

public class StringType implements Type{
    public boolean equals(Object another){
        return another instanceof StringType;
    }
    public String toString() {
        return "String";
    }

    public StringValue defaultValue()
    {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
