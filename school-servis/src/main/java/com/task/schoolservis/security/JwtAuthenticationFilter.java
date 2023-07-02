package com.task.schoolservis.security;

import com.task.schoolservis.service.imp.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getJWTFromToken( request );
        if (StringUtils.hasText( token ) && this.jwtTokenProvider.validateToken( token )) {
            String username = this.jwtTokenProvider.getUsernameFromJWT( token );
            UserDetails userDetails = this.userDetailServiceImp.loadUserByUsername( username );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );

            SecurityContextHolder.getContext().setAuthentication( authenticationToken );
        }

        filterChain.doFilter( request, response );
    }

    private String getJWTFromToken(HttpServletRequest request) {
        String bearerToken = request.getHeader( "Authorization" );
        if (StringUtils.hasText( bearerToken ) && bearerToken.startsWith( "Bearer " )) {
            return bearerToken.substring( 7 );
        }

        return null;
    }
}
