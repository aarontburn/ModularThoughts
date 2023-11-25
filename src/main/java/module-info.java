module com.aarontburn.modularthoughts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.aarontburn.modularthoughts to javafx.fxml;
    exports com.aarontburn.modularthoughts;
    exports com.aarontburn.modularthoughts.module;
    opens com.aarontburn.modularthoughts.module to javafx.fxml;
    exports com.aarontburn.modularthoughts.module.change_reporter;
    opens com.aarontburn.modularthoughts.module.change_reporter to javafx.fxml;
    exports com.aarontburn.modularthoughts.module.settings;
    opens com.aarontburn.modularthoughts.module.settings to javafx.fxml;
}