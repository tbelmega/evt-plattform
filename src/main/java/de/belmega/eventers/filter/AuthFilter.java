package de.belmega.eventers.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Use this filter for all requests that contain the .xhtml file extension.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthFilter implements Filter {

    public static final String JAVAX_FACES_RESOURCE_URL = "javax.faces.resource";
    public static final String ATTRIBUTE_USER_ID = "userId";

    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    /**
     * This method is executed for each HttpRequest that matches the @WebFilter(urlPatterns = {"*.xhtml"}).
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        if (isPrivatePage(url) && !userIsLoggedIn(session)) {
            System.out.println("Kein User eingeloggt, leite weiter zu Login");
            redirectToLogin((HttpServletResponse) response);
        } else {
            System.out.println("User ist berechtigt, diese Seite zu sehen.");
            chain.doFilter(request, response);
        }
    }


    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("register-provider.xhtml");
    }

    /**
     * Check if the HttpSession has the USER_IS_LOGGED_IN attribute.
     */
    private boolean userIsLoggedIn(HttpSession session) {
        boolean sessionHasAttributeUserLoggedIn = session != null && session.getAttribute(ATTRIBUTE_USER_ID) != null;
        return sessionHasAttributeUserLoggedIn;
    }

    /**
     * Check if the requested page is registration or a resource.
     */
    private boolean isPrivatePage(String url) {
        return !(url.contains("register") || url.contains(JAVAX_FACES_RESOURCE_URL));
    }


    public void destroy() {
        // do nothing
    }
}
