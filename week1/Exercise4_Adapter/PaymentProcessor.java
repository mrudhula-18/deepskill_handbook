
interface PaymentProcessor {
    void pay(int amount);
}

class Razorpay {
    void makePayment(int amt) {
        System.out.println("Razorpay paid " + amt);
    }
}

class RazorpayAdapter implements PaymentProcessor {
    Razorpay r = new Razorpay();
    public void pay(int amount) {
        r.makePayment(amount);
    }
}

public class TestAdapter {
    public static void main(String[] args) {
        PaymentProcessor p = new RazorpayAdapter();
        p.pay(1000);
    }
}
