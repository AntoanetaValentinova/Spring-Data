package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="stores_locations")
public class StoreLocation extends BaseEntity{
    private String locationName;
    private Set<Sale> sales;

    public StoreLocation() {
        this.sales=new HashSet<>();
    }

    @OneToMany(mappedBy = "storeLocation")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Column(name = "location_name",nullable = false)
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
