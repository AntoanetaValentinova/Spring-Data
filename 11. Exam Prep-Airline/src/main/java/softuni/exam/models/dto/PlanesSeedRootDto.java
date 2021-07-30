package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanesSeedRootDto {

    @XmlElement(name="plane")
    private List<PlaneSeedDataDto> planes;

    public PlanesSeedRootDto() {
    }

    public List<PlaneSeedDataDto> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneSeedDataDto> planes) {
        this.planes = planes;
    }
}
