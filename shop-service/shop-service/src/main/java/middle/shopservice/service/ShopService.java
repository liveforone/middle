package middle.shopservice.service;

import lombok.RequiredArgsConstructor;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.feignClient.AdvertisementFeignService;
import middle.shopservice.feignClient.constant.CircuitLog;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.service.util.ShopMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
    private final AdvertisementFeignService advertisementFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    private Long getRecommendShopId() {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT_LOG.getValue())
                .run(
                        advertisementFeignService::getDrawShopId,
                        throwable -> null
                );
    }

    public ShopResponse getShopById(Long shopId) {
        return ShopMapper.entityToDtoDetail(shopRepository.findOneById(shopId));
    }

    public List<ShopResponse> getHomePage(Long lastId, int pageSize) {
        Long recommendShopId = getRecommendShopId();
        return shopRepository.findHomePage(recommendShopId, lastId, pageSize);
    }

    public List<ShopResponse> searchShopNamePage(String shopName, Long lastId, int pageSize) {
        Long recommendShopId = getRecommendShopId();
        return shopRepository.searchShopNamePage(shopName, recommendShopId, lastId, pageSize);
    }
}
