package Model.Stmt;

import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Utils.MyIDictionary;
import Utils.MyIStack;
import Utils.MyStack;

public class ForkStmt implements IStmt{
    private IStmt stmt;
    public ForkStmt(IStmt stm)
    {
        stmt = stm;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException {
        MyIStack<IStmt> stk = new MyStack<>();
        return new PrgState(stk,state.getSymTable().deepCopy(), state.getOut(), stmt, state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new ForkStmt(stmt);
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    public String toString()
    {
        return "fork("+stmt.toString()+")";
    }
}
