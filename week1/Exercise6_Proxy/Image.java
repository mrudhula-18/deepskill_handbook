
interface Image {
    void display();
}

class RealImage implements Image {
    String file;
    RealImage(String file) {
        this.file = file;
        load();
    }
    void load() {
        System.out.println("Loading " + file);
    }
    public void display() {
        System.out.println("Displaying " + file);
    }
}

class ProxyImage implements Image {
    RealImage real;
    String file;

    ProxyImage(String file) {
        this.file = file;
    }

    public void display() {
        if (real == null) real = new RealImage(file);
        real.display();
    }
}

public class TestProxy {
    public static void main(String[] args) {
        Image img = new ProxyImage("pic.jpg");
        img.display();
        img.display();
    }
}
