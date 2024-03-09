package com.book.items.composite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComposite extends AbstractProductItem {
    private int id;
    private int pid;
    private String name;
    private List<AbstractProductItem> child = new ArrayList<>();

    @Override
    public void addProductItem(AbstractProductItem item) {
        this.child.add(item);
    }

    @Override
    public void delProductChild(AbstractProductItem item) {
        ProductComposite removeItem = (ProductComposite) item;
        Iterator iterator = child.iterator();
        while (iterator.hasNext()) {
            ProductComposite composite = (ProductComposite) iterator.next();
            if(composite.getId() == removeItem.getId()) {
                iterator.remove();
                break;
            }
        }
    }
}
