package middle.shopservice.service;

import lombok.RequiredArgsConstructor;
import middle.shopservice.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {

    private ShopRepository shopRepository;
}
