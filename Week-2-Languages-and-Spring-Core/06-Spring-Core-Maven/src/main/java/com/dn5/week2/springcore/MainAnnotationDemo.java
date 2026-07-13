package com.dn5.week2.springcore;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Boots the annotation-driven Spring context ({@link AppConfig}) and starts
 * the {@link Car} bean, whose {@code start()} calls are wrapped by
 * {@link LoggingAspect}.
 */
public class MainAnnotationDemo {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            Car car = context.getBean(Car.class);
            String result = car.start();
            System.out.println("Result: " + result);
        }
    }
}
