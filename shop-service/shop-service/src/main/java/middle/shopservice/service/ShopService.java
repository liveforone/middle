package middle.shopservice.service;

import lombok.RequiredArgsConstructor;
import middle.shopservice.async.AsyncConstant;
import middle.shopservice.domain.Shop;
import middle.shopservice.dto.updateShop.UpdateAddressRequest;
import middle.shopservice.dto.ShopRequest;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.dto.updateShop.UpdateNameRequest;
import middle.shopservice.dto.updateShop.UpdateTelRequest;
import middle.shopservice.feignClient.RecommendFeignService;
import middle.shopservice.feignClient.constant.CircuitLog;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.service.util.ShopMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;
    private final RecommendFeignService recommendFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    private Long getRecommendShopId() {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT_LOG.getValue())
                .run(
                        recommendFeignService::getRecommendShopId,
                        throwable -> null
                );
    }

    public ShopResponse getShopById(Long shopId) {
        return ShopMapper.entityToDtoDetail(shopRepository.findOneById(shopId));
    }

    public ShopResponse getShopByUsername(String username) {
        return ShopMapper.entityToDtoDetail(shopRepository.findOneByUsername(username));
    }

    public Long getShopByUsernameForProvide(String username) {
        return shopRepository.findOneByUsernameForProvide(username);
    }

    public List<ShopResponse> getHomePage(Long lastId) {
        Long recommendShopId = getRecommendShopId();
        return shopRepository.findHomePage(recommendShopId, lastId);
    }

    public List<ShopResponse> searchShop(String city, String street, String shopName, Long lastId) {
        Long recommendShopId = getRecommendShopId();
        return shopRepository.searchShopPage(city, street, shopName, recommendShopId, lastId);
    }

    @Transactional
    public void createShop(ShopRequest shopRequest, String username) {
        Shop shop = Shop.create(shopRequest, username);
        shopRepository.save(shop);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateShopName(UpdateNameRequest request, String username) {
        Shop shop = shopRepository.findOneByUsername(username);
        shop.updateShopName(request);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateTel(UpdateTelRequest request, String username) {
        Shop shop = shopRepository.findOneByUsername(username);
        shop.updateTel(request);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateAddress(UpdateAddressRequest request, String username) {
        Shop shop = shopRepository.findOneByUsername(username);
        shop.updateAddress(request);
    }
}
