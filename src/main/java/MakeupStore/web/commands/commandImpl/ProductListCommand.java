package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.model.dao.MakeupDao;
import MakeupStore.model.dao.impl.JdbcMakeupDao;
import MakeupStore.model.enums.SortField;
import MakeupStore.model.enums.SortOrder;
import MakeupStore.model.exceptions.DaoException;
import MakeupStore.web.commands.ICommand;
import MakeupStore.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * @author ejevika
 * @version 1.0
 * Command to show list of products
 */
public class ProductListCommand implements ICommand {
    private final MakeupDao makeupDao = JdbcMakeupDao.getInstance();
    private static final String QUERY_PARAMETER = "query";
    private static final String SORT_PARAMETER = "sort";
    private static final String ORDER_PARAMETER = "order";
    private static final String MAKEUP_ATTRIBUTE = "makeup";
    private static final String PAGE_PARAMETER = "page";
    private static final String PAGE_ATTRIBUTE = "numberOfPages";
    private static final int ITEMS_ON_PAGE = 10;

    /**
     * Get product list and return product list jsp
     * @param request http request
     * @return product list jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String pageNumber = request.getParameter(PAGE_PARAMETER);
        Long number;
        try {
            request.setAttribute(MAKEUP_ATTRIBUTE, makeupDao.findAll(((pageNumber == null ? 1 : Integer.parseInt(pageNumber)) - 1) * ITEMS_ON_PAGE, ITEMS_ON_PAGE,
                    Optional.ofNullable(request.getParameter(SORT_PARAMETER)).map(SortField::valueOf).orElse(null),
                    Optional.ofNullable(request.getParameter(ORDER_PARAMETER)).map(SortOrder::valueOf).orElse(null), request.getParameter(QUERY_PARAMETER)));
            number = makeupDao.numberByQuery(request.getParameter(QUERY_PARAMETER));
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        request.setAttribute(PAGE_ATTRIBUTE, (number + ITEMS_ON_PAGE - 1) / ITEMS_ON_PAGE);
        return JspPageName.PRODUCT_LIST_JSP;
    }
}
