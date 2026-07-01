
interface PaymentStrategy {
    void pay(int amt);
}

class CreditCard implements PaymentStrategy {
    public void pay(int amt) {
        System.out.println("Credit Card: " + amt);
    }
}

class PayPal implements PaymentStrategy {
    public void pay(int amt) {
        System.out.println("PayPal: " + amt);
    }
}

class PaymentContext {
    PaymentStrategy strategy;
    void setStrategy(PaymentStrategy s) { strategy = s; }
    void pay(int amt) { strategy.pay(amt); }
}

public class TestStrategy {
    public static void main(String[] args) {
        PaymentContext c = new PaymentContext();
        c.setStrategy(new CreditCard());
        c.pay(500);

        c.setStrategy(new PayPal());
        c.pay(300);
    }
}
