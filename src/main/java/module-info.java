module com.example.sinamekihunter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires com.google.gson;

    opens com.example.sinamekihunter to javafx.fxml;
    exports com.example.sinamekihunter;
    exports com.example.sinamekihunter.Controllers;
    opens com.example.sinamekihunter.Controllers to javafx.fxml;
}