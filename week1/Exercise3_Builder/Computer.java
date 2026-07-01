
class Computer {
    String cpu;
    int ram;
    int storage;

    private Computer(Builder b) {
        this.cpu = b.cpu;
        this.ram = b.ram;
        this.storage = b.storage;
    }

    static class Builder {
        String cpu;
        int ram;
        int storage;

        Builder setCPU(String cpu) { this.cpu = cpu; return this; }
        Builder setRAM(int ram) { this.ram = ram; return this; }
        Builder setStorage(int storage) { this.storage = storage; return this; }

        Computer build() { return new Computer(this); }
    }

    public String toString() {
        return cpu + " " + ram + "GB " + storage + "GB";
    }
}

public class TestBuilder {
    public static void main(String[] args) {
        Computer c = new Computer.Builder()
            .setCPU("i7")
            .setRAM(16)
            .setStorage(512)
            .build();

        System.out.println(c);
    }
}
