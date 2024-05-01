package com.youyi.ecom.composite;

/**
 * 组合模式 Component 抽象组件
 */
public abstract class BaseProductItem {

    protected void add(BaseProductItem item) {
        throw new UnsupportedOperationException("BaseProductItem can't add");
    }

    protected void remove(BaseProductItem item) {
        throw new UnsupportedOperationException("BaseProductItem can't remove");
    }
}
