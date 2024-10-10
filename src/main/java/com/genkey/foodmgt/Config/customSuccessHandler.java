package com.genkey.foodmgt.Config;

import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.genkey.foodmgt.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class customSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserDAO userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object pricipal = auth.getPrincipal();
        String user = "";
        if (pricipal instanceof customUserDetails) {
            user = ((customUserDetails) pricipal).getUsername();
            System.out.println(user);
        }

        Users user1 = userService.GetByUsername(user).orElse(null);

        String redirectUrl = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            System.out.println("role " + grantedAuthority.getAuthority());
            if (grantedAuthority.getAuthority().equals("USER") && !user1.isFirstTime()) {
                redirectUrl = "/";
                break;

            } else if (grantedAuthority.getAuthority().equals("USER") && user1.isFirstTime()) {
                redirectUrl = "/Password";
                break;

            } else if (grantedAuthority.getAuthority().equals("INTERN") && !user1.isFirstTime()) {
                redirectUrl = "/";
                break;

            } else if (grantedAuthority.getAuthority().equals("SECURITY") && !user1.isFirstTime()) {
                redirectUrl = "/";
                break;

            } else if (grantedAuthority.getAuthority().equals("SECURITY") && user1.isFirstTime()) {
                redirectUrl = "/Password";
                break;

            } else if (grantedAuthority.getAuthority().equals("INTERN") && user1.isFirstTime()) {
                redirectUrl = "/Password";
                break;

            } else if (grantedAuthority.getAuthority().equals("ADMIN") && user1.isFirstTime()) {
                redirectUrl = "/Password";
                break;

            } else if (grantedAuthority.getAuthority().equals("ADMIN") && !user1.isFirstTime()) {
                redirectUrl = "/admin/admindashboard";
                break;

            } else if (grantedAuthority.getAuthority().equals("NSS") && user1.isFirstTime()) {
                redirectUrl = "/Password";
                break;

            } else if (grantedAuthority.getAuthority().equals("NSS") && !user1.isFirstTime()) {
                redirectUrl = "/";
                break;
            }
        }
        System.out.println("redirectUrl " + redirectUrl);
        if (redirectUrl == null) {
            throw new IllegalStateException();
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

    }

}
