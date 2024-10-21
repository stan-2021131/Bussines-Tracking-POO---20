package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.Categoria;
import com.bussinestracking.model.MongoDBConnection;
import com.bussinestracking.model.Producto;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {
    private HelloApplication principalStage;
    String[] parametros2 = {"_idProducto", "nombreProducto", "descripcionProducto"};
    public HelloApplication getPrincipalStage() {
        return principalStage;
    }
    boolean isSelected = false;

    @FXML
    private TableView<Producto> productoTable;
    @FXML
    private TableColumn<Producto, String> nombCol;
    @FXML
    private TableColumn<Producto, String> descCol;
    @FXML
    private TextField nombField;
    @FXML
    private TextArea descripArea;
    @FXML
    private Button addUpBtn;
    @FXML
    private Button delBtn;
    private ObservableList<Producto> productos;
    Document pro = new Document();

    private void getProductos() {
        productos.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Categories");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Producto producto = new Producto(document.getObjectId(parametros2[0]).toString(), document.getString(parametros2[1]), document.getString(parametros2[2]));
            productos.add(producto);
        }
        MongoDBConnection.close();
    }

    public void selectElement(){
        if(productoTable.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null,"No se ha seleccionado un elemento de la tabla");
        }else{
            isSelected = true;
            String id = String.valueOf(((Producto)productoTable.getSelectionModel().getSelectedItem()).getIdProducto());
            nombField.setText(String.valueOf(((Producto)productoTable.getSelectionModel().getSelectedItem()).getNombre()));
            descripArea.setText(String.valueOf(((Producto)productoTable.getSelectionModel().getSelectedItem()).getDescripcion()));
            addUpBtn.setText("Actualizar");
            addUpBtn.setDisable(false);
            delBtn.setDisable(false);
            pro.append(parametros2[0], new ObjectId(id));
        }
    }

    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Categories");
        if(addUpBtn.getText().equals("Agregar")){
            Document exist = new Document(parametros2[1], nombField.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                JOptionPane.showMessageDialog(null,"Ya existe una categoría con este nombre.");
            }else{
                Document newCat = new Document(parametros2[1], nombField.getText()).append(parametros2[2], descripArea.getText());
                collection.insertOne(newCat);
                JOptionPane.showMessageDialog(null, "Nueva categoría agregada.");
            }
        } else if (addUpBtn.getText().equals("Actualizar")) {
            Document exist = collection.find(pro).first();
            if(exist != null){
                Document newData = new Document(parametros2[1], nombField.getText()).append(parametros2[2], descripArea.getText());
                Document up = new Document("$set", newData);
                collection.updateOne(exist, up);
                JOptionPane.showMessageDialog(null, "Categoría actualizada.");
            }else{
                JOptionPane.showMessageDialog(null,"No se ha encotrado la categoría");
            }
            addUpBtn.setText("Agregar");
            delBtn.setDisable(true);
        }
        getProductos();
        clearControls();
    }

    public void delete(){
        int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la categoría?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            MongoDBConnection.conexion();
            MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Categories");
            Document exist = new Document(parametros2[1], nombField.getText()).append(parametros2[2], descripArea.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                collection.deleteOne(alreadyExist);
                JOptionPane.showMessageDialog(null, "Categoría eliminada");
            }else{
                JOptionPane.showMessageDialog(null, "Categoría no encontrada");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Se ha cancelado la eliminación");
        }
        clearControls();
        addUpBtn.setText("Agregar");
        delBtn.setDisable(true);
        getProductos();
    }

    public void clearControls(){
        nombField.clear();
        descripArea.clear();
        productoTable.getSelectionModel().clearSelection();
        isSelected = false;
        pro.clear();
    }
    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }

    public void principalStage(){
        principalStage.menuView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}