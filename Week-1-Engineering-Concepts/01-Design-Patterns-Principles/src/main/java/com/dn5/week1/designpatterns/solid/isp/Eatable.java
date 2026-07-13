package com.dn5.week1.designpatterns.solid.isp;

/** ISP: a small, focused interface - kept separate from Workable so robots aren't forced to "eat". */
public interface Eatable {
    void eat();
}
