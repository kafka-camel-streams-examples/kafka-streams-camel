package com.redhat.summit.route;

import com.redhat.summit.util.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaProducerRoute extends RouteBuilder {

    @Autowired
    private KafkaProperties kafkaProperties;

    String testKafkaMessage = "Test Message from  MessagePublisherClient :: ";

    @Override
    public void configure() throws Exception {

        // @formatter:off

        log.info("Bootstrapping Kafka Producer Routes");

        // Timer service - Produce every 5s
        from("timer://simpleTimer?period=5000").routeId("Camel::Timer::Producer")
                .process(new Processor() {
                    public void process(Exchange ex) {
                        ex.getIn().setBody(getMessageBody());
                    }
                })
                .log(LoggingLevel.DEBUG, "Sending Kafka Producer request")
                .to("direct:kafkaStart");

        // Produce message
        from("direct:kafkaStart").routeId("Camel::Kafka::Producer")
                .setHeader(KafkaConstants.PARTITION_KEY, simple("0"))
                .setHeader(KafkaConstants.KEY, simple("1"))
                .log(LoggingLevel.INFO, "PRODUCER  :: " + kafkaProperties.getKafkaProducerTestTopic() + " :: ${body}")
                .log(LoggingLevel.DEBUG, "Sending Kafka Message Headers: ${headers}")
                .to(kafkaProperties.kafkaTestProducerUri());

        // @formatter:on
    }

    private String getMessageBody() {
        return this.testKafkaMessage + UUID.randomUUID().toString();
    }
}
