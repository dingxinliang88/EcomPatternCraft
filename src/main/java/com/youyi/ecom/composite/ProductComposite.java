package com.youyi.ecom.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 组合模式 Composite 树枝组件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductComposite extends BaseProductItem {

    private Long id;
    private String name;
    private Long pid;
    private List<BaseProductItem> children = new ArrayList<>();

    @Override
    public void add(BaseProductItem item) {
        this.children.add(item);
    }

    @Override
    public void remove(BaseProductItem item) {
        ProductComposite removeItem = (ProductComposite) item;
        Iterator<BaseProductItem> itr = children.iterator();
        while (itr.hasNext()) {
            ProductComposite child = (ProductComposite) itr.next();
            if (child.getId().equals(removeItem.getId())) {
                itr.remove();
                break;
            }
        }
    }
}
