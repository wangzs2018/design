package com.book.controller;

import com.book.items.composite.ProductComposite;
import com.book.pojo.ProductItem;
import com.book.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;
    @PostMapping("/fetchAllItems")
    public ProductComposite fetchAllItems(){
        return productItemService.fetchAllItems();
    }

    @PostMapping("/addItems")
    public ProductComposite addItems(@RequestBody ProductItem item){
        return productItemService.addItems(item);
    }

    @PostMapping("/delItems")
    public ProductComposite delItems(@RequestBody ProductItem item){
        return productItemService.delItems(item);
    }
}
