package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.Categoria;
import com.bussinestracking.model.MongoDBConnection;
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

public class CategoriasController implements Initializable {
    private HelloApplication principalStage;
    String[] parametros = {"_id", "nombre", "descripcion"};
    public HelloApplication getPrincipalStage() {
        return principalStage;
    }
    boolean isSelected = false;

    @FXML
    private TableView<Categoria> categoriaTable;
    @FXML
    private TableColumn<Categoria, String> nombCol;
    @FXML
    private TableColumn<Categoria, String> descCol;
    @FXML
    private TextField nombField;
    @FXML
    private TextArea descripArea;
    @FXML
    private Button addUpBtn;
    @FXML
    private Button delBtn;
    private ObservableList<Categoria> categorias;
    Document cat = new Document();

    private void getCategorias() {
        categorias.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Categories");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Categoria categoria = new Categoria(document.getObjectId(parametros[0]).toString(), document.getString(parametros[1]), document.getString(parametros[2]));
            categorias.add(categoria);
        }
        MongoDBConnection.close();
    }

    public void selectElement(){
        if(categoriaTable.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null,"No se ha seleccionado un elemento de la tabla");
        }else{
            isSelected = true;
            String id = String.valueOf(((Categoria)categoriaTable.getSelectionModel().getSelectedItem()).getIdCategoria());
            nombField.setText(String.valueOf(((Categoria)categoriaTable.getSelectionModel().getSelectedItem()).getNombre()));
            descripArea.setText(String.valueOf(((Categoria)categoriaTable.getSelectionModel().getSelectedItem()).getDescripcion()));
            addUpBtn.setText("Actualizar");
            addUpBtn.setDisable(false);
            delBtn.setDisable(false);
            cat.append(parametros[0], new ObjectId(id));
        }
    }

    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Categories");
        if(addUpBtn.getText().equals("Agregar")){
            Document exist = new Document(parametros[1], nombField.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                JOptionPane.showMessageDialog(null,"Ya existe una categoría con este nombre.");
            }else{
                Document newCat = new Document(parametros[1], nombField.getText()).append(parametros[2], descripArea.getText());
                collection.insertOne(newCat);
                JOptionPane.showMessageDialog(null, "Nueva categoría agregada.");
            }
        } else if (addUpBtn.getText().equals("Actualizar")) {
            Document exist = collection.find(cat).first();
            if(exist != null){
                Document newData = new Document(parametros[1], nombField.getText()).append(parametros[2], descripArea.getText());
                Document up = new Document("$set", newData);
                collection.updateOne(exist, up);
                JOptionPane.showMessageDialog(null, "Categoría actualizada.");
            }else{
                JOptionPane.showMessageDialog(null,"No se ha encotrado la categoría");
            }
            addUpBtn.setText("Agregar");
            delBtn.setDisable(true);
        }
        getCategorias();
        clearControls();
    }

    public void delete(){
        int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la categoría?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            MongoDBConnection.conexion();
            MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Categories");
            Document exist = new Document(parametros[1], nombField.getText()).append(parametros[2], descripArea.getText());
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
        getCategorias();
    }

    public void clearControls(){
        nombField.clear();
        descripArea.clear();
        categoriaTable.getSelectionModel().clearSelection();
        isSelected = false;
        cat.clear();
    }
    public void setPrincipalStage(HelloApplication principalStage) {
        this.principalStage = principalStage;
    }

    public void principalStage(){
        principalStage.menuView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombCol.setCellValueFactory(new PropertyValueFactory<>(parametros[1]));
        descCol.setCellValueFactory(new PropertyValueFactory<>(parametros[2]));
        categorias = FXCollections.observableArrayList();
        getCategorias();
        categoriaTable.setItems(categorias);
    }
}
