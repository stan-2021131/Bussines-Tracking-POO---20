package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.Categoria;
import com.bussinestracking.model.MongoDBConnection;
import com.bussinestracking.model.Proveedor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.types.ObjectId;
import org.bson.Document;
import javax.print.Doc;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProveedorController implements Initializable{
    private HelloApplication principalStage;
    String[] parametros = {"_id", "nombre", "descripcion"};
    boolean isSelected = false;
    @FXML
    private TextField nombreprov;
    @FXML
    private TextField descripcionprov;
    @FXML
    private Button eliminarprov;
    @FXML
    private Button agregarprov;
    @FXML
    private TableView <Proveedor> tablaprov;
    @FXML
    private TableColumn <Proveedor, String> nombrecolprov;
    @FXML
    private TableColumn <Proveedor, String> descripcolprov;
    private ObservableList<Proveedor> proveedores;
    Document docprov = new Document();

    private void getProveedores() {
        proveedores.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Supplier");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Proveedor proveedor = new Proveedor(document.getObjectId(parametros[0]).toString(), document.getString(parametros[1]),document.getString(parametros[2]));
            proveedores.add(proveedor);
        }
        MongoDBConnection.close();
    }

    public void selectElement(){
        if (tablaprov.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un elemento de la tabla");
        }else{
            isSelected = true;
            String id = String.valueOf(((Proveedor)tablaprov.getSelectionModel().getSelectedItem()).getIdproveedor());
            nombreprov.setText(String.valueOf(((Proveedor)tablaprov.getSelectionModel().getSelectedItem()).getNombre()));
            agregarprov.setText("Actualizar");
            agregarprov.setDisable(false);
            eliminarprov.setDisable(false);
            docprov.append(parametros[0], new ObjectId(id));
        }
    }

    public void clearControls(){
        nombreprov.clear();
        descripcionprov.clear();
        tablaprov.getSelectionModel().clearSelection();
        isSelected = false;
        docprov.clear();
    }

    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Supplier");
        if (agregarprov.getText().equals("Actualizar")) {
            Document exist = new Document(parametros[1], nombreprov.getText());
            Document alreadyExist = collection.find(exist).first();
            if (alreadyExist != null) {
                JOptionPane.showMessageDialog(null, "El proveedor ya existe");
            }else {
                Document newdocprov = new Document(parametros[1], nombreprov.getText()).append(parametros[2], descripcionprov.getText());
                collection.insertOne(newdocprov);
                JOptionPane.showMessageDialog(null, "El proveedor se ha agregado");
            }
        } else if (agregarprov.getText().equals("Actualizar")) {
            Document exist = collection.find(docprov).first();
            if (exist != null) {
                Document newData =new Document(parametros[1], nombreprov.getText()).append(parametros[2], descripcionprov.getText());
                Document up = new Document("$set", newData);
                collection.updateOne(exist, up);
                JOptionPane.showMessageDialog(null, "El proveedor se ha actualizado");
            }else{
                JOptionPane.showMessageDialog(null, "El proveedor no existe");
            }
            agregarprov.setText("Agregar");
            eliminarprov.setDisable(true);
        }
        getProveedores();
        clearControls();
    }
    public void delete(){
        int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el proveedor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            MongoDBConnection.conexion();
            MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Supplier");
            Document exist = new Document(parametros[1], nombreprov.getText()).append(parametros[2], descripcionprov.getText());
            Document alreadyExist = collection.find(exist).first();
            if (alreadyExist != null) {
                collection.deleteOne(alreadyExist);
                JOptionPane.showMessageDialog(null, "El proveedor se ha eliminado");
            }else {
                JOptionPane.showMessageDialog(null, "El proveedor no existe");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Se ha cancelado la eliminacion");
        }
        clearControls();
        agregarprov.setText("Agregar");
        eliminarprov.setDisable(true);
        getProveedores();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombrecolprov.setCellValueFactory(new PropertyValueFactory<>(parametros[1]));
        descripcolprov.setCellValueFactory(new PropertyValueFactory<>(parametros[2]));
        proveedores = FXCollections.observableArrayList();
        getProveedores();
        tablaprov.setItems(proveedores);
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


}
