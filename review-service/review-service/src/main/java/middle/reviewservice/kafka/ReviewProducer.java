package middle.reviewservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.reviewservice.async.AsyncConstant;
import middle.reviewservice.kafka.constant.KafkaLog;
import middle.reviewservice.kafka.constant.Topic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void shopIsGood(Long shopId) {
        String jsonOrder = gson.toJson(shopId);
        String topic = Topic.SHOP_IS_GOOD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void shopIsBad(Long shopId) {
        String jsonOrder = gson.toJson(shopId);
        String topic = Topic.SHOP_IS_BAD;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
