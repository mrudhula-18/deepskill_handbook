package com.dn5.week2.springcore;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Boots the XML-based Spring context ({@code beans.xml}) and starts the
 * XML-wired {@code xmlCar} bean, which uses the diesel engine.
 */
public class MainXmlDemo {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("beans.xml")) {
            Car car = (Car) context.getBean("xmlCar");
            String result = car.start();
            System.out.println("Result: " + result);
        }
    }
}
