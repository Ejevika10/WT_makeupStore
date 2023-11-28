package MakeupStore.model.entities.order;

import MakeupStore.model.entities.makeup.Makeup;

public class OrderItem {
    private Long id;
    private Makeup makeupItem;
    private Order order;
    private int quantity;

    public Makeup getMakeupItem() {
        return makeupItem;
    }

    public void setMakeupItem(final Makeup makeupItem) {
        this.makeupItem = makeupItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}