package io.sebi.calexport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Sebastian Aigner
 */
public class MainUIController {

    public TableView calendarTableView;

    public void doStuff() throws Exception {
        ObservableList<Calendar> calendars = FXCollections.observableArrayList(Main.analyzeUserCalendars());
        calendarTableView.setItems(calendars);
        TableColumn<Calendar, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));

        TableColumn subscriptionUrlColumn = new TableColumn<>("CalDAV Subscription URL");
        subscriptionUrlColumn.setCellValueFactory(new PropertyValueFactory("subscriptionUrl"));
        titleColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.23));
        subscriptionUrlColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.74));
        calendarTableView.getColumns().setAll(titleColumn, subscriptionUrlColumn);
    }

    public void thingDoing(ActionEvent actionEvent) throws Exception {
        doStuff();
    }

    @FXML
    protected void initialize() throws Exception {
        doStuff();
    }
}
