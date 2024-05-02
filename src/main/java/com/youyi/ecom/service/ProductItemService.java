package com.youyi.ecom.service;

import com.youyi.ecom.composite.BaseProductItem;
import com.youyi.ecom.composite.ProductComposite;
import com.youyi.ecom.constants.ProductItemConstant;
import com.youyi.ecom.pojo.po.ProductItem;
import com.youyi.ecom.repo.ProductItemRepository;
import com.youyi.ecom.util.RedisUtil;
import com.youyi.ecom.visitor.AddItemVisitor;
import com.youyi.ecom.visitor.DelItemVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductItemService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private AddItemVisitor addItemVisitor;

    @Autowired
    private DelItemVisitor delItemVisitor;

    public ProductComposite addItem(ProductItem item) {
        String name = item.getName();
        Long pid = item.getPid();

        productItemRepository.addItem(name, pid);

        ProductComposite toAddItem = ProductComposite.builder()
                .id(productItemRepository.findByNameAndPid(name, pid).getId())
                .name(name)
                .pid(pid)
                .children(new ArrayList<>())
                .build();
        BaseProductItem newItems = addItemVisitor.visitor(toAddItem);
        redisUtil.set(ProductItemConstant.PRODUCT_CACHE_KEY, newItems);
        return (ProductComposite) newItems;
    }

    public ProductComposite delItem(ProductItem item) {
        Long id = item.getId();
        String name = item.getName();
        Long pid = item.getPid();

        productItemRepository.delItem(id);

        ProductComposite toDelItem = ProductComposite.builder()
                .id(id)
                .name(name)
                .pid(pid)
                .build();
        BaseProductItem newItems = delItemVisitor.visitor(toDelItem);
        redisUtil.set(ProductItemConstant.PRODUCT_CACHE_KEY, newItems);
        return (ProductComposite) newItems;
    }

    public ProductComposite getAllItems() {
        Object cacheItems = redisUtil.get(ProductItemConstant.PRODUCT_CACHE_KEY);
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }

        List<ProductItem> productItemList = productItemRepository.findAll();
        ProductComposite itemTree = generateProductTree(productItemList);
        if (itemTree == null) {
            throw new RuntimeException("product item tree is null");
        }

        redisUtil.set(ProductItemConstant.PRODUCT_CACHE_KEY, itemTree);
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
