class ProductSearch{
    int productId; String productName; String category;
    ProductSearch(int productId,String productName,String category){
        this.productId=productId; this.productName=productName; this.category=category;
    }
}
public class Exercise2_SearchFunction{
    static int linearSearch(ProductSearch[] products,String name){
        for(int i=0;i<products.length;i++) if(products[i].productName.equals(name)) return i;
        return -1;
    }
    static int binarySearch(ProductSearch[] products,String name){
        int low=0,high=products.length-1;
        while(low<=high){
            int mid=(low+high)/2;
            int cmp=products[mid].productName.compareTo(name);
            if(cmp==0) return mid;
            else if(cmp<0) low=mid+1;
            else high=mid-1;
        }
        return -1;
    }
}