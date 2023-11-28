package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.web.commands.ICommand;
import MakeupStore.model.dao.OrderDao;
import MakeupStore.model.dao.impl.JdbcOrderDao;
import MakeupStore.model.exceptions.DaoException;
import MakeupStore.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ejevika
 * @version 1.0
 * Command for admin orders page
 */
public class AdminOrdersCommand implements ICommand {
    private static final String ORDERS_ATTRIBUTE = "orders";
    private final OrderDao orderDao = JdbcOrderDao.getInstance();

    /**
     * @param request http request
     * @return Return armin order page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            request.setAttribute(ORDERS_ATTRIBUTE, orderDao.findOrders());
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        return JspPageName.ADMIN_ORDERS_PAGE_JSP;
    }
}
