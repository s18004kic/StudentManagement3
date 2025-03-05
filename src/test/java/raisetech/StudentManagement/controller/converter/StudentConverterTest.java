package raisetech.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  @InjectMocks
  private StudentConverter converter = new StudentConverter();

  @Test
  void 学生とコースが正しくマッピングされること() {
    List<Student> studentList = createStudentList("1", "高橋次郎");
    List<StudentCourse> studentCourseList = createStudentCourseList(
        "1", "Javaコース", LocalDateTime.of(2023, 1, 1, 9, 0), LocalDateTime.of(2023, 12, 31, 18, 0),"仮申込");

    List<StudentDetail> result = converter.convertStudentDetails(studentList, studentCourseList);

    assertStudentDetail(result, "高橋次郎", 1, "Javaコース",
        LocalDateTime.of(2023, 1, 1, 9, 0), LocalDateTime.of(2023, 12, 31, 18, 0));
  }

  @Test
  void 学生がコースを持たない場合_空のコースリストが返されること() {
    List<Student> studentList = createStudentList("2", "山田太郎");
    List<StudentCourse> studentCourseList = new ArrayList<>(); // コースなし

    List<StudentDetail> result = converter.convertStudentDetails(studentList, studentCourseList);

    assertStudentDetail(result, "山田太郎", 0, null, null, null);
  }

  // ヘルパーメソッド（テストデータ作成用）
  private List<Student> createStudentList(String id, String name) {
    List<Student> studentList = new ArrayList<>();
    Student student = new Student();
    student.setId(id);
    student.setName(name);
    studentList.add(student);
    return studentList;
  }

  //以下追加したもの
  private List<StudentCourse> createStudentCourseList(String studentId, String courseName,
      LocalDateTime courseStartAt, LocalDateTime courseEndAt, String status) {
    List<StudentCourse> studentCourseList = new ArrayList<>();

    // コンストラクタを使ってオブジェクトを作成
    StudentCourse course = new StudentCourse("101", studentId, courseName, courseStartAt, courseEndAt, status);

    studentCourseList.add(course);
    return studentCourseList;
  }

  // 検証用のヘルパーメソッド
  private void assertStudentDetail(List<StudentDetail> result, String expectedName,
      int expectedCourseSize, String expectedCourseName,
      LocalDateTime expectedCourseStartAt, LocalDateTime expectedCourseEndAt) {
    assertEquals(1, result.size());
    assertEquals(expectedName, result.get(0).getStudent().getName());
    assertEquals(expectedCourseSize, result.get(0).getStudentCourseList().size());
    if (expectedCourseSize > 0) {
      assertEquals(expectedCourseName, result.get(0).getStudentCourseList().get(0).getCourseName());
      assertEquals(expectedCourseStartAt, result.get(0).getStudentCourseList().get(0).getCourseStartAt());
      assertEquals(expectedCourseEndAt, result.get(0).getStudentCourseList().get(0).getCourseEndAt());
    }
  }
}