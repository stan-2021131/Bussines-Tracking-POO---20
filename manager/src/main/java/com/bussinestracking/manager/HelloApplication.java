package com.bussinestracking.manager;

import com.bussinestracking.controller.HelloController;
import com.bussinestracking.controller.LoginController;
import com.bussinestracking.controller.MenuController;
import com.bussinestracking.controller.UserController;
import com.bussinestracking.model.MongoDBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class HelloApplication extends Application {
    private Stage principalStage;

    @Override
    public void start(Stage principalStage) throws Exception {
        this.principalStage = principalStage;
        ventanaPrincipal();
        principalStage.show();
    }

    public void ventanaPrincipal(){
        try{
            HelloController ventanaPrincipal = (HelloController)cambiarEscena("hello-view.fxml", 600,400);
            ventanaPrincipal.setPrincipalStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loginView() {
        try {
            LoginController ventanaLogin =(LoginController)cambiarEscena("Login.fxml", 600,400);
            ventanaLogin.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuView(){
        try {
            MenuController ventanaMenu = (MenuController)cambiarEscena("Menu Page.fxml", 895,511);
            ventanaMenu.setPrincipalPage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userView(){
        try {
            UserController ventanaUsers = (UserController)cambiarEscena("Users.fxml", 597,366);
            ventanaUsers.setPrincipalPage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        final String PAQUETE_VISTA = "/com/bussinestracking/manager/view/";
        InputStream archivo = HelloApplication.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(HelloController.class.getResource(PAQUETE_VISTA+fxml));
        Scene stage = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        principalStage.setScene(stage);
        principalStage.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }
}