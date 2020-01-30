//package com.redhat.summit.config;
//
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.KStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.Properties;
//
//@Service
//public class KafkaStreamConsumer {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger();
//
//    @Autowired
//    private KafkaConsumerProperties kafkaConsumerProperties;
//
//    private String topic;
//
//    @PostConstruct
//    public void processKafkaConsumer() {
//        Properties properties = kafkaConsumerProperties.getConsumerProperties();
//        KafkaStreams kafkaStreams = null;
//        try {
//            StreamsBuilder builder = new StreamsBuilder();
//            KStream<String, String> kStream = builder.stream(topic);
//            kStream.mapValues(value -> {
//                LOGGER.info("Camel::Kafka::Stream - Processing Value :: {}", value);
//                return value;
//            }).to(outTopic, Produced.with(Serdes.String(), Serdes.String()));
//
//            kStream.process(kafkaOrderFeedProcessor, new String[0]);
//            kafkaStreams = new KafkaStreams(builder.build(), properties);
//            kafkaStreams.start();
//            log.info("op={}, status=OK, desc={}", "KafkaConsumer", "kafka consumer stream  started successfully");
//        } catch (Exception var9) {
//            log.error("op={}, status=KO, desc={} and exception={}", new Object[]{"KafkaConsumer", "exception while starting kafka consumer stream", var9.getMessage()});
//            if (kafkaStreams != null) {
//                kafkaStreams.close();
//            }
//        }
//
//    }
//}