package com.book.items.vistor;

import com.book.items.composite.AbstractProductItem;
import com.book.items.composite.ProductComposite;
import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddItemVisitor implements ItemVisitor<AbstractProductItem>{
    @Autowired
    private RedisCommonProcessor redisProcessor;
    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        ProductComposite addItem = (ProductComposite) productItem;
        if(addItem.getPid() == currentItem.getId()) {
            currentItem.addProductItem(addItem);
            return currentItem;
        }
        addChild(addItem, currentItem);
        return currentItem;
    }

    private void addChild(ProductComposite addItem, ProductComposite currentItem) {
        for(AbstractProductItem abstractItem : currentItem.getChild()) {
            ProductComposite item = (ProductComposite) abstractItem;
            if(item.getId() == addItem.getPid()) {
                item.addProductItem(addItem);
                break;
            } else {
                addChild(addItem, item);
            }
        }
    }
}
