package hu.csontz.homework.message.receiver.controller;

import hu.csontz.homework.message.receiver.model.request.PostMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;

@RestController
public class MessageReceiverController {
    private static Logger log = LoggerFactory.getLogger(MessageReceiverController.class);

    @PostMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public void publishToTopic(@Valid @RequestBody PostMessageRequest messageData) {
        log.info("Message received: sent at {}, {}", messageData.getTimestamp().format(DateTimeFormatter.ISO_ZONED_DATE_TIME), messageData.getContent());
    }
}
