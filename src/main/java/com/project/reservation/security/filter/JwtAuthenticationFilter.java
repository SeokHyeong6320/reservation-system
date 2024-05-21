package com.project.reservation.security.filter;

import com.project.reservation.security.util.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.project.reservation.security.constant.SecurityConst.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String jwtToken = getToken(request);

        if(StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {

            Authentication authentication =
                    tokenProvider.getAuthentication(jwtToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * HttpServletRequest 에서 token 가져오는 메서드
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(TOKEN_HEADER);

        // header가 Bearer 로 시작하는지 확인
        if (!ObjectUtils.isEmpty(header) && header.startsWith(TOKEN_PREFIX)) {
            // token만 잘라서 반환
            return header.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
