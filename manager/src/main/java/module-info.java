module com.bussinestracking.manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.mongodb.driver.sync.client;

    opens com.bussinestracking.manager to javafx.fxml;
    exports com.bussinestracking.manager;
}