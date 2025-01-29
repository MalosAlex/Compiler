package Model.Stmt;

import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Utils.MyIDictionary;

public interface IStmt {
    PrgState execute(PrgState state) throws StmtException, ExpException;
    IStmt deepCopy() throws StmtException, ExpException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException;
}
