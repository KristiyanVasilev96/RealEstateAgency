package softuni.exam.models.dto;

import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Town;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentSeedDto {
    @XmlElement(name = "apartmentType")
    private ApartmentType apartmentType;
    @XmlElement(name = "area")
    private double area;
    @XmlElement(name = "town")
    private String town;

    public ApartmentSeedDto() {
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    @Min(40)
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
