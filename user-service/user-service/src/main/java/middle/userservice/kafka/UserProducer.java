package middle.userservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.userservice.async.AsyncConstant;
import middle.userservice.kafka.constant.KafkaLog;
import middle.userservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void removeShopBelongMember(String username) {
        String jsonOrder = gson.toJson(username);
        String topic = Topic.REMOVE_SHOP_BELONG_MEMBER;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
