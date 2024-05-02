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
public class DelItemVisitor implements ItemVisitor<BaseProductItem> {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseProductItem visitor(BaseProductItem productItem) {
        ProductComposite currItem = (ProductComposite) redisUtil.get(
                ProductItemConstant.PRODUCT_CACHE_KEY);
        ProductComposite toDelItem = (ProductComposite) productItem;

        if (currItem.getId().equals(toDelItem.getId())) {
            throw new UnsupportedOperationException("根节点不能删除");
        }

        if (currItem.getId().equals(toDelItem.getPid())) {
            currItem.remove(toDelItem);
            return currItem;
        }
        delChild(currItem, toDelItem);
        return currItem;
    }

    private void delChild(ProductComposite currItem, ProductComposite toDelItem) {
        for (BaseProductItem child : currItem.getChildren()) {
            ProductComposite item = (ProductComposite) child;
            if (item.getId().equals(toDelItem.getPid())) {
                item.remove(toDelItem);
                return;
            }
            delChild((ProductComposite) child, toDelItem);
        }
    }
}
