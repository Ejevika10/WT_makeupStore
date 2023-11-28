package MakeupStore.model.entities.stock;

import MakeupStore.model.entities.makeup.Makeup;

public class Stock {
    private Makeup makeup;
    private Integer stock;
    private Integer reserved;

    public Makeup getMakeup() {
        return makeup;
    }

    public void setMakeup(Makeup makeup) {
        this.makeup = makeup;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

}
