package com.ggarr.www.core.config.security.handlers;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.LoginLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginLogEntity log = new LoginLogEntity();
        log.setDefaultEntity(request, response);
        String redirect_url = request.getParameter("redirect_url");
        log.setName(authentication.getName());
        log.setSuccess(true);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        log.setUserPk(userPrincipal.getIdx());
        em.persist(log);

        if (redirect_url != null) {
            response.sendRedirect(redirect_url);
        } else {
            response.sendRedirect("/");
        }
    }
}
