package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.web.commands.ICommand;
import MakeupStore.web.exceptions.CommandException;
import MakeupStore.model.service.CartService;
import MakeupStore.model.service.impl.HttpSessionCartService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ejevika
 * @version 1.0
 * Command to get cart jsp
 */
public class CartGetCommand implements ICommand {
    private static final String CART_ATTRIBUTE = "cart";
    private final CartService cartService = HttpSessionCartService.getInstance();

    /**
     * Get cart jsp
     *
     * @param request http request
     * @return cart jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(CART_ATTRIBUTE, cartService.getCart(request.getSession()));
        return JspPageName.CART_JSP;
    }
}
