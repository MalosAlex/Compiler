package Model;

import Model.Stmt.CompStmt;
import Model.Stmt.IStmt;
import Model.Stmt.NopStmt;
import Model.Stmt.StmtException;
import Model.Value.StringValue;
import Model.Value.Value;

import Utils.*;


import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrgState {
    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, Value> getHeap(){
        return heap;
    }

    public int getId(){
       return fid;
    }

    public static synchronized int getNextId(){
        return ++id;
    }

    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram; //optional field, but good to have
    private IFileTable<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private static int id = 1;
    private final int fid;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg,
                    IFileTable<StringValue, BufferedReader> fT, MyIHeap<Integer, Value> h) throws StmtException {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fT;
        originalProgram = prg.deepCopy();//recreate the entire original prg
        heap = h;
        stk.push(prg);
        fid = getNextId();
    }

    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws ADTException {
        if(exeStack.isEmpty()) throw new ADTException("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(fid);
        s.append("\n");
        s.append("ExeStack:\n");
        //s.append(exeStack.toString());
        s.append(distinctStatements());
        s.append("SymTable:\n");
        s.append(symTable.toString());
        s.append("Out:\n");
        s.append(out.toString());
        s.append("FileTable:\n");
        s.append(fileTable.toString());
        s.append("Heap:\n");
        s.append(heap.toString());
        return s.toString();
    }

    public String distinctStatements() {
        MyTree<IStmt> tree = new MyTree<>();
        List<IStmt> inOrderList = new LinkedList<>();

        // Iterate over all elements in the execution stack
        List<IStmt> stackStatements = getExeStack().toListS();
        if (!stackStatements.isEmpty()) {
            Node<IStmt> root = null;
            for (IStmt stmt : stackStatements) {
                Node<IStmt> subtree = toTree(stmt); // Create a subtree for each statement
                if (root == null) {
                    root = subtree; // Initialize the tree with the first statement
                } else {
                    // Add subsequent statements as right children of the root
                    Node<IStmt> newRoot = new Node<>(new NopStmt());
                    newRoot.setLeft(root);
                    newRoot.setRight(subtree);
                    root = newRoot;
                }
            }
            tree.setRoot(root);
            tree.inorderTraversal(inOrderList, tree.getRoot());
        }

        StringBuilder sb = new StringBuilder();
        for (IStmt n : inOrderList) {
            if (!(n instanceof NopStmt)) {
                sb.append(n.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public List<String> GUIdistinctStatements() {
        MyTree<IStmt> tree = new MyTree<>();
        List<IStmt> inOrderList = new LinkedList<>();

        // Iterate over all elements in the execution stack
        List<IStmt> stackStatements = getExeStack().toListS();
        if (!stackStatements.isEmpty()) {
            Node<IStmt> root = null;
            for (IStmt stmt : stackStatements) {
                Node<IStmt> subtree = toTree(stmt); // Create a subtree for each statement
                if (root == null) {
                    root = subtree; // Initialize the tree with the first statement
                } else {
                    // Add subsequent statements as right children of the root
                    Node<IStmt> newRoot = new Node<>(new NopStmt());
                    newRoot.setLeft(root);
                    newRoot.setRight(subtree);
                    root = newRoot;
                }
            }
            tree.setRoot(root);
            tree.inorderTraversal(inOrderList, tree.getRoot());
        }
        List<String> l = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (IStmt n : inOrderList) {
            if (!(n instanceof NopStmt)) {
                l.add(n.toString());
            }
        }
        return l;
    }

    private Node<IStmt> toTree(IStmt stmt) {
        if (stmt instanceof CompStmt) {
            CompStmt compStmt = (CompStmt) stmt;
            Node<IStmt> node = new Node<>(new NopStmt());
            node.setLeft(toTree(compStmt.getFirst()));  // Recursive left subtree
            node.setRight(toTree(compStmt.getSecond())); // Recursive right subtree
            return node;
        } else {
            // Base case: single statement
            return new Node<>(stmt);
        }
    }


}
