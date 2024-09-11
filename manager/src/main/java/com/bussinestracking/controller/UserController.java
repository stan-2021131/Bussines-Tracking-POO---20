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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import javax.swing.text.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    private HelloApplication principalPage;
    @FXML
    private TableView userTable;
    @FXML
    private TableColumn<Usuario, String> userCol;
    @FXML
    private TableColumn<Usuario, String> rolCol;

    private ObservableList<Usuario> users;

    private void getUsers(){
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Users");
        FindIterable<Document> users1 = data.find();
        for (Document user : users1) {
            Usuario usuario = new Usuario(user.getString("username"), user.getString("password"), user.getString("rol"));
            users.add(usuario);
        }
        MongoDBConnection.close();
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
        userCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolCol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        users = FXCollections.observableArrayList();
        getUsers();
    }
}
