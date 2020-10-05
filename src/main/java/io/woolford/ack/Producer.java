package io.woolford.ack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
class Producer {

    @Autowired
    KafkaTemplate kafkaTemplate;

    Logger LOG = LoggerFactory.getLogger(Producer.class);
    String topic = "spring-producer-ack";

    void sendMessageWithCallback(String message) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Message [{}] delivered to topic {}, partition {} with offset {}",
                        message,
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                        );
            }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Unable to deliver message [{}]. {}",
                        message,
                        ex.getMessage());
            }
        });
    }
}
