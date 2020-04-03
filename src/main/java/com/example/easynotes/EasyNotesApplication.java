package com.example.easynotes;

import com.example.easynotes.model.Address;
import com.example.easynotes.model.Employee;
import com.example.easynotes.model.EmployeeIdentity;
import com.example.easynotes.model.Gender;
import com.example.easynotes.model.Movie;
import com.example.easynotes.model.Name;
import com.example.easynotes.model.Person;
import com.example.easynotes.model.Post;
import com.example.easynotes.model.Tag;
import com.example.easynotes.model.User;
import com.example.easynotes.model.UserProfile;
import com.example.easynotes.repository.EmployeeRepository;
import com.example.easynotes.repository.MovieRepository;
import com.example.easynotes.repository.PersonRepository;
import com.example.easynotes.repository.TagRepository;
import com.example.easynotes.repository.UserProfileRepository;
import com.example.easynotes.repository.UserRepository;
import com.example.easynotes.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Calendar;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class EasyNotesApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(EasyNotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToOneExample();
		manyToManyExample();
		compositePrimaryKeyExample();
		embeddableExample();
	}

	private void embeddableExample() {
		// Cleanup the users table
		personRepository.deleteAllInBatch();

		// Insert a new user in the database
		Name name = new Name("Rajeev", "Kumar", "Singh");
		Address address = new Address("747", "Golf View Road", "Bangalore", "Karnataka", "India", "560008");
		Person user = new Person(name, "rajeev@callicoder.com", address);

		personRepository.save(user);

		Optional<Person> personFound = personRepository.findByNameLastName("Singh").stream().findFirst();
		System.out.println(personFound.get().getAddress().getAddressLine2());
	}

	private void compositePrimaryKeyExample() {
		// ==== @Composite Primary Key example: with Employee and EmployeeIdentity ===================================

		// Clean up the tables
		employeeRepository.deleteAllInBatch();

		// Insert a new Employee in the database
		Employee employee = new Employee(new EmployeeIdentity("E-123", "D-456"),
													"Jonathan",
													"jonathan@email.com",
											 "+3161234567");
		employeeRepository.save(employee);

		// Retrieve an employee using the composite primary key
		System.out.println("A:" + employeeRepository.findById(new EmployeeIdentity("E-123", "D-456")));
		System.out.println("Printed A");

		// Retrieve all employees from a particular company
		System.out.println("B" + employeeRepository.findByEmployeeIdentityCompanyId("D-456"));
		System.out.println("printed B");

		System.out.println("C" + employeeService.findEmployee("E-123" , "d-456"));
		System.out.println("printed C");


	}


	private void manyToManyExample() {
		// ==== @ManyToMany example: with Movie and Tag ===================================

		// Cleanup the tables
		movieRepository.deleteAllInBatch();
		tagRepository.deleteAllInBatch();

		// Create a Movie
		Movie movie = new Movie("Hibernate Many to Many Example with Spring Boot");

		// Create two tags
		Tag tag1 = new Tag("Spring Boot");
		Tag tag2 = new Tag("Hibernate");

		// Add tag reference to the movie
		movie.getTags().add(tag1);
		movie.getTags().add(tag2);

		// Add movie reference to the tags
		tag1.getMovies().add(movie);
		tag2.getMovies().add(movie);

		// Save parent reference (which wil automatically save child reference)
		movieRepository.save(movie);
	}

	private void oneToOneExample() {
		// === @OneToOne example: with User and UserProfile =========================================================================

		// Clean up database tables
		userProfileRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

		// Create a User instance
		User user = new User("Jonathan", "Li", "jykhli@gmail.com", "MY_SUPER_PASSWORD");

		// Create UserProfile instance
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.set(1990, 2, 20);
		UserProfile userProfile = new UserProfile("1234567", Gender.MALE, dateOfBirth.getTime(), "Gravenstraat 125",
				"Rotterdam", "Zuid-Holland", "Nederland", "1234AB");

		// Set Child reference(userProfile) in parent entity(user)
		user.setUserProfile(userProfile);

		// Set parent reference(user) in child entity(userProfile)
		userProfile.setUser(user);

		// Save Parent Reference(which will save the child as well)
		userRepository.save(user);
	}
}