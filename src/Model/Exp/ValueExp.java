package Model.Exp;

import Model.Type.Type;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class ValueExp implements Exp{
    private Value e;

    public ValueExp(Value v)
    {
        this.e = v;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException
    {
        return e;
    }

    public Type typecheck(MyIDictionary<String, Type> typeEnv)throws ExpException{
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }
}
