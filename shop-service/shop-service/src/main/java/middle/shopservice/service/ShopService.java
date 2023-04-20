package middle.shopservice.service;

import lombok.RequiredArgsConstructor;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.service.util.ShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopResponse getShopById(Long shopId) {
        return ShopMapper.entityToDtoDetail(shopRepository.findOneById(shopId));
    }
}
