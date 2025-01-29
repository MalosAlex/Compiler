package Model.Exp;

import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private int op; // 1=and 2=or
    private String sign;
    private static Type t = new BoolType();

    public LogicExp(String s, Exp e_1, Exp e_2) throws ExpException
    {
        if(s.equals("add"))
            this.op = 1;
        else if(s.equals("or"))
            this.op = 2;
        else throw new ExpException("Operation does not exist");

        this.sign = s;
        this.e1 = e_1;
        this.e2 = e_2;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException
    {
        Value v1,v2;
        v1 = e1.eval(tbl, heap);
        if(v1.getType().equals(t))
        {
            v2 = e2.eval(tbl, heap);
            if(v2.getType().equals(t))
            {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean i1,i2;
                i1 = b1.getVal();
                i2 = b2.getVal();
                if(this.op == 1)
                {
                    return new BoolValue(i1 && i2);
                }
                else if(this.op == 2)
                {
                    return new BoolValue(i1 || i2);
                }
                else throw new ExpException("Error");
            }
            else throw new ExpException("second expression isn't boolean");
        }
        else throw new ExpException("first expression isn't boolean");
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(t)) {
            if(typ2.equals(t)) {
                return new BoolType();
            }
            else throw new ExpException("second operand is not a boolean");
        }
        else throw new ExpException("first operand is not a boolean");
    }

    @Override
    public Exp deepCopy() throws ExpException {
        return new LogicExp(sign, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString() {
        return e1.toString() + sign + e2.toString();
    }
}
