package com.adamdubiel.workshop.metrics.api;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class MetricsInterceptor extends HandlerInterceptorAdapter {

    private static final String METRICS_TIMER = "metrics-timer";

    private final MetricRegistry metricRegistry;

    public MetricsInterceptor(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // it's not neccessary to hold whole Timer.Context object in request, we can do with simple millis
        // the upside is also that we can name the metric later, when we know the status code
        request.setAttribute(METRICS_TIMER, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // this makes use of Handler object which points to a Controller method
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            long startTime = (Long) request.getAttribute(METRICS_TIMER);
            long duration = System.currentTimeMillis() - startTime;

            String name =
                    method.getMethod().getDeclaringClass().getSimpleName() +
                            "." + method.getMethod().getName() +
                            "." + response.getStatus();

            metricRegistry.timer(name).update(duration, TimeUnit.MILLISECONDS);
        }
    }
}
