package pt.ua.tqs;
 
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


 
public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<Book>();

	public Date iso8601Date(int day, String month, int year){
		int month2 = Month.valueOf(month.toUpperCase()).getValue() - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month2);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
 
	@Given("a(nother) book with the title {string}, written by {string}, published in {int} {word} {int}")
	public void addNewBook(final String title, final String author, final int day, final String month, final int year) {
		Date published = iso8601Date(day, month, year);
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
 
	@When("the customer searches for books published between {int} and {int}")
	public void setSearchParameters( final int yearFrom, final int yearTo) {
		Date from = iso8601Date(1, "January", yearFrom);
		Date to = iso8601Date(1, "January", yearTo);
		result = library.findBooks(from, to);
	}

	@When("the customer searchs for books from {string}")
	public void setSearchAuthor(final String author){
		result = library.searchByAuthor(author);
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
        assertEquals(result.size(), booksFound);
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
        assertEquals(result.get(position - 1).getTitle(), title);
	}
}
