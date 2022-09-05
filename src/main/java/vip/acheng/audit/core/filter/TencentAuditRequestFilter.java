package vip.acheng.audit.core.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TencentAuditRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!((HttpServletRequest) request).getRequestURI().contains("send")) {
            chain.doFilter(request, response);
            return;
        }
        AuditMetaRequestWrapper requestWrapper = new AuditMetaRequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}