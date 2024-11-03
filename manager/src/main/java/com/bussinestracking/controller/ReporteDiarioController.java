package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import javafx.fxml.Initializable;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;

public class ReporteDiarioController implements Initializable {
    private HelloApplication principalStage;

    public HelloApplication getPrincipalStage() {
        return principalStage;
    }

    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }

    public void principalStage(){
        principalStage.menuView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }
}