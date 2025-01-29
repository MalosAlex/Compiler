package Controller;

import Model.Value.RefValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Model.PrgState;
import Model.Stmt.IStmt;
import Repository.Repository;
import Utils.*;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        List<Integer> HeapRefferenced = getHeapRefferenced(heap);

        Set<Integer> allAddresses = new HashSet<Integer>(symTableAddr);
        allAddresses.addAll(HeapRefferenced);
        return heap.entrySet().stream()
                .filter(e -> allAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getHeapRefferenced(Map<Integer, Value> heap) {
        return heap.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public IRepository getRepo(){
        return repo;
    }

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty())
        {
           /* for(PrgState prg:prgList)
                prg.getHeap().setContent(safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()),
                    prg.getHeap().getContent()));*/
            try{
                oneStepForAllPrg(prgList);
            }
            catch (InterruptedException e)
            {
                throw new ADTException(e.toString());
            }
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        //update the repository state
        repo.setPrgList(prgList);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws ADTException, InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        //RUN concurrently one step for each of the existing PrgStates

        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p)->(Callable<PrgState>) (p::oneStep)).toList();

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (ExecutionException | InterruptedException e)
            {
                throw new ADTException(e.toString());
            }
        }).filter(Objects::nonNull).toList();
        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg->repo.logPrgStateExec(prg));
        //Save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public int getRepoLength(){
        return repo.lng();
    }

    public MyIHeap<Integer, Value> getHeap(){
        return repo.getPrgList().getFirst().getHeap();
    }

    public MyIDictionary<String,Value> getSymbTbl(){
        return repo.getPrgList().getFirst().getSymTable();
    }

    public MyIList<Value> getOut(){
        return repo.getPrgList().getFirst().getOut();
    }

    public IFileTable<StringValue, BufferedReader> getFileTable(){
        return repo.getPrgList().getFirst().getFileTable();
    }

    public List<PrgState> getPrgs(){
       return repo.getPrgList().stream().filter(Objects::nonNull).toList();
    }

}
