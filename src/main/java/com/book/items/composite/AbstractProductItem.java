package com.book.items.composite;

public abstract class AbstractProductItem {
    //增加 商品类目
    protected void addProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child add!");
    }
    //移除 商品类目
    protected void delProductChild(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child remove!");
    }
}
