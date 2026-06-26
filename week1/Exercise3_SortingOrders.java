class Order{
    int orderId; String customerName; double totalPrice;
    Order(int orderId,String customerName,double totalPrice){
        this.orderId=orderId; this.customerName=customerName; this.totalPrice=totalPrice;
    }
}
public class Exercise3_SortingOrders{
    static void bubbleSort(Order[] orders){
        for(int i=0;i<orders.length-1;i++)
            for(int j=0;j<orders.length-i-1;j++)
                if(orders[j].totalPrice>orders[j+1].totalPrice){
                    Order t=orders[j]; orders[j]=orders[j+1]; orders[j+1]=t;
                }
    }
}