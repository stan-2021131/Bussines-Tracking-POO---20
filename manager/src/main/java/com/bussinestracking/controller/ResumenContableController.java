package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.Categoria;
import com.bussinestracking.model.MongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class ResumenContableController implements Initializable {
    private HelloApplication principalStage;
    String[] parametros_2 = {"idResumen", "totalCompras", "totalVentas"}

    boolean isSelected = false;
    @FXML
    private Label TOTALINVERTIDO;
    @FXML
    private Label TOTALVENDIDO;
    @FXML
    private Label TOTALCOMPRADO;

    private void getCategorias() {
        ResumenContable.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("ResumenContable");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Categoria categoria = new Categoria(document.getObjectId(parametros_2[0]).toString(), document.getString(parametros_2[1]), document.getString(parametros[2]));
            ResumenContable.add(categoria);
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
    }
}