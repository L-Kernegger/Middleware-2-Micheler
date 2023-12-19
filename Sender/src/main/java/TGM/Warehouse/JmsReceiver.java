package TGM.Warehouse;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

    @JmsListener(destination = "yourQueueName")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}