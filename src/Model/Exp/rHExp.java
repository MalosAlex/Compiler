package Model.Exp;

import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class rHExp implements Exp{
    private Exp exp;

    public rHExp(Exp e)
    {
        exp = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException {
        Value v = exp.eval(tbl, heap);
        if (v instanceof RefValue)
        {
            RefValue rv = (RefValue) v;
            int k = rv.getAddress();
            if(heap.containsKey(k))
            {
                return heap.lookUp(k);
            }
            else
                throw new ExpException("The heap is empty at that address");
        }
        else
        {System.out.println("AAA");
            throw new ExpException("Expression is not an RefValue");}
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new ExpException("the rH argument is not a Ref Type");
    }

    @Override
    public Exp deepCopy() throws ExpException {
        return new rHExp(exp);
    }

    public String toString()
    {
        return "rH(" + exp.toString() + ")";
    }
}
