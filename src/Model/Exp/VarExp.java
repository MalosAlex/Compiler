package Model.Exp;

import Model.Type.Type;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class VarExp implements Exp{
    private String id;
    public VarExp(String name)
    {
        this.id = name;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException
    {
        return tbl.lookUp(id);
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv)throws ExpException{
        return typeEnv.lookUp(id);
    }

    public String toString(){
        return id;
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }
}
