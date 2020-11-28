package com.xbank.event.listener;

import com.xbank.event.OnCreateTransactionEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnCreateTransactionEventListener implements ApplicationListener<OnCreateTransactionEvent> {

    @Override
    public void onApplicationEvent(OnCreateTransactionEvent onCreateTransactionEvent) {

    }
}
