package com.dn5.week1.designpatterns.solid.isp;

/**
 * ISP: a robot only implements Workable - it is never forced to provide a
 * meaningless eat() implementation the way a single "fat" interface would
 * have required.
 */
public class RobotWorker implements Workable {

    private boolean working;

    @Override
    public void work() {
        working = true;
        System.out.println("Robot is working.");
    }

    public boolean isWorking() {
        return working;
    }
}
