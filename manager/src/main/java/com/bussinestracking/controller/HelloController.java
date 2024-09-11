package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private HelloApplication principalStage;

    public HelloApplication getPrincipalStage() {
        return principalStage;
    }
    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }
    public void loginView(){
        principalStage.loginView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}