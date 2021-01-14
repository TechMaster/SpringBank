package com.xbank.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class TransactionEventPublisher implements ApplicationListener<TransactionEvent>, Consumer<FluxSink<TransactionEvent>> {

    private final Executor executor;
    private final BlockingQueue<TransactionEvent> queue;

    TransactionEventPublisher() {
        this.executor = Executors.newSingleThreadExecutor();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void onApplicationEvent(TransactionEvent event) {
        this.queue.offer(event);
    }

    @Override
    public void accept(FluxSink<TransactionEvent> sink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    TransactionEvent event = queue.take();
                    sink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        });
    }
}