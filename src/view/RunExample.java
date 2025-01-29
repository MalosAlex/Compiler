package view;

import Controller.Controller;
import Utils.ADTException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String k, String d, Controller ctr)
    {
        super(k,d);
        controller = ctr;
    }

    @Override
    public void Execute()
    {
        try{
            controller.allStep();
        }
        catch (ADTException e)
        {
            System.out.println(e.toString());
        }
    }

}
