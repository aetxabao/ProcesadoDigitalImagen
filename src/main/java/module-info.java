module edu.masanz.da.prog.pdi.procesadodigitalimagen {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    opens edu.masanz.da.prog.pdi to javafx.fxml;
    exports edu.masanz.da.prog.pdi;
}