package softuni.exam.models.dto;

import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Town;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name="ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDataDto {
    @XmlElement(name="serial-number")
    private String serialNumber;
    @XmlElement(name="price")
    private BigDecimal price;
    @XmlElement(name="take-off")
    private String takeoff;
    @XmlElement(name="from-town")
    private TownNameDto fromTown;
    @XmlElement(name="to-town")
    private TownNameDto toTown;
    @XmlElement(name="passenger")
    private PassengerEmailDto passenger;
    @XmlElement(name="plane")
    private PlaneRegisterNumberDto plane;

    public TicketSeedDataDto() {
    }

    @Size(min=2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public TownNameDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownNameDto fromTown) {
        this.fromTown = fromTown;
    }

    public TownNameDto getToTown() {
        return toTown;
    }

    public void setToTown(TownNameDto toTown) {
        this.toTown = toTown;
    }

    public PassengerEmailDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailDto passenger) {
        this.passenger = passenger;
    }

    public PlaneRegisterNumberDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegisterNumberDto plane) {
        this.plane = plane;
    }
}
