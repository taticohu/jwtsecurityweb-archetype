package indi.huhy.archetypes.jwtsecurityweb.config.security;

import indi.huhy.archetypes.jwtsecurityweb.entity.response.BaseResult;
import indi.huhy.archetypes.jwtsecurityweb.util.JwtTokenUtil;
import indi.huhy.archetypes.jwtsecurityweb.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    public static final String AUTH_HEADER = "Authentication";

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(AUTH_HEADER);
        if (StringUtils.isBlank(token) || !JwtTokenUtil.verifyToken(token)) {
            ResponseUtil.out(response, HttpStatus.UNAUTHORIZED, new BaseResult<>(-1, "Authentication failed"));
            return;
        }
        String[] roles = JwtTokenUtil.getRolesByToken(token);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.length);
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    @Override
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            ResponseUtil.out(response, HttpStatus.UNAUTHORIZED, new BaseResult<>(-1, "Authentication failed"));
        };
    }
}
