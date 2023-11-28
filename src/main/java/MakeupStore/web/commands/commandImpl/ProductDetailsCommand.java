package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.web.commands.ICommand;
import MakeupStore.model.dao.MakeupDao;
import MakeupStore.model.dao.impl.JdbcMakeupDao;
import MakeupStore.model.entities.makeup.Makeup;
import MakeupStore.model.exceptions.DaoException;
import MakeupStore.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ejevika
 * @version 1.0
 * Command to get product details page
 */
public class ProductDetailsCommand implements ICommand {
    private final MakeupDao makeupDao = JdbcMakeupDao.getInstance();
    private static final String MAKEUP_ATTRIBUTE = "makeup";

    /**
     * Return product details page of current makeup item
     * @param request http request
     * @return product details page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Makeup makeup;
        try {
            if (request.getParameter("makeup_id") == null){
                makeup = makeupDao.get(Long.parseLong(request.getAttribute("makeup_id").toString())).orElse(null);
            } else {
                makeup = makeupDao.get(Long.valueOf(request.getParameter("makeup_id"))).orElse(null);
            }
        } catch (DaoException e) {
            throw new CommandException(e.getMessage());
        }
        if (makeup != null) {
            request.setAttribute(MAKEUP_ATTRIBUTE, makeup);

            return JspPageName.PRODUCT_PAGE;
        }
        else{
            return JspPageName.PRODUCT_NOT_FOUND_PAGE;
        }
    }
}
