module com.thisastergroup {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.thisastergroup to javafx.fxml;
    exports com.thisastergroup;
}
