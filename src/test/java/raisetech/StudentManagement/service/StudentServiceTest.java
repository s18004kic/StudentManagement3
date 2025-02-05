package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository,converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentList();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList,studentCourseList);
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出され_正しい受講生詳細が返ること() {
    // テスト用データ
    String studentId = "1";
    Student student = new Student();
    student.setId(studentId);
    student.setName("Test User");

    List<StudentCourse> studentCourses = new ArrayList<>();
    StudentCourse course = new StudentCourse();
    course.setStudentId(studentId);
    course.setCourseName("Javaコース");
    studentCourses.add(course);

    // リポジトリのモック設定
    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentCourse(studentId)).thenReturn(studentCourses);

    // メソッド実行
    StudentDetail result = sut.searchStudent(studentId);

    // 検証
    assertNotNull(result);
    assertEquals(studentId, result.getStudent().getId());
    assertEquals("Test User", result.getStudent().getName());
    assertEquals(1, result.getStudentCourseList().size());
    assertEquals("Javaコース", result.getStudentCourseList().get(0).getCourseName());

    // リポジトリの呼び出しを確認
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentCourse(studentId);
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出され_受講生詳細が返ること() {
    // テスト用データの作成
    Student student = new Student();
    student.setId("1");
    student.setName("Test User");

    StudentCourse course1 = new StudentCourse();
    course1.setCourseName("Javaコース");

    StudentCourse course2 = new StudentCourse();
    course2.setCourseName("Spring Framework");

    List<StudentCourse> studentCourses = new ArrayList<>();
    studentCourses.add(course1);
    studentCourses.add(course2);

    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    // リポジトリのモック設定
    doNothing().when(repository).registerStudent(any(Student.class));
    doNothing().when(repository).registerStudentCourse(any(StudentCourse.class));

    // メソッド実行
    StudentDetail result = sut.registerStudent(studentDetail);

    // 検証
    assertNotNull(result);
    assertEquals(student.getId(), result.getStudent().getId());
    assertEquals(student.getName(), result.getStudent().getName());
    assertEquals(2, result.getStudentCourseList().size());
    assertEquals("Javaコース", result.getStudentCourseList().get(0).getCourseName());
    assertEquals("Spring Framework", result.getStudentCourseList().get(1).getCourseName());

    // リポジトリの呼び出しを検証
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(2)).registerStudentCourse(any(StudentCourse.class));

    // `initStudentsCourse()` により `StudentCourse` に `studentId` がセットされていることを確認
    assertEquals("1", studentCourses.get(0).getStudentId());
    assertEquals("1", studentCourses.get(1).getStudentId());
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出され_正しい受講生が返ること() {
    // テスト用データの作成
    Student student = new Student();
    student.setId("1");
    student.setName("Test User");

    StudentCourse course1 = new StudentCourse();
    course1.setCourseName("Javaコース");

    StudentCourse course2 = new StudentCourse();
    course2.setCourseName("Spring Framework");

    List<StudentCourse> studentCourses = new ArrayList<>();
    studentCourses.add(course1);
    studentCourses.add(course2);

    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    // リポジトリのモック設定
    doNothing().when(repository).registerStudent(any(Student.class));
    doNothing().when(repository).registerStudentCourse(any(StudentCourse.class));

    // メソッド実行
    StudentDetail result = sut.registerStudent(studentDetail);

    // 検証
    assertNotNull(result);
    assertEquals(student.getId(), result.getStudent().getId());
    assertEquals(student.getName(), result.getStudent().getName());
    assertEquals(2, result.getStudentCourseList().size());
    assertEquals("Javaコース", result.getStudentCourseList().get(0).getCourseName());
    assertEquals("Spring Framework", result.getStudentCourseList().get(1).getCourseName());

    // リポジトリの呼び出しを検証
    verify(repository, times(1)).registerStudent(student);
    verify(repository, times(2)).registerStudentCourse(any(StudentCourse.class));

    // `initStudentsCourse()` により `StudentCourse` に `studentId` がセットされていることを確認
    assertEquals("1", studentCourses.get(0).getStudentId());
    assertEquals("1", studentCourses.get(1).getStudentId());
  }

  //@Test
  //void 受講生詳細の登録_初期化処理が行われること(){
  //  String id = "1";
  //  Student student = new Student();
  //  student.setId(id);
  //  StudentCourse studentCourse = new StudentCourse();
//
  //  sut.initStudentsCourse((studentCourse, student.getId());
//
  //  assertEquals(id, studentCourse.getStudentId());
  //  assertEquals(LocalDateTime.now().getHour(), studentCourse.getCourseStartAt().getHour());
  //  assertEquals(LocalDateTime.now().plusYears(1).getYear(), studentCourse.getCourseEndAt().getYear());
  //}
}