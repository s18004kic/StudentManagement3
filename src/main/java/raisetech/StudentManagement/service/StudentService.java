package raisetech.StudentManagement.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

  private StudentRepository repository;
  //private List<Student> students;
  //private List<Student> students = new ArrayList<>();

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {//search30{ //getStudentList()
    return repository.search();
    //検索処理 repository.search();return students.stream()
    //List<Student> filteredStudents = students.stream()
    //    .filter(student -> students.get >= 30 && student.getAge() < 40)
    //    .collect(Collectors.toList());
    //return filteredStudents;

    //return repository.search().stream()
    //    .filter(student -> student.getAge() >= 0 && student.getAge() < 100)
    //    .collect(Collectors.toList());
  }  //ここで何からの処理を行う

  public StudentDetail searchStudent(String id){
    Student student = repository.searchstudent(id);
    List<StudentsCourses>studentsCourses = repository.searchStudentsCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<StudentsCourses> searchStudentCourseList() {
    // Javaコースをフィルタリングして返す
    return repository.findAll(); //.stream()
        //.filter(course -> "Javaコース".equals(course.getCourseName()))
        //.collect(Collectors.toList());
  }

  @Transactional
  public void registerStudent (StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    // TODO:コース情報登録を行う
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()){
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setCourseStartAt(LocalDateTime.now());
      studentsCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourse);
    }
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    // TODO:コース情報登録を行う
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()){
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}