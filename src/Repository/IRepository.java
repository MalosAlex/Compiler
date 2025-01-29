package Repository;

import Model.PrgState;

import java.util.List;

public interface IRepository {
    //PrgState getCurrentState();
    void add(PrgState state);
    void logPrgStateExec(PrgState state);
    void update(PrgState state);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);
    int lng();
}
