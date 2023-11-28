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
 * Command to get user orders page
 */
public class UserOrdersCommand implements ICommand {
    private OrderDao orderDao = JdbcOrderDao.getInstance();
    private static final String ORDERS_ATTRIBUTE = "orders";

    /**
     * Get user orders and show user orders page
     * @param request http request
     * @return user orders page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            request.setAttribute(ORDERS_ATTRIBUTE, orderDao.findOrdersByLogin(request.getSession().getAttribute("login").toString()));
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        return JspPageName.USER_ORDERS_PAGE_JSP;
    }
}
