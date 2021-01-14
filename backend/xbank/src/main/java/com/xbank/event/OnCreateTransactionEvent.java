package com.xbank.event;

import com.xbank.domain.Transaction;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnCreateTransactionEvent extends ApplicationEvent {

    private transient UriComponentsBuilder redirectUrl;

    private Transaction transaction;

    public OnCreateTransactionEvent(Transaction transaction, UriComponentsBuilder redirectUrl) {
        super(transaction);
        this.transaction = transaction;
        this.redirectUrl = redirectUrl;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public UriComponentsBuilder getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
