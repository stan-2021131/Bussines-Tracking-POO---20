package com.bussinestracking.controller;

import com.bussinestracking.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DetailProductsController {
    String[] parametros = {"_id", "nombre", "cantidad", "precioVenta", "precioOriginal"};
    @FXML
    private TableView<Producto> tblDetails;
    @FXML
    private TableColumn<Producto, String> nombCol;
    @FXML
    private TableColumn<Producto, Short> cantCol;
    @FXML
    private TableColumn<Producto, Float> preVCol;
    @FXML
    private TableColumn<Producto, Float> preOCol;

    private ObservableList<Producto> productos;

    public void initialize(){
        nombCol.setCellValueFactory(new PropertyValueFactory<>(parametros[1]));
        cantCol.setCellValueFactory(new PropertyValueFactory<>(parametros[2]));
        preVCol.setCellValueFactory(new PropertyValueFactory<>(parametros[3]));
        preOCol.setCellValueFactory(new PropertyValueFactory<>(parametros[4]));
    }

    public void setProductos(List<Producto> productos){
        this.productos = FXCollections.observableArrayList(productos);
        tblDetails.setItems(this.productos);
    }
}
