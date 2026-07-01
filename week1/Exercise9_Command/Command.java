
interface Command {
    void execute();
}

class Light {
    void on() { System.out.println("Light ON"); }
    void off() { System.out.println("Light OFF"); }
}

class LightOn implements Command {
    Light l;
    LightOn(Light l) { this.l = l; }
    public void execute() { l.on(); }
}

class RemoteControl {
    Command c;
    void setCommand(Command c) { this.c = c; }
    void press() { c.execute(); }
}

public class TestCommand {
    public static void main(String[] args) {
        Light l = new Light();
        RemoteControl r = new RemoteControl();

        r.setCommand(new LightOn(l));
        r.press();
    }
}
