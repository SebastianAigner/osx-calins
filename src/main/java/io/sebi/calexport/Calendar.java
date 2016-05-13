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

    /**
     * Creates a new calendar.
     *
     * @param title           Title of the calendar
     * @param subscriptionUrl Subscription URL for the calendar
     * @param principalUrl    Principal Host for the calendar
     * @param calendarPath    Path (relative to the principal host) of the calendar
     */
    public Calendar(String title, String subscriptionUrl, String principalUrl, String calendarPath) {
        this.title = title;
        this.subscriptionUrl = subscriptionUrl;
        this.principalUrl = principalUrl;
        this.calendarPath = calendarPath;
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

    public void setPrincipalUrl(String principalUrl) {
        this.principalUrl = principalUrl;
    }

    public String getCalendarPath() {
        return this.calendarPath;
    }

    public void setCalendarPath(String calendarPath) {
        this.calendarPath = calendarPath;
    }

    /**
     * Depending on the information available, tries to determine the most usable subscription, and returns it as a
     * StringProperty. This is required for use with the TableView in the user interface.
     * If a subscription URL is present, the subscription URL is used.
     * If a calendar path is available and a (shortened) principal URL is available, a concatenation of the principal
     * URL domain and the calendar path is used.
     * If only a principal URL is available, it will be used.
     *
     * @return
     */
    public StringProperty usableSubscriptionProperty() {
        if (subscriptionUrl != null) {
            return new SimpleStringProperty(subscriptionUrl);
        } else if (calendarPath != null && principalUrl != null) {
            return new SimpleStringProperty(principalUrl + calendarPath);
        } else {
            return new SimpleStringProperty(principalUrl);
        }
    }

    public void setSubscriptionUrl(String subscriptionUrl) {
        this.subscriptionUrl = subscriptionUrl;
    }
}
