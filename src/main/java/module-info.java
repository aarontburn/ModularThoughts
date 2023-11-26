module com.aarontburn.modularthoughts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens com.aarontburn.modularthoughts to javafx.fxml;
    exports com.aarontburn.modularthoughts;
    exports com.aarontburn.modularthoughts.module_builder;
    opens com.aarontburn.modularthoughts.module_builder to javafx.fxml;
    exports com.aarontburn.modularthoughts.module_builder.change_reporter;
    opens com.aarontburn.modularthoughts.module_builder.change_reporter to javafx.fxml;
    exports com.aarontburn.modularthoughts.module_builder.settings;
    opens com.aarontburn.modularthoughts.module_builder.settings to javafx.fxml;
    exports com.aarontburn.modularthoughts.handlers;
    opens com.aarontburn.modularthoughts.handlers to javafx.fxml;
    exports com.aarontburn.modularthoughts.built_ins.settings.types;
    opens com.aarontburn.modularthoughts.built_ins.settings.types to javafx.fxml;
}