package com.dn5.week2.tdd;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit 5 test suite that runs every test in the {@code com.dn5.week2.tdd}
 * package in a single execution. Run with:
 * {@code mvn test -Dtest=AllTestsSuite}
 */
@Suite
@SelectPackages("com.dn5.week2.tdd")
public class AllTestsSuite {
}
