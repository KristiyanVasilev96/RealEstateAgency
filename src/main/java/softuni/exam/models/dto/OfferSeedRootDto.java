package softuni.exam.models.dto;

import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offers")
public class OfferSeedRootDto {

    @XmlElement(name = "offer")
    private List<OfferSeedDto> offers;

    public OfferSeedRootDto() {
    }

    public List<OfferSeedDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferSeedDto> offers) {
        this.offers = offers;
    }
}
