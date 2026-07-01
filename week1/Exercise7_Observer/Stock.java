
import java.util.*;

interface Observer {
    void update(double price);
}

class MobileApp implements Observer {
    public void update(double price) {
        System.out.println("Mobile: " + price);
    }
}

class WebApp implements Observer {
    public void update(double price) {
        System.out.println("Web: " + price);
    }
}

interface Stock {
    void register(Observer o);
    void deregister(Observer o);
    void notifyObservers();
}

class StockMarket implements Stock {
    List<Observer> list = new ArrayList<>();
    double price;

    public void register(Observer o) { list.add(o); }
    public void deregister(Observer o) { list.remove(o); }

    public void setPrice(double p) {
        this.price = p;
        notifyObservers();
    }

    public void notifyObservers() {
        for (Observer o : list) o.update(price);
    }
}

public class TestObserver {
    public static void main(String[] args) {
        StockMarket sm = new StockMarket();
        sm.register(new MobileApp());
        sm.register(new WebApp());

        sm.setPrice(100);
    }
}
