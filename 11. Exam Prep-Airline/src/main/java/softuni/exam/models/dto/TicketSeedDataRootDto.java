package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDataRootDto {

    @XmlElement(name="ticket")
    private List<TicketSeedDataDto> tickets;

    public TicketSeedDataRootDto() {
    }

    public List<TicketSeedDataDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketSeedDataDto> tickets) {
        this.tickets = tickets;
    }
}
