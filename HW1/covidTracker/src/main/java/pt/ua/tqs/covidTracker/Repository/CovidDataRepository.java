package pt.ua.tqs.covidtracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.covidtracker.Models.CovidData;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData, Long> {
    CovidData findByCountryAndDayOfData(String country, int dayOfData);
    List<CovidData> findAllByCreatedLessThanEqual(long created);
}
