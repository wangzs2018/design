package com.book.pay.strategy.factory;

/**
 * 抽象工厂类
 * 只有在扩展新的具体工厂类子类时才发挥其优势
 * 可以不创建这个抽象工厂类
 * @param <T>
 */
public abstract class AbstractPayContextFactory<T> {
    public abstract T getContext(String payType);
}
