package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.web.commands.ICommand;
import MakeupStore.web.exceptions.CommandException;
import MakeupStore.model.dao.OrderDao;
import MakeupStore.model.dao.impl.JdbcOrderDao;
import MakeupStore.model.exceptions.DaoException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ejevika
 * @version 1.0
 * Command to get order overview page
 */
public class OrderOverviewCommand implements ICommand {
    private OrderDao orderDao = JdbcOrderDao.getInstance();
    private static final String ORDER_ATTRIBUTE = "order";

    /**
     * Get order overview page
     *
     * @param request http request
     * @return order overview page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            if (request.getParameter("secureId") == null) {
                request.setAttribute(ORDER_ATTRIBUTE, orderDao.getBySecureID(request.getAttribute("secureId").toString()).orElse(null));
            } else {
                request.setAttribute(ORDER_ATTRIBUTE, orderDao.getBySecureID(request.getParameter("secureId")).orElse(null));
            }

        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        return JspPageName.ORDER_OVERVIEW_PAGE_JSP;
    }
}
