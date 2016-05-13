package io.sebi.calexport;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sebastian Aigner
 */
public class Calendar {
    private String title;
    private String subscriptionUrl;
    private String principalUrl;
    private String calendarPath;

    public Calendar(String title, String subscriptionUrl, String principalUrl, String calendarPath) {
        this.title = title;
        this.subscriptionUrl = subscriptionUrl;
        this.principalUrl = principalUrl;
        this.calendarPath = calendarPath;
    }

    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getPrincipalUrl() {
        return principalUrl;
    }

    public StringProperty principalUrlProperty() {
        return new SimpleStringProperty(principalUrl);
    }

    public void setPrincipalUrl(String principalUrl) {
        this.principalUrl = principalUrl;
    }

    public String getCalendarPath() {
        return this.calendarPath;
    }

    public StringProperty calendarPathProperty() {
        return new SimpleStringProperty(calendarPath);
    }

    public void setCalendarPath(String calendarPath) {
        this.calendarPath = calendarPath;
    }

    public String getSubscriptionUrl() {
        return this.subscriptionUrl;
    }

    public StringProperty usableSubscriptionProperty() {
        if (subscriptionUrl != null) {
            return new SimpleStringProperty(subscriptionUrl);
        } else if (calendarPath != null) {
            return new SimpleStringProperty(principalUrl + calendarPath);
        } else {
            return new SimpleStringProperty(principalUrl);
        }
    }

    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl = subscriptionUrl;
    }
}
