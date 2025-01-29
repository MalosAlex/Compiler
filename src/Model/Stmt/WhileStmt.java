package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.MyDictionary;
import Utils.MyIDictionary;
import Utils.MyIStack;
import Utils.MyStack;

public class WhileStmt implements IStmt{
    Exp exp;
    IStmt stm;
    BoolType t = new BoolType();

    public WhileStmt(Exp e, IStmt s){
        exp = e;
        stm = s;
    }
    public PrgState execute(PrgState s){
        Value v = exp.eval(s.getSymTable(), s.getHeap());
        MyIStack<IStmt> stk = s.getExeStack();
        if(exp.eval(s.getSymTable(), s.getHeap()).getType().equals(t))
        {
            BoolValue bv = (BoolValue) v;
            if(bv.getVal() == true)
            {
                stk.push(this);
                stk.push(stm);
            }
        }
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(t)) {
            stm.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new ExpException("The condition of while has not the type bool");
    }

    public IStmt deepCopy() throws StmtException, ExpException {
        return new WhileStmt(exp, stm);
    }

    public String toString()
    {
        return "While (" + exp.toString() + ") " + stm.toString();
    }
}
