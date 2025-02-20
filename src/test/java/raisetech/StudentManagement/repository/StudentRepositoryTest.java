package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING_BUFFER;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    // 実際の検索結果を取得
    List<Student> actual = sut.search();

    // 検証①: 受講生の数が5件であることを確認
    assertThat(actual).hasSize(5);

    // 検証②: 具体的なデータの中身を確認（例: 1人目のデータ）
    assertThat(actual.get(0))
        .extracting(Student::getId, Student::getName, Student::getEmail)
        .containsExactly("1", "山田太郎", "taro@example.com");

    // 検証③: すべての受講生データが期待通りか確認
    assertThat(actual).extracting(Student::getName)
        .containsExactly("山田太郎", "鈴木一郎", "田中花子", "佐藤良子", "伊藤大気");
  }

  //@Test
  //void 受講生の全件検索が行えること() {
  //  List<Student> actual = sut.search();
  //  assertThat(actual.size()).isEqualTo(5);
  //}

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setName("山田太郎");
    student.setName("ヤマダタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@example.com");
    student.setArea("東京");
    student.setAge(25);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 指定したIDで受講生が正しく検索されること() {
    // Arrange: 期待する受講生データを準備（コンストラクタを使用）
    Student expected = new Student(
        "1", "山田太郎", "ヤマダタロウ", "タロ",
        "taro@example.com", "東京", 25, "男性",
        null, false, null, null
    );

    // Act: IDで検索
    Student actual = sut.searchStudent("1");

    // Assert: 検索結果が期待値と一致することを一括で検証
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  //@Test
  //void 指定したIDで受講生が正しく検索されること() {
  //  // Arrange: 期待する受講生データを準備
  //  String studentId = "1";
  //  Student expected = new Student();
  //  expected.setId(studentId);
  //  expected.setName("山田太郎");
  //  expected.setKanaName("ヤマダタロウ");
  //  expected.setNickname("タロ");
  //  expected.setEmail("taro@example.com");
  //  expected.setArea("東京");
  //  expected.setAge(25);
  //  expected.setSex("男性");
//
  //  // Act: IDで検索
  //  Student actual = sut.searchStudent(studentId);
//
  //  // Assert: 検索結果が期待値と一致することを一括で検証
  //  assertThat(actual).isEqualToComparingFieldByField(expected);

  @Test
  void 受講生コースの全件検索が行えること() {
    // 実際の検索結果を取得
    List<StudentCourse> actual = sut.searchStudentCourseList();

    // 受講生コースの件数が10件であることを確認
    assertThat(actual).hasSize(10);

    // 1件目のデータが正しいことを確認
    assertThat(actual.get(0))
        .extracting(StudentCourse::getStudentId, StudentCourse::getCourseName,
            StudentCourse::getCourseStartAt, StudentCourse::getCourseEndAt)
        .containsExactly(
            "1", "Javaコース",
            LocalDateTime.of(2023, 4, 1, 9, 0, 0),
            LocalDateTime.of(2023, 7, 1, 15, 0, 0));

    // すべてのコース名が期待通りであることを確認
    assertThat(actual)
        .extracting(StudentCourse::getCourseName)
        .containsExactly(
            "Javaコース", "AWSコース", "デザインコース", "Web制作コース", "デザインコース",
            "マーケティングコース", "Javaコース", "マーケティングコース", "AWSコース", "Web制作コース"
        );
  }

  //@Test
  //void 受講生コースの全件検索が行えること() {
  //  List<StudentCourse> actual = sut.searchStudentCourseList();
  //  assertThat(actual.size()).isEqualTo(10);
  //}

  @Test
  void 指定したIDで受講生のコースが正しく検索されること() {
    // Arrange: テストデータを準備
    String studentId = "1";

    // テスト用の期待されるコースデータを準備
    StudentCourse expectedCourse1 = new StudentCourse();
    expectedCourse1.setId("1");
    expectedCourse1.setStudentId(studentId);
    expectedCourse1.setCourseName("Javaコース");
    expectedCourse1.setCourseStartAt(LocalDateTime.of(2023, 4, 1, 9, 0));
    expectedCourse1.setCourseEndAt(LocalDateTime.of(2023, 7, 1, 15, 0));

    StudentCourse expectedCourse2 = new StudentCourse();
    expectedCourse2.setId("2");
    expectedCourse2.setStudentId(studentId);
    expectedCourse2.setCourseName("AWSコース");
    expectedCourse2.setCourseStartAt(LocalDateTime.of(2023, 5, 1, 10, 0));
    expectedCourse2.setCourseEndAt(LocalDateTime.of(2023, 8, 1, 16, 0));

    // Act: メソッドを実行してコース情報を取得
    List<StudentCourse> actualCourses = sut.searchStudentCourse(studentId);

    // Assert: 結果の検証
    assertNotNull(actualCourses);
    assertEquals(3, actualCourses.size());

    // 各コースの詳細を検証
    assertThat(actualCourses).extracting(StudentCourse::getCourseName)
        .containsExactlyInAnyOrder("Javaコース", "AWSコース", "Web制作コース");

    assertEquals(LocalDateTime.of(2023, 4, 1, 9, 0), actualCourses.get(0).getCourseStartAt());
    assertEquals(LocalDateTime.of(2023, 8, 1, 16, 0), actualCourses.get(1).getCourseEndAt());
  }

  @Test
  void 受講生とコースが正しく登録されること() {
    // Arrange: 受講生データを準備
    Student student = new Student();
    student.setId("6");
    student.setName("佐藤次郎");
    student.setKanaName("サトウジロウ");
    student.setNickname("ジロ");
    student.setEmail("jiro@example.com");
    student.setArea("千葉");
    student.setAge(32);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);
    student.setStatus("仮申込");

    // コースデータを準備
    StudentCourse course = new StudentCourse();
    course.setId("11");
    course.setStudentId("6");
    course.setCourseName("Pythonコース");
    course.setCourseStartAt(LocalDateTime.of(2025, 1, 1, 9, 0));
    course.setCourseEndAt(LocalDateTime.of(2025, 5, 1, 18, 0));

    // Act: 受講生とコースを登録
    sut.registerStudent(student);
    sut.registerStudentCourse(course);

    // Assert: データが正しく登録されたか検証
    List<Student> actualStudents = sut.search();
    assertThat(actualStudents.size()).isEqualTo(6);  // 6人目の受講生が追加されたか

    Student registeredStudent = actualStudents.stream()
        .filter(s -> s.getId().equals("6"))
        .findFirst()
        .orElse(null);
    assertNotNull(registeredStudent);
    assertEquals("佐藤次郎", registeredStudent.getName());
    assertEquals("jiro@example.com", registeredStudent.getEmail());

    List<StudentCourse> actualCourses = sut.searchStudentCourse("6");
    assertThat(actualCourses.size()).isEqualTo(1);
    assertEquals("Pythonコース", actualCourses.get(0).getCourseName());
    assertEquals(LocalDateTime.of(2025, 1, 1, 9, 0), actualCourses.get(0).getCourseStartAt());
    assertEquals(LocalDateTime.of(2025, 5, 1, 18, 0), actualCourses.get(0).getCourseEndAt());
  }

  @Test
  void 受講生の情報が正しく更新されること() {
    // Arrange: 初期データを準備
    String studentId = "1";  // 既存の受講生ID
    Student student = new Student();
    student.setId(studentId);
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@example.com");
    student.setArea("東京");
    student.setAge(25);
    student.setSex("男性");
    student.setRemark("");
    student.setDeleted(false);
    student.setStatus("仮申込");

    // Act: 受講生情報を更新
    student.setName("山田花子");
    student.setEmail("hanako@example.com");
    student.setArea("大阪");
    student.setAge(26);
    student.setSex("女性");
    student.setStatus("仮申込");

    sut.updateStudent(student);

    // Assert: 更新結果を検証
    Student updatedStudent = sut.searchStudent(studentId);
    assertNotNull(updatedStudent);
    assertEquals("山田花子", updatedStudent.getName());
    assertEquals("hanako@example.com", updatedStudent.getEmail());
    assertEquals("大阪", updatedStudent.getArea());
    assertEquals(26, updatedStudent.getAge());
    assertEquals("女性", updatedStudent.getSex());
  }

  @Test
  void 受講生のコース情報が正しく更新されること() {
    // Arrange: 初期データ
    String studentId = "1";
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId(studentId);
    studentCourse.setCourseName("Javaコース");
    studentCourse.setCourseStartAt(LocalDateTime.of(2023, 4, 1, 9, 0));
    studentCourse.setCourseEndAt(LocalDateTime.of(2023, 7, 1, 15, 0));
    studentCourse.setStatus("仮申込");

    sut.updateStudentCourse(studentCourse);

    // Assert: 更新された情報を取得して検証
    List<StudentCourse> updatedCourse = sut.searchStudentCourseById(studentId);
    assertNotNull(updatedCourse);
    //assertEquals("Javaコース", updatedCourse.getCourseName()); //上のコース名と同じにする
    assertEquals(studentCourse.getCourseName(),updatedCourse.get(0).getCourseName());
    assertEquals(studentCourse.getCourseStartAt().getYear(),updatedCourse.get(0).getCourseStartAt().getYear());
    assertEquals(studentCourse.getCourseEndAt().getYear(),updatedCourse.get(0).getCourseEndAt().getYear());
    assertEquals(studentCourse.getStatus(),updatedCourse.get(0).getStatus());
  }
}
// Act: コース情報を更新
//tudentCourse.setCourseName("AWSコース");
//tudentCourse.setCourseStartAt(LocalDateTime.of(2023, 5, 1, 10, 0));
//tudentCourse.setCourseEndAt(LocalDateTime.of(2023, 8, 1, 16, 0));
//tudentCourse.setStatus("本申込");

  //@Test
  //void 受講生のコース情報が正しく更新されること() {
  //  // Arrange: 初期データ
  //  StudentCourse studentCourse = new StudentCourse();
  //  studentCourse.setId("1");
  //  studentCourse.setStudentId("1");
  //  studentCourse.setCourseName("Javaコース");
  //  studentCourse.setCourseStartAt(LocalDateTime.of(2023, 4, 1, 9, 0));
  //  studentCourse.setCourseEndAt(LocalDateTime.of(2023, 7, 1, 15, 0));
  //  studentCourse.setStatus("本申込");
//
  //  // Act & Assert: 更新処理が例外を投げないことを確認
  //  assertDoesNotThrow(() -> sut.updateStudentCourse(studentCourse));
  //}

  //@Test
  //void 受講生のコース情報が正しく更新されること() {
  //  // Arrange: 初期データを準備
  //  String courseId = "1";  // 既存のコースID
  //  String studentId = "1";  // 既存の受講生ID
//
  //  StudentCourse studentCourse = new StudentCourse();
  //  studentCourse.setId(courseId);
  //  studentCourse.setStudentId(studentId);
  //  studentCourse.setCourseName("Javaコース");
  //  studentCourse.setCourseStartAt(LocalDateTime.of(2023, 4, 1, 9, 0));
  //  studentCourse.setCourseEndAt(LocalDateTime.of(2023, 7, 1, 15, 0));
//
  //  // Act: コース情報を更新
  //  studentCourse.setCourseName("AWSコース");
  //  studentCourse.setCourseStartAt(LocalDateTime.of(2023, 5, 1, 10, 0));
  //  studentCourse.setCourseEndAt(LocalDateTime.of(2023, 8, 1, 16, 0));
//
  //  sut.updateStudentCourse(studentCourse);
//
  //  // Assert: 変更後の `studentCourse` オブジェクトを直接検証
  //  assertNotNull(studentCourse);
  //  assertEquals("AWSコース", studentCourse.getCourseName());
  //  assertEquals(LocalDateTime.of(2023, 5, 1, 10, 0), studentCourse.getCourseStartAt());
  //  assertEquals(LocalDateTime.of(2023, 8, 1, 16, 0), studentCourse.getCourseEndAt());
  //}
