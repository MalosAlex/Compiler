package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class wHStmt implements IStmt{
    private String var_name;
    Exp exp;

    public wHStmt(String vn, Exp e)
    {
        var_name = vn;
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExpException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.containsKey(var_name))
        {
            Value v = symTbl.lookUp(var_name);
            if(v instanceof RefValue)
            {
                RefValue rv = (RefValue) v;
                int adr = rv.getAddress();
                if(heap.containsKey(adr))
                {
                    Value val = exp.eval(symTbl, heap);
                    if(val.getType().equals(rv.getLocationType()))
                    {
                        heap.update(adr, val);
                        return null;
                    }
                    else throw new StmtException("The types are different");
                }
                else throw new StmtException("The address isn't in the heap");
            }
            else throw new StmtException("Variable isn't a reference type");
        }
        else throw new StmtException("Variable doesn't exist in the symbol table");
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new wHStmt(var_name, exp);
    }

    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typexp = exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString()
    {
        return "wh("+ var_name + ", " + exp.toString() + ")";
    }
}
