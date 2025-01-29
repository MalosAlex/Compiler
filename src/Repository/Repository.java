package Repository;

import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> Repo;
    private PrintWriter logfile;
    private int pos;

    public Repository(PrgState p, String file) throws IOException {
        Repo = new ArrayList<PrgState>();
        Repo.add(p);
        pos = 0;
        logfile = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        logfile.println();
        logfile.flush();
        logfile = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
    }

    /*@Override
    public PrgState getCurrentState(){
        return Repo.get(pos);
    }*/

    @Override
    public List<PrgState> getPrgList()
    {
        return Repo;
    }

    public void setPrgList(List<PrgState> l)
    {
        Repo = l;
    }

    @Override
    public void add(PrgState state){
        Repo.add(state);
    }

    public void update(PrgState state){
        Repo.set(pos,state);
    }
    public void logPrgStateExec(PrgState state)
    {
        logfile.println(state.toString());
        logfile.println("");
        logfile.flush();
    }

    public int lng(){
        return Repo.size();
    }
}
