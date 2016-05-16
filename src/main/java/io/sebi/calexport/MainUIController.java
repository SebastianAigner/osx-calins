package io.sebi.calexport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sebastian Aigner
 */
public class MainUIController {

    public TableView<Calendar> calendarTableView;
    private final Clipboard clipboard = Clipboard.getSystemClipboard();
    private final ClipboardContent clipboardContent = new ClipboardContent();
    private ObservableList<Calendar> calendars = null;

    /**
     * Updates the table which is the main staple of the UI with the data coming from the calendar analyzer.
     */
    public void updateTable() {
        try {
            calendars = FXCollections.observableArrayList(Main.analyzeUserCalendars());
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not read calendar files successfully!");
        }

        calendarTableView.setRowFactory(tv -> {
            TableRow<Calendar> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Calendar c = row.getItem();
                    clipboardContent.putString(c.usableSubscriptionProperty().get());
                    clipboard.setContent(clipboardContent);
                    System.out.println(c);
                }
            });
            return row;
        });

        calendarTableView.setItems(calendars);
        TableColumn<Calendar, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Calendar, String> subscriptionUrlColumn = new TableColumn<>("CalDAV Subscription URL");
        subscriptionUrlColumn.setCellValueFactory(new PropertyValueFactory<>("usableSubscription"));
        titleColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.23));
        subscriptionUrlColumn.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(0.74));
        calendarTableView.getColumns().setAll(Arrays.asList(titleColumn, subscriptionUrlColumn));
    }

    /**
     * Updates the table upon launching the user interface.
     */
    @FXML
    protected void initialize() {
        updateTable();
    }

    /**
     * Method that gets executed when the "Export as HTML" button in the user interface is pressed.
     * Presents the user with a save dialog.
     *
     * @param actionEvent
     */
    public void exportHtml(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save HTML report");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                if (calendars != null) {
                    fileWriter.write(generateHtmlReport(calendars));
                }
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method that gets executed when the "Export as plain text" button in the user interface is pressed.
     * Presents the user with a save dialog.
     *
     * @param actionEvent
     */
    public void exportPlainText(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save pure text report");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Plain Text", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                if (calendars != null) {
                    fileWriter.write(generatePlainTextReport(calendars));
                }
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Generates an HTML formatted report from the list of calendars passed in.
     *
     * @param calendars list of calendars on which to report
     * @return HTML formatted report as String
     */
    private String generateHtmlReport(List<Calendar> calendars) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "  <head>\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <title>title</title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "<table>" +
                        "<tr style='font-weight: bold;'>" +
                        "<td>Title</td>" +
                        "<td>CalDAV Subscription URL</td>" +
                        "</tr>");
        for (Calendar c : calendars) {
            if (c.usableSubscriptionProperty().get() != null) {
                stringBuilder.append("<tr>");
                stringBuilder.append("<td>");
                stringBuilder.append(c.getTitle());
                stringBuilder.append("</td>");
                stringBuilder.append("<td>");
                stringBuilder.append(c.usableSubscriptionProperty().get());
                stringBuilder.append("</td>");
                stringBuilder.append("</tr>");
            }
        }
        stringBuilder.append("</table>\n");
        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    /**
     * Generates a plain text report from the list of calenders passed in.
     *
     * @param calendars list of calendars on which to report
     * @return plain text report
     */
    private String generatePlainTextReport(List<Calendar> calendars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Calendar c : calendars) {
            if (c.usableSubscriptionProperty().get() != null) {
                stringBuilder.append(c.getTitle());
                stringBuilder.append(": ");
                stringBuilder.append(c.usableSubscriptionProperty().get());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
