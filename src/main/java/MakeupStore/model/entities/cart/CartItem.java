package MakeupStore.model.entities.cart;

import MakeupStore.model.entities.makeup.Makeup;
import MakeupStore.model.exceptions.CloneException;

import java.io.Serializable;

public class CartItem implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private Makeup makeupItem;
    private int quantity;

    public CartItem(Makeup product, int quantity) {
        this.makeupItem = product;
        this.quantity = quantity;
    }

    public Makeup getMakeupItem() {
        return makeupItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setMakeupItem(Makeup product) {
        this.makeupItem = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "code=" + makeupItem.getId() +
                ", quantity=" + quantity;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException("Error copying the product " + makeupItem.getId() + "with quantity" + quantity);
        }
    }
}
