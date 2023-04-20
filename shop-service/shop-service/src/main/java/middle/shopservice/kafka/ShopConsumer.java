package middle.shopservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.async.AsyncConstant;
import middle.shopservice.domain.Shop;
import middle.shopservice.kafka.constant.KafkaLog;
import middle.shopservice.kafka.constant.Topic;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopConsumer {

    private final ShopRepository shopRepository;
    private final ShopProducer shopProducer;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = Topic.SHOP_IS_GOOD)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void increaseShopIsGood(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long shopId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(shopId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Shop shop = shopRepository.findOneById(shopId);
            shop.increaseGood();
            log.info(KafkaLog.INCREASE_SHOP_GOOD.getValue() + shopId);
        }
    }

    @KafkaListener(topics = Topic.SHOP_IS_BAD)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void increaseShopIsBad(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        Long shopId = objectMapper.readValue(kafkaMessage, Long.class);

        if (CommonUtils.isNull(shopId)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Shop shop = shopRepository.findOneById(shopId);
            shop.increaseBad();
            log.info(KafkaLog.INCREASE_SHOP_BAD.getValue() + shopId);
        }
    }

    @KafkaListener(topics = Topic.REMOVE_SHOP_BELONG_MEMBER)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeShopBelongMember(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Shop shop = shopRepository.findOneByUsername(username);
            shopProducer.removeTimetable(shop.getId());
            shopRepository.deleteOneByUsername(username);
            log.info(KafkaLog.REMOVE_SHOP_BELONG_MEMBER.getValue() + username);
        }
    }
}
