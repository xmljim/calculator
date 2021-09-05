package io.xmljim.retirement.calculator.entity;

public class SimpleServiceItem<T> implements ServiceResult {
    private T item;
    public SimpleServiceItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
