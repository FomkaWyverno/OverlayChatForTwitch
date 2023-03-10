package ua.wyverno.twitch.api.http.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.wyverno.twitch.api.authorization.AccessTokenNoLongerValidException;
import ua.wyverno.twitch.api.authorization.Authorization;
import ua.wyverno.twitch.api.chat.ChatWebSocketServer;
import ua.wyverno.twitch.api.http.server.HttpHandle;

import java.io.IOException;
import java.io.OutputStream;

@HttpHandle(path = "/close")
public class CloseHandle implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(CloseHandle.class);

    @Override
    public void handle(HttpExchange t) throws IOException {
        logger.debug("Close POST!");

        String response = "CLOSE SERVER";

        t.sendResponseHeaders(200,response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

        t.getHttpContext().getServer().stop(0);
        try {
            Authorization.getAccountInstance().closeAccount();
        } catch (AccessTokenNoLongerValidException e) {
            logger.trace("Account is not logged");
        }

        ChatWebSocketServer.stopWSS();
        logger.info("HTTP Server - is stop");
    }
}
