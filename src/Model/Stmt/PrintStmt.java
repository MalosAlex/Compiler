package Model.Stmt;

import Model.Exp.*;
import Model.PrgState;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIList;
import Model.Type.*;

public class PrintStmt implements IStmt{
    private Exp exp;

    public PrintStmt(Exp e){
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState s) throws StmtException {
        MyIList<Value> out= s.getOut();
        MyIDictionary<String, Value> symtbl = s.getSymTable();
        out.add(this.exp.eval(symtbl, s.getHeap()));
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString(){
        return "Print(" + exp + ")";
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new PrintStmt(exp.deepCopy());
    }
}
