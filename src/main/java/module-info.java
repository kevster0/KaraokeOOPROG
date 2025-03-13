module com.ooprog.javafxmedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.ooprog.javafxmedia to javafx.fxml;
    exports com.ooprog.javafxmedia;
}