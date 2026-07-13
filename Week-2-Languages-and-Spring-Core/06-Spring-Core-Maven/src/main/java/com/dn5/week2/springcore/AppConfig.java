package com.dn5.week2.springcore;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Annotation-based Spring configuration: component scanning plus AspectJ
 * auto-proxying so {@link LoggingAspect} can wrap {@link Car}.
 */
@Configuration
@ComponentScan("com.dn5.week2.springcore")
@EnableAspectJAutoProxy
public class AppConfig {
}
