package Model.Stmt;

import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Utils.MyIDictionary;

public class NopStmt implements IStmt{
    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        return typeEnv;
    }
    public String toString()
    {
        return "Nop";
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new NopStmt();
    }
}
