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

import javax.print.Doc;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {
    private HelloApplication principalStage;
    String[] parametros = {"_id", "nombre", "descripcion", "cantidad", "precioVenta", "precioOriginal", "categoria"};
    String[] parametros2 = {"_id", "nombre", "descripcion"};
    public HelloApplication getPrincipalStage() {
        return principalStage;
    }
    boolean isSelected = false;


    @FXML
    private TableView<Producto> tvProducto;
    @FXML
    private TableColumn<Producto, String> nombCol;
    @FXML
    private TableColumn<Producto, String> descCol;
    @FXML
    private TableColumn<Producto, Short> cantCol;
    @FXML
    private TableColumn<Producto, Float> preVenCol;
    @FXML
    private TableColumn<Producto, Float> preComCol;
    @FXML
    private TableColumn<Producto, String> catCol;
    @FXML
    private TextField nombField;
    @FXML
    private TextArea descripArea;
    @FXML
    private Button addUpBtn;
    @FXML
    private Button delBtn;
    @FXML
    private TextField textPreV;
    @FXML
    private TextField textPreC;
    @FXML
    private TextField textCant;
    @FXML
    private ComboBox<Categoria> cmbCategoria;
    private ObservableList<Producto> productos;
    private ObservableList<Categoria> categorias;
    Document pro = new Document();

    private void getProductos() {
        productos.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Products");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            String id = document.getObjectId(parametros[0]).toString();
            String nombre = document.getString(parametros[1]);
            String descripcion = document.getString(parametros[2]);
            Integer cant = document.getInteger(parametros[3]);
            Short cantidad = (cant != null) ? document.getInteger(parametros[3]).shortValue(): 0;
            Double preV = document.getDouble(parametros[4]);
            float precioVenta = (preV != null) ? document.getDouble(parametros[4]).floatValue() : 0;
            Double preC = document.getDouble(parametros[5]);
            float precioCompra = (preC != null) ? document.getDouble(parametros[5]).floatValue() : 0;
            Categoria newCategoria = null;
            Document categoria = document.get(parametros[6], Document.class);
            if(categoria != null) {
                newCategoria = new Categoria(
                        categoria.getString(parametros2[0]),
                        categoria.getString(parametros2[1]),
                        categoria.getString(parametros[2])
                );
            }
            Producto producto = new Producto(id, nombre, descripcion , cantidad, precioVenta, precioCompra , newCategoria);
            productos.add(producto);
        }
        MongoDBConnection.close();
    }

    private void getCategorias() {
        categorias.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Categories");
        FindIterable<Document> iterable = data.find();
        for (Document document : iterable) {
            Categoria categoria = new Categoria(document.getObjectId(parametros2[0]).toString(), document.getString(parametros2[1]), document.getString(parametros2[2]));
            categorias.add(categoria);
        }
        MongoDBConnection.close();
    }

    public void selectElement(){
        Producto producto = tvProducto.getSelectionModel().getSelectedItem();
        if(producto == null){
            JOptionPane.showMessageDialog(null,"No se ha seleccionado un elemento de la tabla");
        }else{
            isSelected = true;
            String id = String.valueOf(producto.getIdProducto());
            nombField.setText(String.valueOf(producto.getNombre()));
            descripArea.setText(String.valueOf(producto.getDescripcion()));
            textPreV.setText(String.valueOf(producto.getPrecioVenta()));
            textPreC.setText(String.valueOf(producto.getPrecioOriginal()));
            textCant.setText(String.valueOf(producto.getCantidad()));
            cmbCategoria.setValue(producto.getCategoria());
            addUpBtn.setText("Actualizar");
            addUpBtn.setDisable(false);
            delBtn.setDisable(false);
            pro.append(parametros[0], new ObjectId(id));
        }
    }

    public void addUp(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Products");
        if(addUpBtn.getText().equals("Agregar")){
            Document exist = new Document(parametros[1], nombField.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                JOptionPane.showMessageDialog(null,"Ya existe un producto con este nombre.");
            }else{
                Categoria cat = cmbCategoria.getValue();
                Document categoria = new Document(parametros2[0], cat.getIdCategoria())
                        .append(parametros2[1], cat.getNombre())
                        .append(parametros2[2], cat.getDescripcion());
                Document newPro = new Document(parametros[1], nombField.getText())
                        .append(parametros[2], descripArea.getText())
                        .append(parametros[3], Short.parseShort(textCant.getText()))
                        .append(parametros[4], Double.parseDouble(textPreV.getText()))
                        .append(parametros[5], Double.parseDouble(textPreC.getText()))
                        .append(parametros[6], categoria);
                collection.insertOne(newPro);
                JOptionPane.showMessageDialog(null, "Nuevo producto agregado.");
            }
        } else if (addUpBtn.getText().equals("Actualizar")) {
            Document exist = collection.find(pro).first();
            if(exist != null){
                Categoria cat = cmbCategoria.getValue();
                Document categoria = new Document(parametros2[0], cat.getIdCategoria())
                        .append(parametros2[1], cat.getNombre())
                        .append(parametros2[2], cat.getDescripcion());
                Document newPro = new Document(parametros[1], nombField.getText())
                        .append(parametros[2], descripArea.getText())
                        .append(parametros[3], Short.parseShort(textCant.getText()))
                        .append(parametros[4], Double.parseDouble(textPreV.getText()))
                        .append(parametros[5], Double.parseDouble(textPreC.getText()))
                        .append(parametros[6], categoria);
                Document up = new Document("$set", newPro);
                collection.updateOne(exist, up);
                JOptionPane.showMessageDialog(null, "Producto actualizado.");
            }else{
                JOptionPane.showMessageDialog(null,"No se ha encotrado el producto");
            }
            addUpBtn.setText("Agregar");
            delBtn.setDisable(true);
        }
        getProductos();
        clearControls();
    }

    public void delete(){
        int op = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if(op == JOptionPane.YES_OPTION){
            MongoDBConnection.conexion();
            MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Products");
            Document exist = new Document(parametros[1], nombField.getText()).append(parametros[2], descripArea.getText());
            Document alreadyExist = collection.find(exist).first();
            if(alreadyExist != null){
                collection.deleteOne(alreadyExist);
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }else{
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
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
        textPreV.clear();
        textPreC.clear();
        textCant.clear();
        cmbCategoria.setValue(null);
        tvProducto.getSelectionModel().clearSelection();
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombCol.setCellValueFactory(new PropertyValueFactory<>(parametros[1]));
        descCol.setCellValueFactory(new PropertyValueFactory<>(parametros[2]));
        cantCol.setCellValueFactory(new PropertyValueFactory<>(parametros[3]));;
        preVenCol.setCellValueFactory(new PropertyValueFactory<>(parametros[4]));;
        preComCol.setCellValueFactory(new PropertyValueFactory<>(parametros[5]));;
        catCol.setCellValueFactory(new PropertyValueFactory<>(parametros[6]));;
        categorias = FXCollections.observableArrayList();
        getCategorias();
        cmbCategoria.setItems(categorias);
        productos = FXCollections.observableArrayList();
        getProductos();
        tvProducto.setItems(productos);
    }
}