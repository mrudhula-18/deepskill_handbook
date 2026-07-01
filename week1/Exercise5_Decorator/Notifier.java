
interface Notifier {
    void send(String msg);
}

class EmailNotifier implements Notifier {
    public void send(String msg) {
        System.out.println("Email: " + msg);
    }
}

abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappee;
    NotifierDecorator(Notifier n) { this.wrappee = n; }
}

class SMSDecorator extends NotifierDecorator {
    SMSDecorator(Notifier n) { super(n); }
    public void send(String msg) {
        wrappee.send(msg);
        System.out.println("SMS: " + msg);
    }
}

public class TestDecorator {
    public static void main(String[] args) {
        Notifier n = new SMSDecorator(new EmailNotifier());
        n.send("Hello");
    }
}
