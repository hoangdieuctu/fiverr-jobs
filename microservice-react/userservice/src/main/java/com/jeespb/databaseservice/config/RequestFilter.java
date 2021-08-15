package com.jeespb.databaseservice.config;

import com.jeespb.databaseservice.model.Access;
import com.jeespb.databaseservice.repository.AccessRepository;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
public class RequestFilter implements Filter {

    private final AccessRepository accessRepository;

    public RequestFilter(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String host = request.getRemoteHost();
        String time = new Date().toString();

        Access access = new Access();
        access.setId(UUID.randomUUID().toString());
        access.setType("filter");
        access.setHost(host);
        access.setTime(time);
        accessRepository.save(access);

        chain.doFilter(request, response);
    }
}
