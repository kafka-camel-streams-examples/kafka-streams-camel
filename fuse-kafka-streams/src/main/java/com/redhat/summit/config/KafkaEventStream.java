//package com.redhat.summit.config;
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Produced;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
//
//@Configuration
//@EnableKafka
//@EnableKafkaStreams
//public class KafkaEventStream {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventStream.class);
//
//    @Value("${camel.component.kafka.brokers}")
//    private String brokerUrl;
//
//    @Value("${producer.topic}")
//    private String inpTopic;
//
//    @Value("${consumer.topic}")
//    private String outTopic;
//
//    // (name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    @Bean
//    public StreamsConfig kStreamsConfigs(KafkaProperties kafkaProperties) {
//        Map<String, Object> props = new HashMap<>();
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        return new StreamsConfig(props);
//    }
//
//    @Bean
//    public KStream<String, String> kStream(StreamsBuilder builder) {
//
//        final KStream<String, String> stream = builder.stream(inpTopic);
//
//        stream.mapValues(value -> {
//            LOGGER.info("Camel::Kafka::Stream - Processing Value :: {}", value);
//            return value;
//        }).to(outTopic, Produced.with(Serdes.String(), Serdes.String()));
//
//        return stream;
//    }
//
//}
