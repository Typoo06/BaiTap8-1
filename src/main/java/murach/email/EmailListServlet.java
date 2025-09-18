package murach.email;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;

public class EmailListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "/index.html";

        // get current action
        String action = req.getParameter("action");
        if (action == null) {
            action = "join"; // this is the default action.
        }

        // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.jsp"; // the 'join' page
        }
        else if (action.equals("add")) {
            // get parrameters from the request
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");

            // store data in User object
            User user = new User(firstName, lastName, email);

            String message = "";
            // validate the parameters
            if (UserDB.emailExists(user.getEmail())) {
                message = "This email address already exists.<br>" + "Please enter another email address.";
                url = "/index.jsp";
            } else {
                message = "";
                url = "/thanks.jsp";
                UserDB.insert(user);
            }
            req.setAttribute("user", user);
            req.setAttribute("message", message);
        }
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}
