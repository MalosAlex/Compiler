package Model.Stmt;

import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Utils.MyIDictionary;
import Utils.MyIStack;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt second;
    public CompStmt(IStmt first, IStmt second)
    {
        this.first = first;
        this.second = second;
    }
    public PrgState execute(PrgState s)
    {
        MyIStack stack = s.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(first.toString() + "," + second.toString() + " ");
        return sb.toString();
    }

    public IStmt getFirst()
    {
        return first;
    }

    public IStmt getSecond()
    {
        return second;
    }

    @Override
    public IStmt deepCopy() throws StmtException {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
