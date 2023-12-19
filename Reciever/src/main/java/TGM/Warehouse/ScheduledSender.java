package TGM.Warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledSender {

    private final MOMSender momSender;

    @Autowired
    public ScheduledSender(MOMSender momSender) {
        this.momSender = momSender;
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void sendMessagesPeriodically() {
        // Example: Sending a fixed message to a fixed destination
        String destinationQueue = "yourQueueName";
        String name = "ExampleName";
        String city = "ExampleCity";
        momSender.sendWarehouseData(name, city, destinationQueue);
    }
}
