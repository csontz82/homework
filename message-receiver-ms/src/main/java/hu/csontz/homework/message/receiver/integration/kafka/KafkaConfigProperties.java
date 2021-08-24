package hu.csontz.homework.message.receiver.integration.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfigProperties {
    private String bootstrapAddress;
    private KafkaTopicConfigProperties topic = new KafkaTopicConfigProperties();

    @Getter
    @Setter
    public class KafkaTopicConfigProperties {
        private String name;
        private int partitions;
        private int replicas;
    }
}
