package com.bussinestracking.manager;

import com.bussinestracking.controller.*;
import com.bussinestracking.model.MongoDBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class HelloApplication extends Application {
    private Stage principalStage;

    @Override
    public void start(Stage principalStage) throws Exception {
        this.principalStage = principalStage;
       // ventanaPrincipal();
        menuView();
        principalStage.show();
    }

    public void ventanaPrincipal(){
        try{
            HelloController ventanaPrincipal = (HelloController)cambiarEscena("hello-view.fxml", 600,400);
            ventanaPrincipal.setPrincipalStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loginView() {
        try {
            LoginController ventanaLogin =(LoginController)cambiarEscena("Login.fxml", 600,400);
            ventanaLogin.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuView(){
        try {
            MenuController ventanaMenu = (MenuController)cambiarEscena("Menu Page.fxml", 895,577);
            ventanaMenu.setPrincipalPage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userView(){
        try {
            UserController ventanaUsers = (UserController)cambiarEscena("Users.fxml", 597,366);
            ventanaUsers.setPrincipalPage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inventarioView(){
        try{
            InventarioController ventanaInventario = (InventarioController)cambiarEscena("Producto.fxml", 1217, 547);
            ventanaInventario.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumenContableView(){
        try{
            ResumenContableController ventanaResumen = (ResumenContableController)cambiarEscena("ResumenContable.fxml", 490, 354);
            ventanaResumen.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporteDiarioView(){
        try{
            ReporteDiarioController ventanaReporteDiario = (ReporteDiarioController)cambiarEscena("ReporteDiario.fxml", 600, 400);
            ventanaReporteDiario.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ventasView(){
        try{
            VentasController ventanaVentas = (VentasController)cambiarEscena("Ventas.fxml", 1047, 472);
            ventanaVentas.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void comprasView(){
        try{
            ComprasController ventanaCompras = (ComprasController)cambiarEscena("COMPRAS.fxml", 730, 400);
            ventanaCompras.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void categoriasView(){
        try {
            CategoriasController ventanaCategorias = (CategoriasController) cambiarEscena("Categorias.fxml",597,366);
            ventanaCategorias.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void proveedorView(){
        try{
            ProveedorController ventanaProveedor = (ProveedorController) cambiarEscena("Proveedor.fxml", 600, 400);
            ventanaProveedor.setPrincipalStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        final String PAQUETE_VISTA = "/com/bussinestracking/manager/view/";
        InputStream archivo = HelloApplication.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(HelloController.class.getResource(PAQUETE_VISTA+fxml));
        Scene stage = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        principalStage.setScene(stage);
        principalStage.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;
    }
}