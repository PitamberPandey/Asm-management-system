package com.amsmanagament.system.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtValidator extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtValidator(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // Skip JWT validation for public endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/signup") || path.startsWith("/auth/signin")
                || path.startsWith("/auth/forget-password/sendotp");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            try {
                String phoneNumber = jwtProvider.getNumberFromToken(jwtToken);
                String authorities = jwtProvider.getRolesFromToken(jwtToken);

                List<GrantedAuthority> authList = AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(phoneNumber, null, authList);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
