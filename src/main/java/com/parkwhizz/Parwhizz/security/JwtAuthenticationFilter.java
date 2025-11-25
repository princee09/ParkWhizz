package com.parkwhizz.Parwhizz.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestToken=request.getHeader("Authorization");

        logger.info("Header: {}",requestToken);
        String username = null;
        String token = null;

        if (requestToken!=null && requestToken.startsWith("Bearer")){
                token =  requestToken.substring(7);
                try {
                    username = this.jwtTokenHelper.getUsernameFromToken(token);
                }
                catch (IllegalArgumentException exception){
                   logger.info("IIlegal Argument while fetchig the user name");
                    exception.printStackTrace();
                }
                catch (ExpiredJwtException exception){
                    logger.info("Given JWT token is expire");
                    exception.printStackTrace();
                }catch (MalformedJwtException exception){
                    exception.printStackTrace();
                }

        }else {
            System.out.println("Jwt token not valid");
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(token,userDetails,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
               logger.info("validation fails");
            }
        }else {
           logger.info(" user name is null and context is not null");
        }
        filterChain.doFilter(request,response);
    }
}
