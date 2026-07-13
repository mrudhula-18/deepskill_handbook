package com.dn5.week1.designpatterns;

import com.dn5.week1.designpatterns.behavioral.command.Command;
import com.dn5.week1.designpatterns.behavioral.command.Light;
import com.dn5.week1.designpatterns.behavioral.command.LightOffCommand;
import com.dn5.week1.designpatterns.behavioral.command.LightOnCommand;
import com.dn5.week1.designpatterns.behavioral.command.RemoteControl;
import com.dn5.week1.designpatterns.behavioral.observer.Investor;
import com.dn5.week1.designpatterns.behavioral.observer.StockTicker;
import com.dn5.week1.designpatterns.behavioral.strategy.CreditCardPayment;
import com.dn5.week1.designpatterns.behavioral.strategy.PaymentContext;
import com.dn5.week1.designpatterns.behavioral.strategy.PaypalPayment;
import com.dn5.week1.designpatterns.creational.builder.Pizza;
import com.dn5.week1.designpatterns.creational.factory.ShapeFactory;
import com.dn5.week1.designpatterns.creational.factory.ShapeType;
import com.dn5.week1.designpatterns.creational.singleton.ConfigurationManager;
import com.dn5.week1.designpatterns.mvc.StudentController;
import com.dn5.week1.designpatterns.mvc.StudentModel;
import com.dn5.week1.designpatterns.mvc.StudentView;
import com.dn5.week1.designpatterns.solid.dip.EmailSender;
import com.dn5.week1.designpatterns.solid.dip.NotificationService;
import com.dn5.week1.designpatterns.solid.isp.HumanWorker;
import com.dn5.week1.designpatterns.solid.isp.RobotWorker;
import com.dn5.week1.designpatterns.solid.lsp.Rectangle;
import com.dn5.week1.designpatterns.solid.lsp.Square;
import com.dn5.week1.designpatterns.solid.ocp.DiscountCalculator;
import com.dn5.week1.designpatterns.solid.ocp.RegularCustomerDiscount;
import com.dn5.week1.designpatterns.solid.ocp.VipCustomerDiscount;
import com.dn5.week1.designpatterns.solid.srp.GoodInvoice;
import com.dn5.week1.designpatterns.solid.srp.InvoicePrinter;
import com.dn5.week1.designpatterns.solid.srp.InvoiceRepository;
import com.dn5.week1.designpatterns.structural.adapter.XmlDataProvider;
import com.dn5.week1.designpatterns.structural.adapter.XmlToJsonAdapter;
import com.dn5.week1.designpatterns.structural.decorator.Coffee;
import com.dn5.week1.designpatterns.structural.decorator.MilkDecorator;
import com.dn5.week1.designpatterns.structural.decorator.SimpleCoffee;
import com.dn5.week1.designpatterns.structural.decorator.SugarDecorator;
import com.dn5.week1.designpatterns.structural.proxy.ProxyImage;

/**
 * Console walkthrough of every SOLID principle and GoF pattern implemented
 * in this module. Run with:
 * {@code mvn -q compile exec:java -Dexec.mainClass=com.dn5.week1.designpatterns.Main}
 * or {@code java -cp target/classes com.dn5.week1.designpatterns.Main}.
 */
public class Main {

    public static void main(String[] args) {
        header("SOLID - Single Responsibility Principle");
        GoodInvoice invoice = new GoodInvoice("Alice", 25.0, 4);
        new InvoicePrinter().printInvoice(invoice);
        new InvoiceRepository().save(invoice);

        header("SOLID - Open/Closed Principle");
        System.out.println("Regular price: " + new DiscountCalculator(new RegularCustomerDiscount()).computeFinalPrice(100));
        System.out.println("VIP price: " + new DiscountCalculator(new VipCustomerDiscount()).computeFinalPrice(100));

        header("SOLID - Liskov Substitution Principle");
        System.out.println("Rectangle area: " + new Rectangle(5, 10).area());
        System.out.println("Square area: " + new Square(5).area());

        header("SOLID - Interface Segregation Principle");
        HumanWorker human = new HumanWorker();
        human.work();
        human.eat();
        RobotWorker robot = new RobotWorker();
        robot.work();

        header("SOLID - Dependency Inversion Principle");
        new NotificationService(new EmailSender()).notify("bob@example.com", "Hello Bob");

        header("Creational - Singleton");
        ConfigurationManager.getInstance().setProperty("app.env", "prod");
        System.out.println("Config app.env = " + ConfigurationManager.getInstance().getProperty("app.env"));

        header("Creational - Factory Method");
        ShapeFactory shapeFactory = new ShapeFactory();
        System.out.println(shapeFactory.createShape(ShapeType.CIRCLE, 3.0).describe());
        System.out.println(shapeFactory.createShape(ShapeType.TRIANGLE, 4.0, 5.0).describe());

        header("Creational - Builder");
        Pizza pizza = new Pizza.Builder().size("large").addTopping("Pepperoni").addTopping("Olives").cheese(true).build();
        System.out.println(pizza);

        header("Structural - Adapter");
        System.out.println(new XmlToJsonAdapter(new XmlDataProvider()).getJsonData());

        header("Structural - Decorator");
        Coffee coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println(coffee.description() + " costs " + coffee.cost());

        header("Structural - Proxy");
        ProxyImage image = new ProxyImage("photo.png");
        System.out.println("Loaded before display? " + image.isRealImageLoaded());
        image.display();
        image.display();

        header("Behavioral - Observer");
        StockTicker ticker = new StockTicker();
        Investor investor = new Investor("Carol");
        ticker.registerObserver(investor);
        ticker.setPrice(101.5);

        header("Behavioral - Strategy");
        PaymentContext paymentContext = new PaymentContext(new CreditCardPayment("4111111111111234"));
        System.out.println(paymentContext.executePayment(50));
        paymentContext.setStrategy(new PaypalPayment("carol@example.com"));
        System.out.println(paymentContext.executePayment(75));

        header("Behavioral - Command");
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        Command onCommand = new LightOnCommand(light);
        Command offCommand = new LightOffCommand(light);
        remote.setCommand(onCommand);
        remote.pressButton();
        remote.setCommand(offCommand);
        remote.pressButton();

        header("MVC demo");
        StudentController controller = new StudentController(new StudentModel(), new StudentView());
        controller.setStudentName("Dave");
        controller.setStudentGrade("A");
        controller.updateView();
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
