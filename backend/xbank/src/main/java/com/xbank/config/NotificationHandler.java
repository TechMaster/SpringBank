//package com.xbank.config;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class NotificationHandler implements WebSocketHandler {
//    private static final Map<String, WebSocketSession> userMap = new ConcurrentHashMap<>();
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        String query = session.getHandshakeInfo().getUri().getQuery();
//        Map<String, String> queryMap = getQueryMap(query);
//        String userId = queryMap.getOrDefault("id", "");
//        userMap.put(userId, session);
//        return session.receive().flatMap(webSocketMessage -> {
//            String payload = webSocketMessage.getPayloadAsText();
//            Message message;
//            try {
//                message = objectMapper.readValue(payload, Message.class);
//                String targetId = message.getTargetId();
//                if (userMap.containsKey(targetId)) {
//                    WebSocketSession targetSession = userMap.get(targetId);
//                    if (null != targetSession) {
//                        WebSocketMessage textMessage = targetSession.textMessage(message.getMessageText());
//                        return targetSession.send(Mono.just(textMessage));
//                    }
//                }
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//                return session.send(Mono.just(session.textMessage(e.getMessage())));
//            }
//            return session.send(Mono.just(session.textMessage("target user is not online")));
//        }).then().doFinally(signal -> System.out.println(userId));
//    }
//
//    private Map<String, String> getQueryMap(String queryStr) {
//        Map<String, String> queryMap = new HashMap<>();
//        if (!StringUtils.isEmpty(queryStr)) {
//            String[] queryParam = queryStr.split("&");
//            Arrays.stream(queryParam).forEach(s -> {
//                String[] kv = s.split("=", 2);
//                String value = kv.length == 2 ? kv[1] : "";
//                queryMap.put(kv[0], value);
//            });
//        }
//        return queryMap;
//    }
//}
