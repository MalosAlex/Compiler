package Model.Stmt;

import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import Utils.MyIDictionary;

public class VarDeclStmt implements IStmt{
    private String id;
    private Type typ;

    public VarDeclStmt(String n, Type t)
    {
        id = n;
        typ = t;
    }

    public String toString()
    {
        return typ.toString() + ' ' + id;
    }

    public PrgState execute(PrgState state) throws StmtException
    {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        if(symtbl.containsKey(id))
            throw new StmtException("The variable is already declared");
        else
            symtbl.put(id,typ.defaultValue());
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{

        typeEnv.put(id,typ);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new VarDeclStmt(id, typ.deepCopy());
    }
}
