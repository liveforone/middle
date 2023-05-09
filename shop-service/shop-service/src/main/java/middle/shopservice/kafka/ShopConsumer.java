package middle.shopservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.async.AsyncConstant;
import middle.shopservice.domain.Shop;
import middle.shopservice.kafka.constant.KafkaLog;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.utility.CommonUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static middle.shopservice.kafka.constant.Topic.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopConsumer {

    private final ShopRepository shopRepository;
    private final ShopProducer shopProducer;
    ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = REMOVE_SHOP_BELONG_MEMBER)
    @Async(AsyncConstant.commandAsync)
    @Transactional
    public void removeShopBelongMember(String kafkaMessage) throws JsonProcessingException {
        log.info(KafkaLog.KAFKA_RECEIVE_LOG.getValue() + kafkaMessage);

        String username = objectMapper.readValue(kafkaMessage, String.class);

        if (CommonUtils.isNull(username)) {
            log.info(KafkaLog.KAFKA_NULL_LOG.getValue());
        } else {
            Shop shop = shopRepository.findOneByUsername(username);
            shopProducer.removeRecommend(shop.getId());
            shopProducer.removeTimetable(shop.getId());
            shopRepository.deleteOneByUsername(username);
            log.info(KafkaLog.REMOVE_SHOP_BELONG_MEMBER.getValue() + username);
        }
    }
}
