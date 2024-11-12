package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ComprasController implements Initializable {
    private HelloApplication principalStage;
    String[] parametros = {"_id", "proveedor", "producto", "cantidad", "preciouni"};
    boolean isSelected = false;
    String[] parametrosprov = {"_id", "nombre", "descripcion"};


    @FXML
    private TextField productocompra;
    @FXML
    private TextField cantidadcompra;
    @FXML
    private TextField preciocompra;
    @FXML
    private Button agregarcompra;
    @FXML
    private TableView <Compra> tablacompra;
    @FXML
    private TableColumn <Compra, String> proveedorcolcompra;
    @FXML
    private TableColumn<Compra, String> productocolcompra;
    @FXML
    private TableColumn<Compra, Integer> cantidadcolcompra;
    @FXML
    private TableColumn<Compra, Float> preciocolcompra;
    @FXML
    private ComboBox<Proveedor> cmbproveedor;
    private ObservableList<Compra> compras;
    private ObservableList<Proveedor> proveedor;
    Document doccompra = new Document();


    private void getCompras(){
        compras.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Purchases");
        FindIterable<Document> iterable = data.find();
        for (Document document: iterable) {
            Proveedor proveedor = null;
            Document proveedordoc = document.get(parametros[1], Document.class);
            if (proveedordoc != null) {
                proveedor = new Proveedor(proveedordoc.getString(parametrosprov[0]), proveedordoc.getString(parametrosprov[1]), proveedordoc.getString(parametrosprov[2]));
            }
            Compra compra = new Compra(document.getObjectId(parametros[0]).toString(),proveedor, document.getString(parametros[2]), document.getInteger(parametros[3]), document.getDouble(parametros[4]).floatValue());
            compras.add(compra);
        }
        MongoDBConnection.close();
    }
    private void getProveedores() {
        proveedor.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Supplier");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Proveedor newProveedor = new Proveedor(document.getObjectId(parametrosprov[0]).toString(), document.getString(parametrosprov[1]),document.getString(parametrosprov[2]));
            proveedor.add(newProveedor);
        }
        MongoDBConnection.close();
    }
    public void clearControls(){
        productocompra.clear();
        cantidadcompra.clear();
        preciocompra.clear();
    }
    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Purchases");
        Proveedor proveedor2 = cmbproveedor.getValue();
        Document docprov = new Document(parametrosprov[0], proveedor2.getIdproveedor()).append(parametrosprov[1], proveedor2.getNombre()).append(parametrosprov[2], proveedor2.getDescripcion());
        Document doccompra = new Document(parametros[1], docprov).append(parametros[2], productocompra.getText()).append(parametros[3], Integer.parseInt(cantidadcompra.getText())).append(parametros[4], Float.parseFloat(preciocompra.getText()));
        collection.insertOne(doccompra);
        JOptionPane.showMessageDialog(null, "Compra agregada");
        getCompras();
        clearControls();
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
        proveedorcolcompra.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        productocolcompra.setCellValueFactory(new PropertyValueFactory<>("productos"));
        cantidadcolcompra.setCellValueFactory(new PropertyValueFactory<>(parametros[3]));
        preciocolcompra.setCellValueFactory(new PropertyValueFactory<>("precio"));
        proveedor = FXCollections.observableArrayList();
        getProveedores();
        cmbproveedor.setItems(proveedor);
        compras = FXCollections.observableArrayList();
        getCompras();
        tablacompra.setItems(compras);
    }
}