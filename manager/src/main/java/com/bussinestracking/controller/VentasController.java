package com.bussinestracking.controller;

import com.bussinestracking.manager.HelloApplication;
import com.bussinestracking.model.Carrito;
import com.bussinestracking.model.MongoDBConnection;
import com.bussinestracking.model.Producto;
import com.bussinestracking.model.Venta;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class VentasController implements Initializable {
    String[] parametros = {"_id", "cliente", "nit", "idProductos", "total", "formaPago", "fecha"};
    String[] parametros2 = {"_id", "nombre", "cantidad", "precioVenta", "precioOriginal"};
    @FXML
    private TableView<Venta> tblVentas;
    @FXML
    private TableColumn<Venta, String> nombCol;
    @FXML
    private TableColumn<Venta, String> nitCol;
    @FXML
    private TableColumn<Venta, Float> totalCol;
    @FXML
    private TableColumn<Venta, String> formaPCol;
    @FXML
    private TableColumn<Venta, Date> fechaCol;
    @FXML
    private TableColumn<Venta ,Void> prodCol;
    @FXML
    private TextField nombField;
    @FXML
    private TextField nitField;
    @FXML
    private TextField formaPagoField;
    private HelloApplication principalStage;

    private ObservableList<Venta> ventas;
    Document vent = new Document();

    private void getVentas(){
        ventas.clear();
        MongoDBConnection.conexion();
        MongoCollection<Document> data = MongoDBConnection.getDatabase().getCollection("Sales");
        FindIterable<Document> iterable = data.find();
        for(Document doc: iterable){
            String id = doc.getObjectId(parametros[0]).toString();
            String cliente = doc.getString(parametros[1]);
            String nit = doc.getString(parametros[2]);
            float total = doc.getDouble(parametros[4]).floatValue();
            String formaPago = doc.getString(parametros[5]);
            Date fecha = doc.getDate(parametros[6]);
            List<Producto> productoList = new ArrayList<>();
            List<Document> producto =(List<Document>) doc.get(parametros[3]);
            if(producto != null){
                for(Document doc2: producto){
                    Producto pro = new Producto();
                    pro.setIdProducto(doc2.getString(parametros2[0]));
                    pro.setNombre(doc2.getString(parametros2[1]));
                    pro.setCantidad(doc2.getInteger(parametros2[2]).shortValue());
                    pro.setPrecioVenta(doc2.getDouble(parametros2[3]).floatValue());
                    pro.setPrecioOriginal(doc2.getDouble(parametros2[4]).floatValue());
                    productoList.add(pro);
                }
            }
            Venta newVenta = new Venta(id, cliente, nit, productoList, total, formaPago,fecha);
            ventas.add(newVenta);
        }
        MongoDBConnection.close();
    }

    public void add(){
        Date dateNow = new Date();
        float total = 0;
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Sales");
        List<Document> productosDoc = new ArrayList<>();
        if(Carrito.getCarrito().isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay productos en el carrito. Agregue algunos en el apartado de productos");
        }else{
            for(Producto pro: Carrito.getCarrito()){
                Document producto = new Document().append(parametros2[0], pro.getIdProducto())
                        .append(parametros2[1], pro.getNombre())
                        .append(parametros2[2], pro.getCantidad())
                        .append(parametros2[3], pro.getPrecioVenta())
                        .append(parametros2[4], pro.getPrecioOriginal());
                productosDoc.add(producto);
                total += (pro.getPrecioVenta() * pro.getCantidad());
            }
            Document newVenta = new Document()
                    .append(parametros[1], nombField.getText())
                    .append(parametros[2], nitField.getText())
                    .append(parametros[3], productosDoc)
                    .append(parametros[4], (float)total)
                    .append(parametros[5], formaPagoField.getText())
                    .append(parametros[6], dateNow);
            collection.insertOne(newVenta);
            JOptionPane.showMessageDialog(null, "Nueva venta hecha");
            updateCant();
            upVentas(total);
            getVentas();
        }
        clearControls();
    }

    public void upVentas(float newVentas){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Summary");
        Document resumen = collection.find().first();
        if(resumen != null){
            float ventasActual = resumen.getDouble("totalVentas").floatValue();
            float upVentas = ventasActual + newVentas;
            Document update = new Document("$set", new Document("totalVentas", upVentas));
            collection.updateOne(new Document("_id", resumen.getObjectId("_id")), update);
        }
        MongoDBConnection.close();
    }

    public void updateCant(){
        MongoDBConnection.conexion();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("Products");
        for(Producto pro: Carrito.getCarrito()){
            short cantiVend = pro.getCantidad();
            Document producto = new Document().append(parametros2[0], new ObjectId(pro.getIdProducto()));
            Document prodDB = collection.find(producto).first();
            if(prodDB != null){
                short currentCant = prodDB.getInteger(parametros2[2]).shortValue();
                short newCant = (short)Math.max(0,currentCant - cantiVend);
                Document upProd = new Document("$set", new Document().append(parametros2[2], newCant ));
                collection.updateOne(producto, upProd);
            }
        }
        Carrito.getCarrito().clear();
        MongoDBConnection.close();
    }

    public void btnActions(){
        prodCol.setCellFactory(col -> new TableCell<Venta, Void>(){
            private final Button btn = new Button("Lista de productos");
            {
                btn.setOnAction(event -> {
                    Venta v = tblVentas.getItems().get(getIndex());
                    if(v != null){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bussinestracking/manager/view/DetailProducts.fxml"));
                            Parent root = loader.load();
                            DetailProductsController controller = loader.getController();
                            controller.setProductos(v.getIdProductos());
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Productos adquiridos");
                            stage.setScene(new Scene(root));
                            stage.show();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    public void clearControls(){
        nombField.clear();
        nitField.clear();
        formaPagoField.clear();
    }

    public void formatDate(){
        fechaCol.setCellFactory(column -> new TableCell<Venta, Date>(){
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }else{
                    setText(dateFormat.format(item));
                }
            }
        });
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
        nombCol.setCellValueFactory(new PropertyValueFactory<>(parametros[1]));
        nitCol.setCellValueFactory(new PropertyValueFactory<>(parametros[2]));
        totalCol.setCellValueFactory(new PropertyValueFactory<>(parametros[4]));
        formaPCol.setCellValueFactory(new PropertyValueFactory<>(parametros[5]));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>(parametros[6]));
        formatDate();
        ventas = FXCollections.observableArrayList();
        getVentas();
        tblVentas.setItems(ventas);
        btnActions();
    }
}