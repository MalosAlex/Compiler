package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Utils.MyIDictionary;
import Utils.MyIStack;
import Model.Value.Value;

public class AssignStmt implements IStmt{
    private String id;
    private Exp exp;

    public AssignStmt(String i, Exp e)
    {
        this.id = i;
        this.exp = e;
    }
    public String toString()
    {
        return id+"="+exp.toString();
    }
    public PrgState execute(PrgState state) throws StmtException, ExpException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.containsKey(id))
        {
            Value val = exp.eval(symTbl, state.getHeap());
            Type typeId = symTbl.lookUp(id).getType();
            if(val.getType().equals(typeId))
                symTbl.update(id,val);
            else
                throw new StmtException("Type of variable "+id+" and the type of the assigned expression do not match");
        }
        else throw new StmtException("The variable " +id+ " is not declared");
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typevar = typeEnv.lookUp(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new ExpException("Assignment: right hand side and left hand side have different types ");
    }

    public AssignStmt deepCopy() throws ExpException
    {
        return new AssignStmt(id, exp.deepCopy());
    }
}
