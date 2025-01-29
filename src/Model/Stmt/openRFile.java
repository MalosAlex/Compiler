package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.IFileTable;
import Utils.MyIDictionary;

import java.io.BufferedReader;
import java.io.FileReader;

public class openRFile implements IStmt{
    private Exp exp;
    private static Type t = new StringType();

    public openRFile(Exp e)
    {
        exp = e;
    }

    public String toString()
    {
        return "openRFile(" + exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws StmtException, ExpException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IFileTable<StringValue, BufferedReader> fT = state.getFileTable();
        Value v = exp.eval(symTbl,state.getHeap());
        if (v.getType().equals(t))
        {
            StringValue st = (StringValue) v;
            if(fT.containsKey(st))
                throw new StmtException("File already exists");
            else{
                try{
                    fT.put(st, new BufferedReader(new FileReader(st.getVal())));
                    return null;
                }
                catch (Exception e){
                    throw new StmtException("Unknown error sent");
                }
            }
        }
        else
            throw new StmtException("Not StringType");
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typexp = exp.typecheck(typeEnv);
        if (t.equals(typexp))
            return typeEnv;
        else
            throw new ExpException("OpenFile: The file name is not a String type ");
    }

    public openRFile deepCopy() throws ExpException
    {
        return new openRFile(exp.deepCopy());
    }
}
