package com.dn5.week1.designpatterns.solid.isp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IspTest {

    @Test
    void humanWorkerImplementsBothWorkableAndEatable() {
        HumanWorker human = new HumanWorker();
        assertTrue(human instanceof Workable);
        assertTrue(human instanceof Eatable);

        human.work();
        human.eat();

        assertTrue(human.isWorking());
        assertTrue(human.isEating());
    }

    @Test
    void robotWorkerOnlyImplementsWorkable() {
        RobotWorker robot = new RobotWorker();
        assertTrue(robot instanceof Workable);
        assertFalse(robot instanceof Eatable);

        robot.work();
        assertTrue(robot.isWorking());
    }
}
