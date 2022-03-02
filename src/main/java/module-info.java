module com.onliner.parser_onliner {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.onliner.parser_onliner to javafx.fxml;
    exports com.onliner.parser_onliner;
}