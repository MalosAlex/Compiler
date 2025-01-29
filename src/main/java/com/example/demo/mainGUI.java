package com.example.demo;

import Controller.Controller;
import Model.Exp.*;
import Model.PrgState;
import Model.Stmt.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import Utils.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.RunExample;

import java.io.BufferedReader;
import java.io.IOException;

public class mainGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectProgram.fxml"));

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Choose Program");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }


}


