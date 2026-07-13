package com.dn5.week1.designpatterns.solid.isp;

/** A human can both work and eat, so it implements both focused interfaces. */
public class HumanWorker implements Workable, Eatable {

    private boolean working;
    private boolean eating;

    @Override
    public void work() {
        working = true;
        System.out.println("Human is working.");
    }

    @Override
    public void eat() {
        eating = true;
        System.out.println("Human is eating.");
    }

    public boolean isWorking() {
        return working;
    }

    public boolean isEating() {
        return eating;
    }
}
