package com.youyi.ecom.service;

import com.youyi.ecom.composite.BaseProductItem;
import com.youyi.ecom.composite.ProductComposite;
import com.youyi.ecom.pojo.po.ProductItem;
import com.youyi.ecom.repo.ProductItemRepository;
import com.youyi.ecom.util.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductItemService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProductItemRepository productItemRepository;

    public ProductComposite getAllItems() {
        Object cacheItems = redisUtil.get("design:product:all");
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }

        List<ProductItem> productItemList = productItemRepository.findAll();
        ProductComposite itemTree = generateProductTree(productItemList);
        if (itemTree == null) {
            throw new RuntimeException("product item tree is null");
        }

        redisUtil.set("design:product:all", itemTree);
        return itemTree;
    }

    private ProductComposite generateProductTree(List<ProductItem> productItemList) {
        List<ProductComposite> composites = new ArrayList<>(productItemList.size());
        productItemList.forEach(item -> composites.add(
                ProductComposite.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .pid(item.getPid())
                        .build()
        ));

        // group by pid
        Map<Long, List<ProductComposite>> pidCompositesMap = composites.stream()
                .collect(Collectors.groupingBy(ProductComposite::getPid));

        composites.forEach(item -> {
            List<ProductComposite> compositeList = pidCompositesMap.get(item.getId());
            if (compositeList != null) {
                item.setChildren(
                        compositeList
                                .stream().map(composite -> (BaseProductItem) composite)
                                .toList());
            }
        });
        return composites.isEmpty() ? null : composites.get(0);
    }


}
