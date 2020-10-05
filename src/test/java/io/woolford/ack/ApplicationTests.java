package io.woolford.ack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class ApplicationTests {

    @Autowired
    Producer producer;

    @Test
    void contextLoads() {

    }

    @Test
    void writeTest(){
        producer.sendMessageWithCallback("test");
    }

}
