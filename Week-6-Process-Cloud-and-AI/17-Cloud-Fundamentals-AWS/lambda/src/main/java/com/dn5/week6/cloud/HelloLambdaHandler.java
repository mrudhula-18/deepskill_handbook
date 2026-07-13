package com.dn5.week6.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Collections;
import java.util.Map;

/**
 * A minimal AWS Lambda function, fronted by API Gateway, that greets a caller
 * by name.
 *
 * <p>Handles {@code GET /hello?name=Ada} and returns a 200 response with a
 * JSON body of the form {@code {"message": "Hello, Ada!"}}. When the
 * {@code name} query-string parameter is absent, the response greets
 * "World".
 */
public class HelloLambdaHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final String DEFAULT_NAME = "World";

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        String name = extractName(request);
        String body = buildJsonBody(name);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
        response.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
        response.setBody(body);
        return response;
    }

    private String extractName(APIGatewayProxyRequestEvent request) {
        if (request == null) {
            return DEFAULT_NAME;
        }

        Map<String, String> queryParams = request.getQueryStringParameters();
        if (queryParams == null) {
            return DEFAULT_NAME;
        }

        String name = queryParams.get("name");
        if (name == null || name.isBlank()) {
            return DEFAULT_NAME;
        }

        return name;
    }

    private String buildJsonBody(String name) {
        String escapedName = name.replace("\\", "\\\\").replace("\"", "\\\"");
        return "{\"message\": \"Hello, " + escapedName + "!\"}";
    }
}
