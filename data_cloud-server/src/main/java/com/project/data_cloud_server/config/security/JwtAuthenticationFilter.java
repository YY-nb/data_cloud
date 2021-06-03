package com.project.data_cloud_server.config.security;

import com.project.data_cloud_server.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDetailsService userDetailsService;
    @Value("${jwt.tokenHeader}")
    private String header;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt=request.getHeader(header);
        if(jwt!=null){
            String userNameFromToken = jwtTokenUtil.getUserNameFromToken(jwt);
            //有token但是未登录
            if(userNameFromToken!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails= userDetailsService.loadUserByUsername(userNameFromToken);
                //验证token是否有效,重新设置用户对象
                if (jwtTokenUtil.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else{
                    throw new JwtException("token 异常，未登录");
                }
            }


        }
        chain.doFilter(request,response);
    }
}
