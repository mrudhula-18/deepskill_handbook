
interface CustomerRepository {
    String findCustomerById(int id);
}

class CustomerRepositoryImpl implements CustomerRepository {
    public String findCustomerById(int id) {
        return "Customer " + id;
    }
}

class CustomerService {
    CustomerRepository repo;

    CustomerService(CustomerRepository r) {
        this.repo = r;
    }

    void showCustomer(int id) {
        System.out.println(repo.findCustomerById(id));
    }
}

public class TestDI {
    public static void main(String[] args) {
        CustomerRepository repo = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repo);

        service.showCustomer(101);
    }
}
