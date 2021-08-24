package hu.csontz.homework.message.receiver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.csontz.homework.message.receiver.model.request.PostMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;

@RestController
public class MessageReceiverController {
    private static Logger log = LoggerFactory.getLogger(MessageReceiverController.class);

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public void publishToTopic(@Valid @RequestBody PostMessageRequest messageData) throws JsonProcessingException {
        log.info("Message received: sent at {}, {}", messageData.getTimestamp().format(DateTimeFormatter.ISO_ZONED_DATE_TIME), messageData.getContent());

        String messageToSend = objectMapper.writeValueAsString(messageData);

        log.info("Sending message to {} kafka topic: {}", topicName, messageToSend);

        sendMessage(messageToSend);

        log.info("Message sent to {} kafka topic", topicName);
    }

    private void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }
}
