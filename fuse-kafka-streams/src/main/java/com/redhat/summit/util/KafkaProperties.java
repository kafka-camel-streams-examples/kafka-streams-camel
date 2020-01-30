package com.redhat.summit.util;

import lombok.Getter;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Getter
public class KafkaProperties {

    @Value("${kafka.broker.url:localhost:9092}")
    private String kafkaConnect;

    @Value("${kafka.topic.consumer.test:test-topic-out}")
    private String kafkaConsumerTestTopic;

    @Value("${kafka.topic.consumer.test:test-topic-in}")
    private String kafkaProducerTestTopic;

    @Value("${kafka.topic.consumer.test.consumergroup:hello-world-consumer}")
    private String kafkaConsumerGroupTestTopic;

    @Value("${kafka.autoOffsetReset:earliest}")
    private String autoOffsetReset;

    @Value("${kafka.ssltrust.location:test_path}")
    private String kafkasfmGuestCancelSSLTruststoreLocation;

    @Value("${kafka.ssltrust.password:password}")
    private String kafkasfmGuestCancelSSLTruststorePassword;

    @Value("${kafka.sslkeys.location:test_path}")
    private String kafkasfmGuestCancelSSLKeystoreLocation;

    @Value("${kafka.sslkeys.password:password}")
    private String kafkasfmGuestCancelSSLKeystorePassword;

    @Value("${kafka.consumerCount:1}")
    private String consumerCount;

    @Value("${kafka.consumerStream:1}")
    private String consumerStream;

    @Value("${kafka.consumerRequestTimeoutMs:450000}")
    private String consumerRequestTimeoutMs;

    @Value("${kafka.sessionTimeoutMs:300000}")
    private String sessionTimeoutMs;

    @Value("${kafka.consumer.seekTo:beginning}")
    private String consumerSeekTo;

    public String buildConsumerTopicUrl(String topic, String group) {
        String uri = this.getKafkaConnectionUri(topic)
                + "&groupId=" + group
                + "&autoOffsetReset=" + autoOffsetReset
                + "&consumersCount=" + consumerCount
                + "&consumerStreams=" + consumerStream
                + "&consumerRequestTimeoutMs=" + consumerRequestTimeoutMs
                + "&sessionTimeoutMs=" + sessionTimeoutMs
                + "&serializerClass=org.apache.kafka.common.serialization.StringSerializer";
        return uri;
    }

    public String getKafkaConnectionUri(String topic) {
        return "kafka:" + topic + "?brokers=" + kafkaConnect;
    }

    public String kafkaTestConsumerUri() {
        return this.buildConsumerTopicUrl(this.kafkaConsumerTestTopic, this.kafkaConsumerGroupTestTopic);
    }

    public String kafkaTestProducerUri() {
        return this.getKafkaConnectionUri(this.kafkaProducerTestTopic);
    }

    public Properties getConsumerProperties() {
        Properties props = new Properties();
        this.setProperty(props, "bootstrap.servers", kafkaConnect);
        this.setProperty(props, "application.id", "id");
        this.setProperty(props, "default.key.serde", Serdes.String().getClass());
        this.setProperty(props, "default.value.serde", Serdes.String().getClass());
        return props;
    }

    private void setProperty(Properties prop, Object key, Object value) {
        if (key != null && value != null) {
            prop.put(key, value);
        }
    }
}
