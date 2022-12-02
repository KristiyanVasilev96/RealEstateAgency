package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {

    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "publishedOn")
    private String publishedOn;
    @XmlElement(name = "agent")
    @Expose
    private AgentSeedDto agent;
    @XmlElement(name = "apartment")
    private ApartmentIdDto apartment;

    public OfferSeedDto() {
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    @NotNull
    public AgentSeedDto getAgent() {
        return agent;
    }

    public void setAgent(AgentSeedDto agent) {
        this.agent = agent;
    }

    @NotNull
    public ApartmentIdDto getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentIdDto apartment) {
        this.apartment = apartment;
    }
}
