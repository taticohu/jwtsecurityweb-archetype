package indi.huhy.archetypes.jwtsecurityweb.config.security;

import indi.huhy.archetypes.jwtsecurityweb.entity.response.BaseResult;
import indi.huhy.archetypes.jwtsecurityweb.util.JwtTokenUtil;
import indi.huhy.archetypes.jwtsecurityweb.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException {
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                request.getParameter("username"), request.getParameter("password")));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String[] roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        String token = JwtTokenUtil.createToken(user.getUsername(), roles);
        ResponseUtil.out(response, new BaseResult<>(0, "success", token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, HttpStatus.UNAUTHORIZED, new BaseResult<>(-1, "failure"));
    }
}
