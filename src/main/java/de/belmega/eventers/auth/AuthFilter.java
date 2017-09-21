package de.belmega.eventers.auth;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Use this filter for all requests that contain the .xhtml file extension.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/internal/*"})
public class AuthFilter implements Filter {

    @Inject
    @ConfigurationValue("urls.pages.login")
    String loginPage;


    public static final String ATTRIBUTE_USER_ID = "userId";

    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(false);

        System.out.println(session);

        if (!userIsLoggedIn(session)) {
            System.out.println("Kein User eingeloggt, leite weiter zu Login");
            redirectToLogin((HttpServletResponse) response);
        } else {
            System.out.println("User ist eingeloggt, leite weiter.");
            chain.doFilter(request, response);
        }
    }


    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(loginPage);
    }

    /**
     * Check if the HttpSession has the LOGGED_IN_USER attribute.
     */
    private boolean userIsLoggedIn(HttpSession session) {
        boolean sessionHasAttributeUserLoggedIn = session != null && session.getAttribute(ATTRIBUTE_USER_ID) != null;
        return sessionHasAttributeUserLoggedIn;
    }


    public void destroy() {
        // do nothing
    }
}
