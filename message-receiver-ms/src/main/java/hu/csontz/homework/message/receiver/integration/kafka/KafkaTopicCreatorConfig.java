package hu.csontz.homework.message.receiver.integration.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicCreatorConfig {
    private static Logger log = LoggerFactory.getLogger(KafkaTopicCreatorConfig.class);

    @Autowired
    private KafkaConfigProperties kafkaProps;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapAddress());

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createTopic() {
        log.info("Creating kafka topic {} with {} partitions and {} replicas via {} bootstrap address", kafkaProps.getTopic().getName(), kafkaProps.getTopic().getPartitions(), kafkaProps.getTopic().getReplicas(), kafkaProps.getBootstrapAddress());

        return new NewTopic(kafkaProps.getTopic().getName(), kafkaProps.getTopic().getPartitions(), (short) kafkaProps.getTopic().getReplicas());
    }
}
