package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("select o from Offer o join Apartment  a on o.apartment.id=a.id " +
            "join Agent  a2 on o.agent.id=a2.id " +
            "join Town  t on a.town.id=t.id " +
            "where a.apartmentType = 'three_rooms' order by a.area desc ,o.price")
    List<Offer> exportTheQuery();


//select * from offers
//join apartments a on offers.apartment_id = a.id
//join agents a2 on offers.agent_id = a2.id
//join towns t on a.town_id = t.id
//where a.apartment_type='three_rooms'
//order by a.area desc,offers.price
}
