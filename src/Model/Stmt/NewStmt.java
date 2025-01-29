package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Utils.MyIDictionary;
import Utils.MyIHeap;

public class NewStmt implements IStmt{
    private String name;
    private Exp exp;
    public NewStmt(String var_name, Exp expression){
        name = var_name;
        exp = expression;
    }
    public PrgState execute(PrgState state){
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.containsKey(name))
        {
            Type type = symTbl.lookUp(name).getType();
            if(type instanceof RefType)
            {
                Value val = exp.eval(symTbl,state.getHeap());
                RefValue rV = (RefValue) symTbl.lookUp(name);
                if(val.getType().equals(rV.getLocationType()))
                {
                    MyIHeap<Integer,Value> heap = state.getHeap();
                    int address =heap.put(val);
                    symTbl.put(name, new RefValue(address, val.getType()));
                }
                else
                    throw new StmtException("The expression value isn't of the same type as the referenced one");
            }
            else
                throw new StmtException("The variable isn't a reference type");
        }
        else throw new StmtException("The variable " +name+ " is not declared");
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typevar = typeEnv.lookUp(name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new ExpException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() throws StmtException, ExpException {
        return new NewStmt(name, exp);
    }

    public String toString()
    {
        return "new(" + name + "," + exp.toString() + ")";
    }
}
