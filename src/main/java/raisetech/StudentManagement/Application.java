package raisetech.StudentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RestController
public class Application {

	//@Autowired
	//private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//@GetMapping("/studentList")
	//public List<Student> getStudentList(){
	//	return repository.search();
	//}
//
	//@GetMapping("/studentsCourseList")
	//public List<StudentsCourses> getStudentsCourseList(){
	//	return repository.findAll();//findAllとは別にStudentsCoursesを入れるのもＯＫ
	//}
}
