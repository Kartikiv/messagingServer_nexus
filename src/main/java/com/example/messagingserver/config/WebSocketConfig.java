package com.example.messagingserver.config;

import org.apache.tomcat.websocket.server.WsServerContainer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue", "/user"); // add /user
        config.setUserDestinationPrefix("/user");               //  tells Spring how to resolve user destinations
        config.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(1024 * 10240 * 10); // 10 MB
        registration.setSendBufferSizeLimit(1024 * 10240 * 10);
        registration.setSendTimeLimit(200000); // 20 seconds
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(customHandshakeHandler()) // attach the custom handler
                .setAllowedOriginPatterns("*");

    }
    @Bean
    public DefaultHandshakeHandler customHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                              Map<String, Object> attributes) {
                if (request instanceof ServletServerHttpRequest servletRequest) {
                    String name = servletRequest.getServletRequest().getParameter("name");
                    if (name != null && !name.isBlank()) {
                        System.out.println(" Registered WebSocket session for user: " + name);
                        return () -> name;
                    }
                }
                return () -> UUID.randomUUID().toString(); // fallback anonymous
            }
        };
    }
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addContextCustomizers(context -> {
            context.addServletContainerInitializer((c, ctx) -> {
                WsServerContainer container = (WsServerContainer) ctx.getAttribute("javax.websocket.server.ServerContainer");
                if (container != null) {
                    container.setDefaultMaxBinaryMessageBufferSize(20 * 1024 * 1024); // 20MB
                    container.setDefaultMaxTextMessageBufferSize(20 * 1024 * 1024);   // 20MB
                }
            }, null);
        });
    }


}
