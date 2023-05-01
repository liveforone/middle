package middle.timetableservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.timetableservice.async.AsyncConstant;
import middle.timetableservice.kafka.constant.KafkaLog;
import middle.timetableservice.kafka.constant.Topic;
import middle.timetableservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimetableConsumer {

    //repos
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.REMOVE_TIMETABLE)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeTimetable(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long shopId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(shopId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            //repos
            log.info(KafkaLog.REMOVE_RECOMMEND.getValue() + shopId);
        }
    }
}
