package ua.wyverno.twitch.api.authorization.http.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.twitch.api.authorization.ResultAsk;

import java.io.*;

public class PostHandle implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(PostHandle.class);
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.debug("POST /processData");
        InputStream inputStream = exchange.getRequestBody();
        logger.debug("Get requestBody");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String requestBody = reader.readLine();
        reader.close();

        logger.debug("RequestBody -> " + requestBody);

        ResultAsk resultAsk = new ObjectMapper().readValue(requestBody, ResultAsk.class);
        logger.debug("Created Result Ask object!");
        logger.info("Created ResultAsk for HttpAuthServer");

        String response = "OK";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        logger.debug("Send response Headers 200");
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        logger.debug("END POST /processData");
    }
}
