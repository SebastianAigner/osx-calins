package io.sebi.calexport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/**
 * Created by Sebastian Aigner
 */
public class MainUIController {

    public TableView<Calendar> calendarTableView;

    /**
     * Updates the table which is the main staple of the UI with the data coming from the calendar analyzer.
     */
    public void updateTable() {
        ObservableList<Calendar> calendars = null;
        try {
            calendars = FXCollections.observableArrayList(Main.analyzeUserCalendars());
        } catch (IOException ex) {
            System.err.println("Could not read calendar files successfully!");
            ex.printStackTrace();
            System.exit(-1);
        }
        calendarTableView.setItems(calendars);
        TableColumn<Calendar, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn subscriptionUrlColumn = new TableColumn<>("CalDAV Subscription URL");
        subscriptionUrlColumn.setCellValueFactory(new PropertyValueFactory("usableSubscription"));
        titleColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.23));
        subscriptionUrlColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.74));
        calendarTableView.getColumns().setAll(titleColumn, subscriptionUrlColumn);
    }

    /**
     * Updates the table upon launching the user interface.
     */
    @FXML
    protected void initialize() {
        updateTable();
    }
}
