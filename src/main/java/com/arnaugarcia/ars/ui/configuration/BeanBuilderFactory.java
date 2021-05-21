package com.arnaugarcia.ars.ui.configuration;

import javafx.util.Builder;
import javafx.util.BuilderFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanBuilderFactory implements BuilderFactory {

    private final ApplicationContext context;

    public BeanBuilderFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        try {
            Object bean = this.context.getBean(type);
            if (bean.getClass().isAssignableFrom(type)) {
                return (Builder) () -> bean;
            } else {
                return null;
            }
        } catch (BeansException e) {
            return null;
        }
    }
}
