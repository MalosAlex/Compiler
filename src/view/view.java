package view;

import Controller.Controller;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.*;
import Model.Exp.*;
import Repository.IRepository;
import Repository.Repository;
import Utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class view {
    public static void main(String[] args) throws StmtException, IOException {

        // int v; v = 2; Print(v) is represented as:
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(18))), new PrintStmt(new VarExp("v"))));
        //int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
        IStmt ex2;
        try{
            ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                    new CompStmt(new VarDeclStmt("b",new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        } catch (StmtException e)
        {
            throw new RuntimeException(e);
        }

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v) is represented as
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(
                        new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(
                                new PrintStmt(new VarExp("varc")), new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));
        Scanner scanner = new Scanner(System.in);
        int choice;
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> dictionary = new MyDictionary<String,Value>();
        MyIList<Value> output = new MyList<Value>();
        FileTable<StringValue, BufferedReader> ft = new FileTable<StringValue, BufferedReader>();
        PrgState prg = null;
        do{
            System.out.println("1. Enter a program");
            System.out.println("2. Exit");
            System.out.println(">");
            choice = scanner.nextInt();

            if (choice == 1){
                System.out.println("Choose one of the 3 programs: ");
                int prgInp = scanner.nextInt();
                switch (prgInp){
                    case 1 :
                        //prg = new PrgState(stack,dictionary, output, ex1, ft);
                        break;
                    case 2 :
                        //prg = new PrgState(stack,dictionary, output, ex2, ft);
                        break;
                    case 3 :
                        //prg = new PrgState(stack,dictionary, output, ex3, ft);
                        break;
                    case 4:
                        //prg = new PrgState(stack,dictionary, output, ex4, ft);
                        break;
                    default:
                        System.out.println("Wrong number. Try again.");
                        break;
                }
                if(prg!=null) {
                    IRepository repo = new Repository(prg, "test.txt");
                    Controller ctr = new Controller(repo);
                    try {
                        ctr.allStep();
                    } catch (ADTException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
        } while(choice != 2);

    }
}
