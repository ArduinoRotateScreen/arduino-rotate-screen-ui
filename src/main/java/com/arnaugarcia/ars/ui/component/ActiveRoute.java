package com.arnaugarcia.ars.ui.component;

import org.springframework.context.ApplicationEvent;

public class ActiveRoute extends ApplicationEvent {
    private final Class<?> route;

    public ActiveRoute(Class<?> route) {
        super(route);
        this.route = route;
    }

    public Class<?> getRoute() {
        return route;
    }

}
