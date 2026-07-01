
interface Document {
    void open();
}

class WordDocument implements Document {
    public void open() { System.out.println("Word opened"); }
}

class PdfDocument implements Document {
    public void open() { System.out.println("PDF opened"); }
}

abstract class DocumentFactory {
    abstract Document createDocument();
}

class WordFactory extends DocumentFactory {
    Document createDocument() { return new WordDocument(); }
}

class PdfFactory extends DocumentFactory {
    Document createDocument() { return new PdfDocument(); }
}

public class TestFactory {
    public static void main(String[] args) {
        DocumentFactory f = new WordFactory();
        f.createDocument().open();

        f = new PdfFactory();
        f.createDocument().open();
    }
}
