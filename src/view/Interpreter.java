package view;

import Controller.Controller;
import Model.Exp.ArithExp;
import Model.Exp.ValueExp;
import Model.Exp.*;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import Utils.*;

import java.io.BufferedReader;
import java.io.IOException;

public class Interpreter {
    public static void main(String args[]) throws IOException {

        // Text Menu init
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        // int v; v = 2; Print(v) is represented as:
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(18))), new PrintStmt(new VarExp("v"))));
        try{
            ex1.typecheck(typeEnv);
            MyIStack<IStmt> stack1 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary1 = new MyDictionary<String,Value>();
            MyIList<Value> output1 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft1 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap1 = new MyHeap<Value>();
            PrgState prg1 = new PrgState(stack1,dictionary1, output1, ex1, ft1, heap1);
            IRepository repo1 = new Repository(prg1,"log1.txt");
            Controller ctr1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", ex1.toString(),ctr1));
        }
        catch(ExpException e){
            System.out.println(e.toString());
        }

        //int a;int b; a=2+3*5;b=a+1;Print(b) is represented as:
        try{
            IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                    new CompStmt(new VarDeclStmt("b",new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
            try{
                ex2.typecheck(typeEnv);
                MyIStack<IStmt> stack2 = new MyStack<IStmt>();
                MyIDictionary<String, Value> dictionary2 = new MyDictionary<String,Value>();
                MyIList<Value> output2 = new MyList<Value>();
                IFileTable<StringValue, BufferedReader> ft2 = new FileTable<StringValue, BufferedReader>();
                MyIHeap<Integer, Value> heap2 = new MyHeap<Value>();
                PrgState prg2 = new PrgState(stack2,dictionary2, output2, ex2, ft2, heap2);
                IRepository repo2 = new Repository(prg2,"log2.txt");
                Controller ctr2 = new Controller(repo2);
                menu.addCommand(new RunExample("2", ex2.toString(),ctr2));
            }
            catch(ExpException e){
                System.out.println(e.toString());
            }
        } catch (StmtException e)
        {
            throw new RuntimeException(e);
        }

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v) is represented as
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        try{
            ex3.typecheck(typeEnv);
            MyIStack<IStmt> stack3 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary3 = new MyDictionary<String,Value>();
            MyIList<Value> output3 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft3 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap3 = new MyHeap<Value>();
            PrgState prg3 = new PrgState(stack3,dictionary3, output3, ex3, ft3, heap3);
            IRepository repo3 = new Repository(prg3,"log3.txt");
            Controller ctr3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", ex3.toString(),ctr3));
        }
        catch(ExpException e){
            System.out.println(e.toString());
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new BoolType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(
                        new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new readFile(new VarExp("varf"), "varc"), new CompStmt(
                                new PrintStmt(new VarExp("varc")), new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));
        try {
            ex4.typecheck(typeEnv);
            MyIStack<IStmt> stack4 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary4 = new MyDictionary<String, Value>();
            MyIList<Value> output4 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft4 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap4 = new MyHeap<Value>();
            PrgState prg4 = new PrgState(stack4, dictionary4, output4, ex4, ft4, heap4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            Controller ctr4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", ex4.toString(),ctr4));
        }
        catch(ExpException e){
            System.out.println(e.toString());
        }

        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())), // Ref int v;
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))), // new(v, 20);
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), // Ref Ref int a;
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")), // new(a, v);
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))), // new(v, 30);
                                                new PrintStmt(
                                                        new rHExp(
                                                                new rHExp(new VarExp("a")) // rH(rH(a))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        try {
            ex5.typecheck(typeEnv);
            MyIStack<IStmt> stack5 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary5 = new MyDictionary<String, Value>();
            MyIList<Value> output5 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft5 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap5 = new MyHeap<Value>();
            PrgState prg5 = new PrgState(stack5, dictionary5, output5, ex5, ft5, heap5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            Controller ctr5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", ex5.toString(),ctr5));
        }
        catch(ExpException e){
            System.out.println(e.toString());
        }

        IStmt ex6 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        try {
            ex6.typecheck(typeEnv);
            MyIStack<IStmt> stack6 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary6 = new MyDictionary<String, Value>();
            MyIList<Value> output6 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft6 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap6 = new MyHeap<Value>();
            PrgState prg6 = new PrgState(stack6, dictionary6, output6, ex6, ft6, heap6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            Controller ctr6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", ex6.toString(),ctr6));
        }
        catch(ExpException e){
            System.out.println(e.toString());
        }

        IStmt ex7 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new rHExp(new VarExp("v"))),
                                new CompStmt(
                                        new wHStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new rHExp(new VarExp("v")), new ValueExp(new IntValue(5))))
                                )
                        )
                )
        );
        try {
            ex7.typecheck(typeEnv);
            MyIStack<IStmt> stack7 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary7 = new MyDictionary<String, Value>();
            MyIList<Value> output7 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft7 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap7 = new MyHeap<Value>();
            PrgState prg7 = new PrgState(stack7, dictionary7, output7, ex7, ft7, heap7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            Controller ctr7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", ex7.toString(),ctr7));
        }
        catch (ExpException e){
            System.out.println(e.toString());
        }

        IStmt ex8 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(new wHStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new rHExp(new VarExp("a")))
                                                                        )
                                                                ))
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new rHExp(new VarExp("a")))
                                                )

                                        )
                                )
                        )
                )
        );
        try {
            ex8.typecheck(typeEnv);
            MyIStack<IStmt> stack8 = new MyStack<IStmt>();
            MyIDictionary<String, Value> dictionary8 = new MyDictionary<String, Value>();
            MyIList<Value> output8 = new MyList<Value>();
            IFileTable<StringValue, BufferedReader> ft8 = new FileTable<StringValue, BufferedReader>();
            MyIHeap<Integer, Value> heap8 = new MyHeap<Value>();
            PrgState prg8 = new PrgState(stack8, dictionary8, output8, ex8, ft8, heap8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            Controller ctr8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", ex8.toString(),ctr8));
        }
        catch(ExpException e)
        {
            System.out.println(e.toString());
        }
        menu.show();
    }
}
