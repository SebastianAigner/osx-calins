package io.sebi.calexport;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sebastian Aigner
 */
public class Calendar {
    StringProperty title = new SimpleStringProperty();
    StringProperty principalUrl = new SimpleStringProperty();
    StringProperty calendarPath = new SimpleStringProperty();
    StringProperty subscriptionUrl = new SimpleStringProperty();

    public Calendar(StringProperty title, StringProperty principalUrl, StringProperty calendarPath, StringProperty subscriptionUrl) {
        this.title = title;
        this.principalUrl = principalUrl;
        this.calendarPath = calendarPath;
        this.subscriptionUrl = subscriptionUrl;
    }

    public Calendar(String title, String principalUrl, String calendarPath, String subscriptionUrl) {
        this.title = new SimpleStringProperty(title);
        this.principalUrl = new SimpleStringProperty(principalUrl);
        this.calendarPath = new SimpleStringProperty(calendarPath);
        this.subscriptionUrl = new SimpleStringProperty(subscriptionUrl);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPrincipalUrl() {
        return principalUrl.get();
    }

    public StringProperty principalUrlProperty() {
        return principalUrl;
    }

    public void setPrincipalUrl(String principalUrl) {
        this.principalUrl.set(principalUrl);
    }

    public String getCalendarPath() {
        return calendarPath.get();
    }

    public StringProperty calendarPathProperty() {
        return calendarPath;
    }

    public void setCalendarPath(String calendarPath) {
        this.calendarPath.set(calendarPath);
    }

    public String getSubscriptionUrl() {
        return subscriptionUrl.get();
    }

    public StringProperty subscriptionUrlProperty() {
        if (subscriptionUrl.get() != null) {
            return subscriptionUrl;
        } else if (calendarPath.get() != null) {
            return new SimpleStringProperty(principalUrl.get() + calendarPath.get());
        } else {
            return principalUrl;
        }
    }

    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl.set(subscriptionUrl);
    }
}
