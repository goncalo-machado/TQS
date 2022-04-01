## a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

__E_EmployeeRestControllerTemplateIT.java__

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

__A_EmployeeRepositoryTest.java__

        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());

# b) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

__B_EmployeeService_UnitTest.java__

        @Mock( lenient = true)
        private EmployeeRepository employeeRepository;

        @InjectMocks
        private EmployeeServiceImpl employeeService;

        public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }

## c) What is the difference between standard @Mock and @MockBean?

These two annotations are very similar to each other, but they differ in a couple of aspects. One of them is that the @Mock annotation is an annotation from Mockito, while @MockBean is from Spring Boot. Another difference is that @Mock is used to create a mock object of a class or interface, which we can use to stub return values for its methods and verify if they were called, while @MockBean adds mock objects to the Spring application context, which means that the mock will replace any existing bean of the same type in the application context. The @Mock annotation should be used when there is no need for dependicies of the Spring Boot container, and if there is and we need to mock one of the beans then the @MockBean should be used.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

This file has the purpose of creating an enviroment for integration tests. For example, if we use a MySQL database in our application, we can use this file to create an enviroment where an H2Database is used since its faster and there is no need for a persistent database.

## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

It makes sense to divide the code in the set of the layers they use, theres no need to test the database and mocking a RestController or loading it context so what we do is slice or Application to multiple layers and the develop each test with each layer.