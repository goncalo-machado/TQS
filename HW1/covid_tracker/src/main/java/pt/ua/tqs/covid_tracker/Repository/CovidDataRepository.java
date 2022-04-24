package pt.ua.tqs.covid_tracker.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.covid_tracker.Models.CovidData;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData, Long> {
    List<CovidData> findAllByCountry(String country);
    Optional<CovidData> findByCountryAndDayOfData(String country, int dayOfData);
    List<CovidData> findAllByCreatedLessThanEqual(Date created);
}
