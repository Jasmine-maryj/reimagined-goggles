package com.dev.userservice.filters;

import com.dev.userservice.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthFilters extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpServletRequest.getHeader("Authorization");

        if(authHeader != null){
            String[] authHeaderArr = authHeader.split("Bearer");

            if(authHeaderArr.length > 1 && authHeaderArr[1] != null){  //authHeader[1] contains token
                String token = authHeaderArr[1];
                try{
                    Claims claims = Jwts.parser().setSigningKey(Token.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody();

                    httpServletRequest.setAttribute("id", Integer.parseInt(claims.get("id").toString()));

                }catch (Exception e){
                    httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token");
                    return;
                }
            }else{
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization must contain token");
            }
        }else{
            httpServletResponse.sendError(HttpStatus.FAILED_DEPENDENCY.value(), "Authorization must be provided");
        }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
