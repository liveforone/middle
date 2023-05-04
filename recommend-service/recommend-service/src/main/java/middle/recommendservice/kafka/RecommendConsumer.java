package middle.recommendservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.recommendservice.async.AsyncConstant;
import middle.recommendservice.kafka.constant.KafkaLog;
import middle.recommendservice.repository.RecommendRepository;
import middle.recommendservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static middle.recommendservice.kafka.constant.Topic.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendConsumer {

    private final RecommendRepository recommendRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = REMOVE_RECOMMEND)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeRecommend(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long shopId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(shopId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            recommendRepository.deleteOneByShopId(shopId);
            log.info(KafkaLog.REMOVE_RECOMMEND.getValue() + shopId);
        }
    }
}
