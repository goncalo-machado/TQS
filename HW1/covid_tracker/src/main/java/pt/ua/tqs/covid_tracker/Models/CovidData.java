package pt.ua.tqs.covid_tracker.Models;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CovidData {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country; 
    private long updated;
    private int cases;
    private int todayCases;
    private int deaths;
    private int todayDeaths;
    private int recovered;
    private int todayRecovered;
    private int active;
    private int critical;
    private float casesPerOneMillion;
    private float deathsPerOneMillion;
    private int tests;
    private float testsPerOneMillion;
    private int population;
    private String continent;
    private float oneCasePerPeople;
    private float oneDeathPerPeople;
    private float oneTestPerPeople;
    private float activePerOneMillion;
    private float recoveredPerOneMillion;
    private float criticalPerOneMillion; 
    private long created;
    private int dayOfData; //0 for today, 1 for yesterday, 2 for before yesterday

    public CovidData(){

    }

    public CovidData(String country, long updated, int cases, int todayCases, int deaths, int todayDeaths, int recovered, int todayRecovered, int active, int critical, float casesPerOneMillion, float deathsPerOneMillion, int tests, float testsPerOneMillion, int population, String continent, float oneCasePerPeople, float oneDeathPerPeople, float oneTestPerPeople, float activePerOneMillion, float recoveredPerOneMillion, float criticalPerOneMillion, int dayOfData) {
        this.country = country;
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.critical = critical;
        this.casesPerOneMillion = casesPerOneMillion;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.tests = tests;
        this.testsPerOneMillion = testsPerOneMillion;
        this.population = population;
        this.continent = continent;
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneDeathPerPeople = oneDeathPerPeople;
        this.oneTestPerPeople = oneTestPerPeople;
        this.activePerOneMillion = activePerOneMillion;
        this.recoveredPerOneMillion = recoveredPerOneMillion;
        this.criticalPerOneMillion = criticalPerOneMillion;
        this.created = System.currentTimeMillis();
        this.dayOfData = dayOfData;
    }
    

    public Long getId() {
        return this.id;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getUpdated() {
        return this.updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public int getCases() {
        return this.cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getTodayCases() {
        return this.todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTodayDeaths() {
        return this.todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getRecovered() {
        return this.recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getTodayRecovered() {
        return this.todayRecovered;
    }

    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCritical() {
        return this.critical;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public float getCasesPerOneMillion() {
        return this.casesPerOneMillion;
    }

    public void setCasesPerOneMillion(float casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public float getDeathsPerOneMillion() {
        return this.deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(float deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public int getTests() {
        return this.tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public float getTestsPerOneMillion() {
        return this.testsPerOneMillion;
    }

    public void setTestsPerOneMillion(float testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getContinent() {
        return this.continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public float getOneCasePerPeople() {
        return this.oneCasePerPeople;
    }

    public void setOneCasePerPeople(float oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public float getOneDeathPerPeople() {
        return this.oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(float oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public float getOneTestPerPeople() {
        return this.oneTestPerPeople;
    }

    public void setOneTestPerPeople(float oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public float getActivePerOneMillion() {
        return this.activePerOneMillion;
    }

    public void setActivePerOneMillion(float activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public float getRecoveredPerOneMillion() {
        return this.recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(float recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public float getCriticalPerOneMillion() {
        return this.criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(float criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getDayOfData() {
        return this.dayOfData;
    }

    public void setDayOfData(int dayOfData) {
        this.dayOfData = dayOfData;
    }

    public boolean isEqual(CovidData covidData) {
        return Objects.equals(country, covidData.country) && cases == covidData.cases && todayCases == covidData.todayCases && deaths == covidData.deaths && todayDeaths == covidData.todayDeaths && recovered == covidData.recovered && todayRecovered == covidData.todayRecovered && active == covidData.active && critical == covidData.critical && casesPerOneMillion == covidData.casesPerOneMillion && deathsPerOneMillion == covidData.deathsPerOneMillion && tests == covidData.tests && testsPerOneMillion == covidData.testsPerOneMillion && population == covidData.population && Objects.equals(continent, covidData.continent) && oneCasePerPeople == covidData.oneCasePerPeople && oneDeathPerPeople == covidData.oneDeathPerPeople && oneTestPerPeople == covidData.oneTestPerPeople && activePerOneMillion == covidData.activePerOneMillion && recoveredPerOneMillion == covidData.recoveredPerOneMillion && criticalPerOneMillion == covidData.criticalPerOneMillion && dayOfData == covidData.dayOfData;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", country='" + getCountry() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", cases='" + getCases() + "'" +
            ", todayCases='" + getTodayCases() + "'" +
            ", deaths='" + getDeaths() + "'" +
            ", todayDeaths='" + getTodayDeaths() + "'" +
            ", recovered='" + getRecovered() + "'" +
            ", todayRecovered='" + getTodayRecovered() + "'" +
            ", active='" + getActive() + "'" +
            ", critical='" + getCritical() + "'" +
            ", casesPerOneMillion='" + getCasesPerOneMillion() + "'" +
            ", deathsPerOneMillion='" + getDeathsPerOneMillion() + "'" +
            ", tests='" + getTests() + "'" +
            ", testsPerOneMillion='" + getTestsPerOneMillion() + "'" +
            ", population='" + getPopulation() + "'" +
            ", continent='" + getContinent() + "'" +
            ", oneCasePerPeople='" + getOneCasePerPeople() + "'" +
            ", oneDeathPerPeople='" + getOneDeathPerPeople() + "'" +
            ", oneTestPerPeople='" + getOneTestPerPeople() + "'" +
            ", activePerOneMillion='" + getActivePerOneMillion() + "'" +
            ", recoveredPerOneMillion='" + getRecoveredPerOneMillion() + "'" +
            ", criticalPerOneMillion='" + getCriticalPerOneMillion() + "'" +
            ", created='" + getCreated() + "'" +
            ", dayOfData='" + getDayOfData() + "'" +
            "}";
    }

}
