package TGM.Warehouse;

import org.apache.tomcat.websocket.WsRemoteEndpointAsync;
import org.springframework.http.MediaType;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RestController
public class JmsReceiver {

    public static HashMap<String, String> log = new HashMap<>();

    public static String send = "[]";

    public static String appendObjectToJsonArray(String jsonArrayStr, String jsonObjectStr) {
        String newjsonArrayStr = "";
        if (jsonArrayStr.endsWith("]")) {
            newjsonArrayStr = jsonArrayStr.substring(0, jsonArrayStr.length() - 1);
        }

        if (jsonArrayStr.equals("[]")) {
            return "[" + jsonObjectStr + "]";
        } else {
            return newjsonArrayStr + "," + jsonObjectStr + "]";
        }
    }


    @JmsListener(destination = "yourQueueName")
    public void receiveMessage(String message) {
        String patternString = "\"warehouseID\":\"(-?\\d+)\"";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);

        send = appendObjectToJsonArray(send,message);

        if (matcher.find()) {
            log.put(matcher.group(1), message);
        } else {
            System.out.println("Kein Warehouse ID im Message gefunden");
        }

        System.out.println("//");
        System.out.println(message);
    }

    @GetMapping(value = "/get-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendData() {
        return send;
    }
}
