package io.sebi.calexport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.plist.XMLPropertyListConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sebastian Aigner
 */
public class Main extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static List<Calendar> analyzeUserCalendars() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "Library", "Calendars");
        Pattern p = Pattern.compile("([^:]+:\\/\\/[^\\/]+)");
        List<Calendar> userCalendars = new ArrayList<>();
        Files.walk(path).filter(Files::isRegularFile).forEach(path1 -> {
            if (path1.toString().endsWith("Info.plist")) {
                XMLPropertyListConfiguration plist = new XMLPropertyListConfiguration();
                plist.setFileName(path1.toString());
                try {
                    plist.load();
                    String title = plist.getString("Title");
                    String principalUrl = plist.getString("PrincipalURL");
                    String subscriptionUrl = plist.getString("SubscriptionURL");
                    String calendarPath = plist.getString("CalendarPath");
                    Calendar c = new Calendar(title, principalUrl, subscriptionUrl, calendarPath);
                    userCalendars.add(c);
                    if (principalUrl != null) {
                        c.setTitle(c.getTitle() + " (Principal)");
                        for (Calendar userC : userCalendars) {
                            if (userC.getPrincipalUrl() == null) {
                                String currentPrincipalUrl = plist.getString("PrincipalURL");
                                Matcher m = p.matcher(currentPrincipalUrl);
                                while (m.find()) {
                                    currentPrincipalUrl = m.group();
                                }
                                userC.setPrincipalUrl(currentPrincipalUrl);
                            }
                        }
                    }
                } catch (ConfigurationException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return userCalendars;
    }

    @Override
    public void start(Stage primaryStage) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Parent root = FXMLLoader.load(classLoader.getResource("io.sebi.calexport/UserInterface.fxml"));
            primaryStage.setTitle("Calendar Explorer");
            primaryStage.setScene(new Scene(root, 1200, 600));
            primaryStage.show();
        } catch (IOException | NullPointerException ex) {
            System.err.println("Graphical User Interface could not be initialized.");
            ex.printStackTrace();
        }
    }
}