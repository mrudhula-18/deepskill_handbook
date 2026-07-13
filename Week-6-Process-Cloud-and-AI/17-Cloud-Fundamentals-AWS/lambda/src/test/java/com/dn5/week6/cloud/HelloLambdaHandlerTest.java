package com.dn5.week6.cloud;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelloLambdaHandlerTest {

    private final HelloLambdaHandler handler = new HelloLambdaHandler();

    @Test
    void returnsGreetingForSuppliedName() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setQueryStringParameters(Collections.singletonMap("name", "Ada"));

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\": \"Hello, Ada!\"}", response.getBody());
        assertEquals("application/json", response.getHeaders().get("Content-Type"));
    }

    @Test
    void defaultsToWorldWhenNameQueryParamMissing() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setQueryStringParameters(null);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\": \"Hello, World!\"}", response.getBody());
    }

    @Test
    void defaultsToWorldWhenNameQueryParamIsBlank() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        Map<String, String> params = Collections.singletonMap("name", "   ");
        request.setQueryStringParameters(params);

        APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        assertEquals("{\"message\": \"Hello, World!\"}", response.getBody());
    }

    @Test
    void handlesNullRequestGracefully() {
        APIGatewayProxyResponseEvent response = handler.handleRequest(null, null);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().contains("Hello, World!"));
    }
}
