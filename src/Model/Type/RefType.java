package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type t) {
        inner = t;
    }
    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another)
    {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    public String toString() { return "Ref " + inner.toString();}

    public Value defaultValue(){
        return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner);
    }
}