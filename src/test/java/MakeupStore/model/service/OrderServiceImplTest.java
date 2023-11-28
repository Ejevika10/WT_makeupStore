package MakeupStore.model.service;

import MakeupStore.model.entities.cart.Cart;
import MakeupStore.model.entities.cart.CartItem;
import MakeupStore.model.entities.order.Order;
import MakeupStore.model.entities.makeup.Makeup;
import MakeupStore.model.service.impl.OrderServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest {
    private OrderService orderService;


    @Before
    public void init() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Test
    public void testCreatingOrderFromCart() {
        Cart cart = new Cart();
        cart.setTotalCost(new BigDecimal(100));
        cart.setTotalItems(3);

        List<CartItem> testList = new ArrayList<>();
        testList.add(new CartItem(new Makeup(), 2));
        cart.setItems(testList);

        Order order = orderService.createOrder(cart);
        Assert.assertEquals(order.getSubtotal(), cart.getTotalCost());
    }

    @Test
    public void testCloningObjectsWhileCreatingOrderFromCart() {
        Cart cart = new Cart();
        cart.setTotalCost(new BigDecimal(100));
        cart.setTotalItems(1);

        List<CartItem> testList = new ArrayList<>();
        testList.add(new CartItem(new Makeup(), 2));
        cart.setItems(testList);

        Order order = orderService.createOrder(cart);
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            Assert.assertEquals(order.getOrderItems().get(i).getMakeupItem(), cart.getItems().get(i).getMakeupItem());
            Assert.assertEquals(order.getOrderItems().get(i).getQuantity(), cart.getItems().get(i).getQuantity());
        }
    }

    @After
    public void clean() {
        orderService = null;
    }
}
