package Model.Exp;

import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class RelationalExp implements Exp{
    private String s;
    private Exp e1,e2;
    private Type t = new IntType();
    private int op; // 1 - <; 2 - <=; 3 - ==; 4 - !=; 5 - >; 5 - >=
    public RelationalExp(String sign, Exp a, Exp b) throws ExpException
    {
        this.s = sign;
        switch (sign) {
            case "<" -> this.op = 1;
            case "<=" -> this.op = 2;
            case "==" -> this.op = 3;
            case "!=" -> this.op = 4;
            case ">" -> this.op = 5;
            case ">=" -> this.op = 6;
            default -> throw new ExpException("This operation doesn't exist");
        }
        this.e1 = a;
        this.e2 = b;
    }
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws ExpException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(t)) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(t)) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                switch (this.op) {
                    case 1 -> {
                        return new BoolValue(n1 < n2);
                    }
                    case 2 -> {
                        return new BoolValue(n1 <= n2);
                    }
                    case 3 -> {
                        return new BoolValue(n1 == n2);
                    }
                    case 4 -> {
                        return new BoolValue(n1 != n2);
                    }
                    case 5 -> {
                        return new BoolValue(n1 > n2);
                    }
                    case 6 -> {
                        return new BoolValue(n1 >= n2);
                    }
                    default -> throw new ExpException("Error");
                }
            } else throw new ExpException("second operand is not integer");
        } else throw new ExpException("first operand is not integer");
    }

    public Type typecheck(MyIDictionary<String,Type> typeEnv){
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(t)) {
            if(typ2.equals(t)) {
                return new BoolType();
            }
            else throw new ExpException("second operand is not an integer");
        }
        else throw new ExpException("first operand is not an integer");
    }

    @Override
    public Exp deepCopy() throws ExpException {
        return new RelationalExp(s, e1.deepCopy(), e2.deepCopy());
    }

    public String toString()
    {
        return e1.toString() + " " + this.s + " " + e2.toString();
    }
}
