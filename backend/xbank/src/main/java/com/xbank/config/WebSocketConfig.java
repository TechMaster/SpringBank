package com.xbank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbank.domain.Transaction;
import com.xbank.event.TransactionEvent;
import com.xbank.event.TransactionEventPublisher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketConfig {

    private static final Map<String, WebSocketSession> userMap = new ConcurrentHashMap<>();
    public static final String WS_EVENTS = "/ws/events";
    @Bean
    HandlerMapping handlerMapping(WebSocketHandler webSocketHandler) {
        return new SimpleUrlHandlerMapping() {{
            setUrlMap(Collections.singletonMap(WS_EVENTS, webSocketHandler));
            setOrder(10);
        }};
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler(TransactionEventPublisher transactionEventPublisher, ObjectMapper objectMapper) {
        Flux<TransactionEvent> publish = Flux.create(transactionEventPublisher).share();
        // Push events that are captured when catalogue item is added or updated
        return session -> {
            String query = session.getHandshakeInfo().getUri().getQuery();
            Map<String, String> queryMap = getQueryMap(query);
            String userId = queryMap.getOrDefault("id", "");
            userMap.put(userId, session);

            return publish.map(evt -> {
                Transaction item = (Transaction) evt.getSource();
                return item;
            }).flatMap(tx -> {
                String transactionAt = tx.getTransactAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                DecimalFormat formatter = new DecimalFormat("###,###,###.##");

                WebSocketMessage textMessage = null;
                if (tx.getAction() == 1) {
                    // Tranfer action
                    textMessage = session.textMessage("Số dư tài khoản " + tx.getAccount() + " - " + formatter.format(tx.getAmount()) + "VNĐ. Chuyển tiền sang tài khoàn " + tx.getToAccount() + " ngày " + transactionAt);
                } else if (tx.getAction() == 2) {
                    // withdraw action
                    textMessage = session.textMessage("Số dư tài khoản " + tx.getAccount() + " - " + formatter.format(tx.getAmount()) + "VNĐ. Rút tiền ngày " + transactionAt);
                } else if (tx.getAction() == 3) {
                    // deposit action
                    textMessage = session.textMessage("Số dư tài khoản " + tx.getAccount() + " + " + formatter.format(tx.getAmount()) + "VNĐ. Nạp tiền ngày " + transactionAt);
                }

                String targetId = tx.getAccount();
                if (userMap.containsKey(targetId)) {
                    WebSocketSession targetSession = userMap.get(targetId);
                    if (null != targetSession) {
                        return targetSession.send(Mono.just(textMessage)).doFinally(signal ->  userMap.remove(userId));
                    }
                }
                return session.send(Mono.just(textMessage));
            }).then();
        };
    }

    private Map<String, String> getQueryMap(String queryStr) {
        Map<String, String> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(queryStr)) {
            String[] queryParam = queryStr.split("&");
            Arrays.stream(queryParam).forEach(s -> {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            });
        }
        return queryMap;
    }
}
