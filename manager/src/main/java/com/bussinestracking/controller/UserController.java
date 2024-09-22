package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.MongoDBConnection;
import com.bussinestracking.model.Usuario;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    boolean isSelected = false;
    private HelloApplication principalPage;
    @FXML
    private TableView<Usuario> userTable;
    @FXML
    private TableColumn<Usuario, String> userCol = new TableColumn<>("username");
    @FXML
    private TableColumn<Usuario, String> rolCol = new TableColumn<>("rol");
    @FXML
    private Button addUpBtn;
    @FXML
    private Button delBtn;
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    @FXML
    private TextField rolField;
    private ObservableList<Usuario> users;
    Document us = new Document();

    private void getUsers(){
        users.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Users");
        FindIterable<Document> users1 = data.find();
        for (Document user : users1) {
            Usuario usuario = new Usuario(user.getString("username"), user.getString("password"), user.getString("rol"));
            users.add(usuario);
        }
        MongoDBConnection.close();
    }

    public void selectElement(){
        if(userTable.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun elemento de la tabla");
        }else{
            userField.setText(String.valueOf(((Usuario)userTable.getSelectionModel().getSelectedItem()).getUsername()));
            passField.setText(String.valueOf(((Usuario)userTable.getSelectionModel().getSelectedItem()).getPassword()));
            rolField.setText(String.valueOf(((Usuario)userTable.getSelectionModel().getSelectedItem()).getRol()));
            addUpBtn.setText("Actualizar");
            addUpBtn.setDisable(false);
            passField.setDisable(true);
            delBtn.setDisable(false);
            us.append("username", userField.getText()).append("password", passField.getText()).append("rol", rolField.getText());
        }
    }

    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Users");
        if(addUpBtn.getText().equals("Agregar")){
            Document exist = new Document("username", userField.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese username, intenta con otro");
            }else {
                Document document = new Document("username", userField.getText()).append("password", passField.getText()).append("rol", rolField.getText());
                collection.insertOne(document);
                JOptionPane.showMessageDialog(null, "Nuevo trabajador agregado");
            }
            getUsers();
            clearControls();
        } else if (addUpBtn.getText().equals("Actualizar")) {
            Document exist = collection.find(us).first();
            if(exist!=null){
                Document newData = new Document("username", userField.getText()).append("password", passField.getText()).append("rol", rolField.getText()).append("rol", rolField.getText());
                Document up = new Document("$set", newData);
                collection.updateOne(exist, up);
                JOptionPane.showMessageDialog(null, "Trabajador actualizado");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha encontrado el registro");
            }
            clearControls();
            addUpBtn.setText("Agregar");
            passField.setDisable(false);
            delBtn.setDisable(true);
            getUsers();
        }
    }

    public void delete(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Users");
        Document exist = new Document("username", userField.getText()).append("password", passField.getText()).append("rol", rolField.getText()).append("rol", rolField.getText());
        Document alreadyExist = collection.find(exist).first();
        if(alreadyExist != null){
            collection.deleteOne(alreadyExist);
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
        }else{
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        }
        clearControls();
        addUpBtn.setText("Agregar");
        passField.setDisable(false);
        delBtn.setDisable(true);
        getUsers();
    }

    public void clearControls(){
        userField.clear();
        passField.clear();
        rolField.clear();
        userTable.getSelectionModel().clearSelection();
        isSelected=false;
        us.clear();
    }

    public HelloApplication getPrincipalPage() {
        return principalPage;
    }

    public void setPrincipalPage(HelloApplication principalPage) {
        this.principalPage = principalPage;
    }
    public void menuView(){
        principalPage.menuView();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        rolCol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        users = FXCollections.observableArrayList();
        userTable.setItems(users);
        getUsers();
    }
}
