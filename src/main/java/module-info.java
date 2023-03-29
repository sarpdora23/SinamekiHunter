module com.example.sinamekihunter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.sinamekihunter to javafx.fxml;
    exports com.example.sinamekihunter;
    exports com.example.sinamekihunter.Controllers;
    opens com.example.sinamekihunter.Controllers to javafx.fxml;
}