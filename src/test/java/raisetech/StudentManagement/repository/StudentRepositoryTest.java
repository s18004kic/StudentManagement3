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
    // Arrange: 期待する受講生データを準備
    List<Student> expected = List.of(
        new Student("1", "山田太郎", "ヤマダタロウ", "タロ", "taro@example.com", "東京", 25,
            "男性",null, false, null),
        new Student("2", "鈴木一郎", "スズキイチロウ", "イチ", "ichiro@example.com", "大阪", 30,
            "男性", null, false, null),
        new Student("3", "田中花子", "タナカハナコ", "ハナ", "hana@example.com", "北海道", 22,
            "女性", null, false, null), // 修正
        new Student("4", "佐藤良子", "サトウリョウコ", "リョウ", "ryoko@example.com", "福岡", 28,
            "女性", null, false, null), // 修正
        new Student("5", "伊藤大気", "イトウタイキ", "タイキ", "haruka@example.com", "沖縄", 38,
            "その他", null, false, null) // 修正
    );

    List<Student> actual = sut.search();
    System.out.println("Expected: " + expected);
    System.out.println("Actual: " + actual);
    // Assert: 検索結果が期待値と完全に一致することを検証
    assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    //assertThat(actual).containsExactlyElementsOf(expected);
  }


  @Test
  void 受講生の登録が行えること() {
    // Arrange: 新規受講生データを準備（コンストラクタを使用）
    Student student = new Student(
        "1",        // id（仮の値、DBで自動生成されるなら不要）
        "山田太郎",      // name
        "ヤマダタロウ",  // kanaName
        "タロ",          // nickname
        "taro@example.com", // email
        "東京",          // area
        25,              // age
        "男性",          // sex
        "",              // remark
        false,           // isDeleted
        null            //telephone
    );

    // Act: 受講生を登録
    sut.registerStudent(student);

    // Assert: 登録後の受講生リストを取得してサイズ確認
    List<Student> actual = sut.search();
    assertThat(actual).hasSize(6);
  }


  @Test
  void 指定したIDで受講生が正しく検索されること() {
    // Arrange: 期待する受講生データを準備（コンストラクタを使用）
    Student expected = new Student(
        "1", "山田太郎", "ヤマダタロウ", "タロ",
        "taro@example.com", "東京", 25, "男性",
        null, false, null
    );

    // Act: IDで検索
    Student actual = sut.searchStudent("1");

    // Assert: 検索結果が期待値と一致することを一括で検証
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void 受講生コースの全件検索が行えること() {
    // 🔹 Arrange: 期待する受講生コースデータ
    List<StudentCourse> expected = List.of(
        new StudentCourse(null, "1", "Javaコース", date(2023, 4, 1, 9), date(2023, 7, 1, 15),
            "仮申込"),
        new StudentCourse(null, "1", "AWSコース", date(2023, 5, 1, 10), date(2023, 8, 1, 16),
            "仮申込"),
        new StudentCourse(null, "2", "デザインコース", date(2023, 6, 1, 11), date(2023, 9, 1, 17),
            "仮申込"),
        new StudentCourse(null, "3", "Web制作コース", date(2023, 7, 1, 12), date(2023, 10, 1, 18),
            "仮申込"),
        new StudentCourse(null, "3", "デザインコース", date(2023, 8, 1, 13), date(2023, 11, 1, 19),
            "仮申込"),
        new StudentCourse(null, "3", "マーケティングコース", date(2023, 9, 1, 9),
            date(2024, 1, 1, 15), "仮申込"),
        new StudentCourse(null, "4", "Javaコース", date(2023, 10, 1, 10), date(2024, 2, 1, 16),
            "仮申込"),
        new StudentCourse(null, "4", "マーケティングコース", date(2023, 11, 1, 11),
            date(2024, 3, 1, 17), "仮申込"),
        new StudentCourse(null, "5", "AWSコース", date(2023, 12, 1, 12), date(2024, 4, 1, 18),
            "仮申込"),
        new StudentCourse(null, "1", "Web制作コース", date(2024, 1, 1, 13), date(2024, 5, 1, 19),
            "仮申込")
    );

    // 🔹 Act: データを取得
    List<StudentCourse> actual = sut.searchStudentCourseList();

    // 🔹 Debug: 取得したデータを確認 (ログ出力)
    System.out.println("取得したデータ:");
    actual.forEach(System.out::println);

    // 🔹 Assert: `id` を無視してフィールドごとに比較
    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("id") // 🔥 `id` の違いを無視
        .isEqualTo(expected);
  }

  private static LocalDateTime date(int year, int month, int day, int hour) {
    return LocalDateTime.of(year, month, day, hour, 0);
  }

  @Test
  void 指定したIDで受講生のコースが正しく検索されること() {
    // Arrange: テストデータを準備
    String studentId = "1";

    // テスト用の期待されるコースデータ（コンストラクタを使用）
    StudentCourse expectedCourse1 = new StudentCourse(
        "1", studentId, "Javaコース",
        LocalDateTime.of(2023, 4, 1, 9, 0),
        LocalDateTime.of(2023, 7, 1, 15, 0),
        "仮申込"
    );

    StudentCourse expectedCourse2 = new StudentCourse(
        "2", studentId, "AWSコース",
        LocalDateTime.of(2023, 5, 1, 10, 0),
        LocalDateTime.of(2023, 8, 1, 16, 0),
        "仮申込"
    );

    StudentCourse expectedCourse3 = new StudentCourse(
        "3", studentId, "Web制作コース",
        LocalDateTime.of(2023, 6, 1, 11, 0),
        LocalDateTime.of(2023, 9, 1, 17, 0),
        "仮申込"
    );

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
    // Arrange: 受講生データを準備（コンストラクタを使用）
    Student student = new Student(
        "6", "佐藤次郎", "サトウジロウ", "ジロ",
        "jiro@example.com", "千葉", 32, "男性", "", false, null
    );

    // コースデータを準備（コンストラクタを使用）
    StudentCourse course = new StudentCourse(
        "11", "6", "Pythonコース",
        LocalDateTime.of(2025, 1, 1, 9, 0),
        LocalDateTime.of(2025, 5, 1, 18, 0),
        "仮申込"
    );

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
    student.setTelephone(null);

    // Act: 受講生情報を更新
    student.setName("山田花子");
    student.setEmail("hanako@example.com");
    student.setArea("大阪");
    student.setAge(26);
    student.setSex("女性");

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
    // Arrange: 初期データ（コンストラクタを使用）
    String studentId = "1";
    StudentCourse studentCourse = new StudentCourse(
        "1", studentId, "Javaコース",
        LocalDateTime.of(2023, 4, 1, 9, 0),
        LocalDateTime.of(2023, 7, 1, 15, 0),
        "仮申込"
    );

    // Act: コース情報を更新
    sut.updateStudentCourse(studentCourse);

    // Assert: 更新された情報を取得して検証
    List<StudentCourse> updatedCourse = sut.searchStudentCourseById(studentId);
    assertNotNull(updatedCourse);
    assertEquals(studentCourse.getCourseName(), updatedCourse.get(0).getCourseName());
    assertEquals(studentCourse.getCourseStartAt().getYear(),
        updatedCourse.get(0).getCourseStartAt().getYear());
    assertEquals(studentCourse.getCourseEndAt().getYear(),
        updatedCourse.get(0).getCourseEndAt().getYear());
    assertEquals(studentCourse.getStatus(), updatedCourse.get(0).getStatus());
  }
}