package com.youyi.ecom.controller;

import com.youyi.ecom.composite.ProductComposite;
import com.youyi.ecom.pojo.po.ProductItem;
import com.youyi.ecom.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductItemService productItemService;

    @PostMapping("/add")
    public ProductComposite add(@RequestBody ProductItem item) {
        return productItemService.addItem(item);
    }

    @PostMapping("/del")
    public ProductComposite del(@RequestBody ProductItem item) {
        return productItemService.delItem(item);
    }

    @GetMapping("/all")
    public ProductComposite getAll() {
        return productItemService.getAllItems();
    }
}
