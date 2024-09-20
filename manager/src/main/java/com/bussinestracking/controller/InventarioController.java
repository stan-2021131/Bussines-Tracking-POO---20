package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {
    private HelloApplication principalStage;

    public HelloApplication getPrincipalStage() {
        return principalStage;
    }

    public void principalStage(){
        principalStage.menuView();
    }

    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
