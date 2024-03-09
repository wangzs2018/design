package com.book.items.vistor;

import com.book.items.composite.AbstractProductItem;

public interface ItemVisitor<T> {
    T visitor(AbstractProductItem productItem);
}
