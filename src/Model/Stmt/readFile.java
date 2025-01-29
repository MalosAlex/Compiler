package Model.Stmt;

import Model.Exp.Exp;
import Model.Exp.ExpException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Utils.IFileTable;
import Utils.MyIDictionary;

import java.io.BufferedReader;
import java.io.FileReader;

public class readFile implements IStmt{
    private Exp exp;
    private static Type t = new IntType();
    private String name;

    public readFile(Exp e, String n)
    {
        exp = e;
        name = n;
    }

    public String toString()
    {
        return "readFile(" + exp.toString() + " and read data for " + name + ")";
    }

    public PrgState execute(PrgState state) throws StmtException, ExpException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IFileTable<StringValue, BufferedReader> fT = state.getFileTable();
        if(symTbl.containsKey(name) && symTbl.lookUp(name).getType().equals(t))
        {
            try{
                StringValue sv = (StringValue)exp.eval(symTbl, state.getHeap());
                BufferedReader read = fT.lookUp(sv);
                String val = read.readLine();
                int v = 0;
                if(val != null)
                {
                    v = Integer.parseInt(val);
                }
                symTbl.update(name, new IntValue(v));
            }
            catch(Exception e)
            {
                throw new StmtException("Error in readFile");
            }
        }
        else throw new StmtException("The variable is not declared");
        return null;
    }

    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws ExpException{
        Type typexp = exp.typecheck(typeEnv);
        Type typv = typeEnv.lookUp(name);
        if (t.equals(typv))
            return typeEnv;
        else
            throw new ExpException("ReadFile: The file name is not a String type ");
    }

    public openRFile deepCopy() throws ExpException
    {
        return new openRFile(exp.deepCopy());
    }
}
