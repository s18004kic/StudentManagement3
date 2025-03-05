package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchCondition {
  private String id;
  private String name;
  private String courseName;
  private String email;
  private String area;
  private Integer age;
  private String sex;
}
