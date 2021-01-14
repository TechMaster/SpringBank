package com.xbank.event;

import com.xbank.domain.Transaction;
import org.springframework.context.ApplicationEvent;

public class TransactionEvent extends ApplicationEvent {

    public static final String ITEM_CREATED = "CREATED";
    public static final String ITEM_UPDATED = "UPDATED";

    private String eventType;

    public TransactionEvent(String eventType, Transaction transactionItem) {
        super(transactionItem);
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
