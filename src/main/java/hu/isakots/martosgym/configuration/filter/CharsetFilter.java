package hu.isakots.martosgym.configuration.filter;

import javax.servlet.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// FYI: In Chrome UTF-8 encoding goes wrong somehow.. it's a workaround for this issue.
public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.name();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Respect the client-specified character encoding
        // (see HTTP specification section 3.4.1)
        if (null == servletResponse.getCharacterEncoding()) {
            servletResponse.setCharacterEncoding(encoding);
        }

        // Set the default response content type and encoding
        servletResponse.setContentType("text/html; charset=UTF-8");
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // intentionally left blank
    }
}