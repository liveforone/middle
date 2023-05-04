package middle.shopservice.kafka;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.async.AsyncConstant;
import middle.shopservice.kafka.constant.KafkaLog;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static middle.shopservice.kafka.constant.Topic.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    Gson gson = new Gson();

    @Async(AsyncConstant.commandAsync)
    public void removeRecommend(Long shopId) {
        String jsonOrder = gson.toJson(shopId);
        String topic = REMOVE_RECOMMEND;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void removeTimetable(Long shopId) {
        String jsonOrder = gson.toJson(shopId);
        String topic = REMOVE_TIMETABLE;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }

    @Async(AsyncConstant.commandAsync)
    public void removeReview(Long shopId) {
        String jsonOrder = gson.toJson(shopId);
        String topic = REMOVE_REVIEW;
        kafkaTemplate.send(topic, jsonOrder);
        log.info(KafkaLog.KAFKA_SEND_LOG.getValue() + topic);
    }
}
