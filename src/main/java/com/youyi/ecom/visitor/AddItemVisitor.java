package com.youyi.ecom.visitor;

import com.youyi.ecom.composite.BaseProductItem;
import com.youyi.ecom.composite.ProductComposite;
import com.youyi.ecom.constants.ProductItemConstant;
import com.youyi.ecom.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 访问者模式 具体访问者
 */
@Component
public class AddItemVisitor implements ItemVisitor<BaseProductItem> {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseProductItem visitor(BaseProductItem productItem) {
        ProductComposite currItem = (ProductComposite) redisUtil.get(
                ProductItemConstant.PRODUCT_CACHE_KEY);
        ProductComposite toAddItem = (ProductComposite) productItem;

        if (currItem.getId().equals(toAddItem.getPid())) {
            currItem.add(toAddItem);
            return currItem;
        }
        addChild(currItem, toAddItem);
        return currItem;
    }

    private void addChild(ProductComposite currItem, ProductComposite toAddItem) {
        for (BaseProductItem child : currItem.getChildren()) {
            ProductComposite item = (ProductComposite) child;
            if (item.getId().equals(toAddItem.getPid())) {
                item.add(toAddItem);
                return;
            }
            addChild((ProductComposite) child, toAddItem);
        }
    }
}
