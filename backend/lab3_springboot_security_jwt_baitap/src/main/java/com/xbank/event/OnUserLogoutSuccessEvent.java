package com.xbank.event;

import com.xbank.model.dto.LogOutRequest;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;
import java.util.Date;

public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    private final String userEmail;
    private final String token;
    private final transient LogOutRequest logOutRequest;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String userEmail, String token, LogOutRequest logOutRequest) {
        super(userEmail);
        this.userEmail = userEmail;
        this.token = token;
        this.logOutRequest = logOutRequest;
        this.eventTime = Date.from(Instant.now());
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getToken() {
        return token;
    }

    public LogOutRequest getLogOutRequest() {
        return logOutRequest;
    }

    public Date getEventTime() {
        return eventTime;
    }
}
