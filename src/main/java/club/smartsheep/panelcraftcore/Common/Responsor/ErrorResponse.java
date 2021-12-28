package club.smartsheep.panelcraftcore.Common.Responsor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    public static void CustomErrorResponse(HttpExchange exchange, String errorName, String errorMessage) {
        try {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));

            Map<String, Object> response = new HashMap<>();
            response.put("error", errorName);
            response.put("message", errorMessage);

            OutputStream responseBody = exchange.getResponseBody();
            exchange.sendResponseHeaders(400, new ObjectMapper().writeValueAsString(response).length());
            responseBody.write(new ObjectMapper().writeValueAsString(response).getBytes());
            responseBody.flush();
            responseBody.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CustomErrorResponse(HttpExchange exchange, String errorName, String errorMessage, int statusCode) {
        try {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));

            Map<String, Object> response = new HashMap<>();
            response.put("error", errorName);
            response.put("message", errorMessage);

            OutputStream responseBody = exchange.getResponseBody();
            exchange.sendResponseHeaders(statusCode, new ObjectMapper().writeValueAsString(response).length());
            responseBody.write(new ObjectMapper().writeValueAsString(response).getBytes());
            responseBody.flush();
            responseBody.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void BodyProcessErrorResponse(HttpExchange exchange, String formatNeed) {
        CustomErrorResponse(exchange, "FormatBodyFailed", "Please check your body, this api require a " + formatNeed + " data.");
    }

    public static void InsufficientPermissionsErrorResponse(HttpExchange exchange) {
        CustomErrorResponse(exchange, "InsufficientPermissions", "Please check your password or reset it, the root password is wrong.");
    }

    public static void DataErrorResponse(HttpExchange exchange, String message) {
        CustomErrorResponse(exchange, "DataError", message);
    }

    public static void ModuleUnactivatedErrorResponse(HttpExchange exchange, String unactivatedModuleName) {
        CustomErrorResponse(exchange, "ModuleUnactivatedError", unactivatedModuleName + " module isn't activate, please check it api is hooked(installed) and reload try again!", 500);
    }

    /**
     * Return missing parameter error response
     * @param exchange The handler can handle parameter
     * @param missingThings What parameter missed, split by space
     */
    public static void MissingArgumentsErrorResponse(HttpExchange exchange, String missingThings) {
        CustomErrorResponse(exchange, "MissingArguments", "Missing " + missingThings + ".");
    }
}
