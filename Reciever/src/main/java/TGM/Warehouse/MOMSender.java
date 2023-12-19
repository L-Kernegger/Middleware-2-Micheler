package TGM.Warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MOMSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final XMLGenerator xmlGenerator;

    @Autowired
    public MOMSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper, XMLGenerator xmlGenerator) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.xmlGenerator = xmlGenerator;
    }

    public void sendWarehouseData(String name, String city, String destination) {
        try {
            String xmlData = xmlGenerator.getJSON(name, city);
            jmsTemplate.convertAndSend(destination, xmlData);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Senden der WarehouseData", e);
        }
    }
}
