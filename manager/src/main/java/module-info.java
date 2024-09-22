module com.bussinestracking.manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.desktop;

    opens com.bussinestracking.manager to javafx.fxml;
    exports com.bussinestracking.manager;
    exports com.bussinestracking.controller to javafx.fxml;
    opens com.bussinestracking.controller to javafx.fxml;
    opens com.bussinestracking.model to javafx.base;
}