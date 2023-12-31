module com.aarontburn.modularthoughts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jsr305;
    requires org.apache.commons.collections4;
    requires java.desktop;
    requires jfxflexbox;


    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.logging;

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
    exports com.aarontburn.modularthoughts.tools;
    opens com.aarontburn.modularthoughts.tools to javafx.fxml;
}