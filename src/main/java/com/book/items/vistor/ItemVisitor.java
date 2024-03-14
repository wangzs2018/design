package com.book.items.vistor;

import com.book.items.composite.AbstractProductItem;

/**
 * 抽象访问者
 * @param <T>
 */
public interface ItemVisitor<T> {
    T visitor(AbstractProductItem productItem);
}
