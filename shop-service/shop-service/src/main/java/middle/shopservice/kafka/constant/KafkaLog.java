package middle.shopservice.kafka.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KafkaLog {

    KAFKA_RECEIVE_LOG("Consumer receive Kafka Message -> "),
    KAFKA_SEND_LOG("Kafka send Success | Topic : "),
    KAFKA_NULL_LOG("!! Kafka Message is Null !!"),
    INCREASE_SHOP_GOOD("Increase Shop good | Shop id : "),
    INCREASE_SHOP_BAD("Increase Shop bad | Shop id : "),
    REMOVE_SHOP_BELONG_MEMBER("Remove Shop Belong Member | Username : ");

    private final String value;
}
