package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;
import Utils.MyDictionary;
import Utils.MyIDictionary;
import Utils.MyIStack;
import Model.Type.*;

public class IfStmt implements IStmt{
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el)
    {
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }

    public String toString()
    {
        return "If(" + exp.toString() + ") Then (" + thenS.toString() + ") Else("+elseS.toString()+")";
    }

    public PrgState execute(PrgState state) throws StmtException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIStack<IStmt> stk = state.getExeStack();
        if(this.exp.eval(tbl,state.getHeap()).getType().equals(new BoolType()))
        {
            BoolValue b = (BoolValue) this.exp.eval(tbl, state.getHeap());
            if(b.getVal())
            {
                stk.push(thenS);
            }
            else
            {
                stk.push(elseS);
            }
            return null;
        }
        else
            throw new StmtException("conditional expression not a boolean type");
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new ExpException("The condition of IF has not the type bool");
    }

    @Override
    public IStmt deepCopy() throws ExpException, StmtException {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
