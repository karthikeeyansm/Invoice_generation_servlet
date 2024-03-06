package auth;

import db.UserHelperDb;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        int user_id = Integer.parseInt(servletRequest.getParameter("user_id"));

        String user_id = servletRequest.getParameter("user_id");

        if(UserHelperDb.checkUser(user_id))
            filterChain.doFilter(servletRequest,servletResponse);
        else
        {
            PrintWriter out = servletResponse.getWriter();
            out.println("user is not authorized to access");
        }


    }

    @Override
    public void destroy() {
        System.out.println("Auth filter ------ destroyed");
    }
}
