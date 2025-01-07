package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchstudent(String id);

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> findAll();//searchStudentsCoursesList; //findAllとは別にStudentsCoursesを入れるのもＯＫ

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCourses(String studentId);//findAll();

  @Insert(
      "INSERT INTO students(name,kana_Name,nickname,email,area,age,sex,remark,is_deleted,telephone) "
      + "VALUES(#{name},#{kanaName},#{nickname},#{email},#{area},#{age},#{sex},#{remark},#{telephone},#{department},false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudent(Student student);

  @Insert(" INSERT INTO students_courses(student_id,course_name,course_start_at,course_end_at)"
      + "VALUES(#{studentId},#{courseName},#{courseStartAt},#{courseEndAt})")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudentsCourses(StudentsCourses studentsCourses);

  @Update(
      "UPDATE students SET name = #{name},kana_name = #{kanaName},nickname = #{nickname},"
          + "email = #{email},area = #{area},age = #{age},sex = #{sex},remark = #{remark},is_deleted = #{isDeleted},telephone = #{telephone}  WHERE id = #{id}")
  void updateStudent(Student student);

  @Update(
      " UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);

  //@Repository
  //public interface StudentRepository extends JpaRepository<Student, Long> {
  //  List<Student> findByIsDeletedFalse();
  //}

}




