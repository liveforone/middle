package middle.reviewservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_SEND_LOG("Kafka send Success | Topic : "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    REMOVE_REVIEW("Remove review Belong shop | ShopId : ");

    private final String value;
}
