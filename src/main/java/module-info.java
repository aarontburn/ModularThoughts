module com.aarontburn.modularthoughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.aarontburn.modularthoughts to javafx.fxml;
    exports com.aarontburn.modularthoughts;
}