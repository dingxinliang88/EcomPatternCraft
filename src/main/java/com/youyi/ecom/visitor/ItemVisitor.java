package com.youyi.ecom.visitor;

import com.youyi.ecom.composite.BaseProductItem;

/**
 * 访问者模式 抽象访问者
 */
public interface ItemVisitor<T> {

    T visitor(BaseProductItem productItem);

}
