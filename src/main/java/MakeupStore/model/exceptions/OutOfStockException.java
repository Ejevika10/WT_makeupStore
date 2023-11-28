package MakeupStore.model.exceptions;

import MakeupStore.model.entities.makeup.Makeup;

/**
 * @author nekit
 * @version 1.0
 */
public class OutOfStockException extends Exception {
    /**
     * Phone that outOfStock
     */
    private Makeup phone;
    /**
     * Requested stock of phone
     */
    private int requestedStock;
    /**
     * Available stock of phone
     */
    private int availableStock;

    /**
     * Constructor of exception
     * @param phone phone of exception
     * @param requestedStock requested stock of exception
     * @param availableStock available stock of exceptttion
     */
    public OutOfStockException(Makeup phone, int requestedStock, int availableStock) {
        this.phone = phone;
        this.requestedStock = requestedStock;
        this.availableStock = availableStock;
    }

    /**
     * Place exception message
     * @param s exception message
     */
    public OutOfStockException(String s) {
        super(s);
    }

    public Makeup getProduct() {
        return phone;
    }

    public int getRequestedStock() {
        return requestedStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

}
