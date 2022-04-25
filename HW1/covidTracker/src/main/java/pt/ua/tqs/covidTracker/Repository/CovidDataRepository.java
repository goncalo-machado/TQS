package pt.ua.tqs.covidTracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.covidTracker.Models.CovidData;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData, Long> {
    CovidData findByCountryAndDayOfData(String country, int dayOfData);
    List<CovidData> findAllByCreatedLessThanEqual(long created);
}
