package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumenContableController implements Initializable {
    private HelloApplication principalStage;
    @FXML
    private Label compLabel;
    @FXML
    private Label ventLabel;
    @FXML
    private Label ganLabel;

    public void setValues(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Summary");
        Document resumen = collection.find().first();
        if(resumen != null){
            float ventasActual = resumen.getDouble("totalVentas").floatValue();
            float comprasActual = resumen.getDouble("totalCompras").floatValue();
            float ganosActual = ventasActual - comprasActual;

            ventLabel.setText(String.valueOf(ventasActual));
            compLabel.setText(String.valueOf(comprasActual));
            ganLabel.setText(String.valueOf(ganosActual));
        }
        MongoDBConnection.close();
    }

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
        setValues();
    }
}