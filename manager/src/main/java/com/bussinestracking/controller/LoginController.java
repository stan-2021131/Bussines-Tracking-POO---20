package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;

    private HelloApplication principalStage;

    public HelloApplication getPrincipalStage() {
        return principalStage;
    }

    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }
    public void principalView(){
        principalStage.ventanaPrincipal();
    }
    public void menuView(){
        MongoDBConnection.conexion();
        String name = userField.getText();
        String pass = passField.getText();
        if(name.isEmpty() || pass.isEmpty()){
            JOptionPane.showMessageDialog(null, "Existen espacios vacios, rellenalos de nuevo");
            return;
        }
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Users");
        Document data = new Document("username", name).append("password", pass);
        MongoCursor<Document> cursor = collection.find(data).iterator();
        if(cursor.hasNext()){
            Document doc = cursor.next();
            JOptionPane.showMessageDialog(null, "Bienvenido " + doc.getString("username") +" , iniciando sesión");
            principalStage.menuView();
            return;
        }else{
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
        MongoDBConnection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
