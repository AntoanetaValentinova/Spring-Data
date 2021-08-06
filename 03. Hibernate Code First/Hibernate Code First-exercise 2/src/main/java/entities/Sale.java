package entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="sales")
public class Sale extends BaseEntity{
    private Product product;
    private Customer customer;
    private StoreLocation storeLocation;
    private LocalDate date;

    public Sale() {
    }


    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Column
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
