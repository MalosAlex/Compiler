package com.example.demo;

import Controller.Controller;  // Assuming Controller is in this package
import Model.PrgState;
import Model.Value.StringValue;
import Repository.IRepository;
import Utils.ADTException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class mainWindowController {
    private Controller ctr;
    @FXML
    private TextField PrgStates;
    @FXML
    private TableView<Map.Entry<String, String>> heapTable;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> addressCol;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> valCol;
    @FXML
    private ListView<String> Out;
    @FXML
    private ListView<String> FileTable;
    @FXML
    private ListView<String> PrgStateIds;
    @FXML
    private TableView<Map.Entry<String, String>> tblView;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> varCol;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> SymValCol;
    @FXML
    private ListView<String> ExeStack;
    @FXML
    private Button RunStep;

    private List<PrgState> prgList;
    private ExecutorService executor;
    private IRepository repo;
    private int i = 0;
    private PrgState prg;

    public void setController(Controller ctr) {
        this.ctr = ctr;
        this.repo = ctr.getRepo();
        // Use the ctr object and its methods here
        System.out.println("Selected program state: " + ctr); // Example usage
        initialize();
    }

    public void initialize(){
        if(ctr == null )
            return;
        //set number of prgStates
        PrgStates.clear();
        Integer c = ctr.getRepoLength();
        if(c == 0)
            return;
        PrgStates.appendText(c.toString());

        //write the heap table

        List<Map.Entry<String,String>> entryList = ctr.getHeap().getContent().entrySet().stream().
                map(entry -> Map.entry(entry.getKey().toString(), entry.getValue().toString())).toList();
        ObservableList<Map.Entry<String,String>> data = FXCollections.observableArrayList(entryList);
        heapTable.setItems(data);
        // Bind columns to the entry key and value
        addressCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey()));
        valCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValue()));

        //write the out table
        List<String> outList = ctr.getOut().getList().stream().map(Object::toString).toList();
        ObservableList<String> outData = FXCollections.observableArrayList(outList);
        Out.setItems(outData);

        //write the file table
        List<String> fileList = ctr.getFileTable().getTable().keySet().stream().map(StringValue::toString).toList();
        ObservableList<String> fileData = FXCollections.observableArrayList(fileList);
        FileTable.setItems(fileData);

        //write the prgState ids
        List<String> prgStates = ctr.getPrgs().stream().map(entry -> String.valueOf(entry.getId())).toList();
        ObservableList<String> prgData = FXCollections.observableArrayList(prgStates);
        PrgStateIds.setItems(prgData);

        if(c == 1 || prg == null)
        {
            List<Map.Entry<String,String>> symbList = ctr.getSymbTbl().getContent().entrySet().stream().
                    map(entry -> Map.entry(entry.getKey(), entry.getValue().toString())).toList();
            ObservableList<Map.Entry<String,String>> symbData = FXCollections.observableArrayList(symbList);
            tblView.setItems(symbData);
            varCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey()));
            SymValCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValue()));

            List<String> exeStack = ctr.getPrgs().get(0).GUIdistinctStatements();
            ObservableList<String> exeData = FXCollections.observableArrayList(exeStack);
            ExeStack.setItems(exeData);

        }
        else{
            List<Map.Entry<String,String>> symbList = prg.getSymTable().getContent().entrySet().stream().
                    map(entry -> Map.entry(entry.getKey(), entry.getValue().toString())).toList();
            ObservableList<Map.Entry<String,String>> symbData = FXCollections.observableArrayList(symbList);
            tblView.setItems(symbData);
            varCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey()));
            SymValCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getValue()));

            List<String> exeStack = prg.GUIdistinctStatements();
            ObservableList<String> exeData = FXCollections.observableArrayList(exeStack);
            ExeStack.setItems(exeData);
        }
    }

    public void RunStep() throws InterruptedException {
        i+=1;

        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        //List<PrgState> prgList=repo.getPrgList();
        if(!prgList.isEmpty())
        {
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
        //System.out.println("Setting repository program list: " + prgList);
        if(!prgList.isEmpty())
            repo.setPrgList(prgList);
        //System.out.println("Repository updated: " + repo.getPrgList());
        initialize();
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
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

    public void selectPrgState(MouseEvent event){
        if (event.getClickCount() == 2) {
            int selectedIndex = PrgStateIds.getSelectionModel().getSelectedIndex();
            prg = repo.getPrgList().get(selectedIndex);

            if (prg == null) {
                System.out.println("Selected PrgState is null");
            } else {
                initialize();
            }
        }
    }

}
