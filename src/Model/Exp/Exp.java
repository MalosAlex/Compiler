package Model.Exp;

import Model.Type.Type;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public interface Exp {
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException;
    Exp deepCopy() throws ExpException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws ExpException;
}
