package com.ggarr.www.core.config.security.handlers;

import com.ggarr.www.entity.LoginLogEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginFailedHandler implements AuthenticationFailureHandler {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        StringBuilder builder = new StringBuilder();
        String redirect_url = request.getParameter("redirect_url");

        builder
                .append(request.getRequestURL().toString())
                .append("?")
                .append("error=")
                .append(exception.getMessage());

        if (redirect_url != null) {
            builder
                    .append("&")
                    .append("redirect_url=")
                    .append(redirect_url);
        }

        LoginLogEntity log = new LoginLogEntity();
        log.setDefaultEntity(request, response);
        log.setSuccess(false);
        em.persist(log);

        response.sendRedirect(builder.toString().replaceAll(" ", "+"));
    }
}
