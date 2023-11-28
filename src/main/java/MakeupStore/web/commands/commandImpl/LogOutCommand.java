package MakeupStore.web.commands.commandImpl;

import MakeupStore.web.JspPageName;
import MakeupStore.web.commands.ICommand;
import MakeupStore.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ejevika
 * @version 1.0
 * Command to log out user
 */
public class LogOutCommand implements ICommand {
    /**
     * Log out user and return authorisation page jsp
     *
     * @param request http request
     * @return authorisation page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        logout(request);
        return JspPageName.AUTHORISATION_JSP;
    }

    /**
     * Remove role and login of user from session
     *
     * @param request http request
     */
    public void logout(HttpServletRequest request) {
        request.getSession().setAttribute("role", "visitor");
        request.getSession().setAttribute("login", "");
    }
}
