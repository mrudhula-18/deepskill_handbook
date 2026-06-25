import java.util.HashMap;
class Product {
    int productId; String productName; int quantity; double price;
    Product(int productId,String productName,int quantity,double price){
        this.productId=productId; this.productName=productName; this.quantity=quantity; this.price=price;
    }
}
public class Exercise1_InventoryManagement {
    static HashMap<Integer, Product> inventory = new HashMap<>();
    static void addProduct(Product p){ inventory.put(p.productId,p); }
    static void updateProduct(int id,int quantity){ if(inventory.containsKey(id)) inventory.get(id).quantity=quantity; }
    static void deleteProduct(int id){ inventory.remove(id); }
}