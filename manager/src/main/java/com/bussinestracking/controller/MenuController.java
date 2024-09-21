package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private HelloApplication principalPage;

    public HelloApplication getPrincipalPage() {
        return principalPage;
    }
    public void setPrincipalPage(HelloApplication principalPage) {
        this.principalPage = principalPage;
    }
    public void loginView() {
        principalPage.loginView();
    }
    public void usersView() {
        principalPage.userView();
    }
    public void inventario() {
        principalPage.inventarioView();
    }
    public void reporteDiarioView() {
        principalPage.reporteDiarioView();
    }
    public void ventasView() {
        principalPage.ventasView();
    }
    public void comprasView() {
        principalPage.comprasView();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
