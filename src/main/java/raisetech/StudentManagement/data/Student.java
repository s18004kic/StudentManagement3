package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private String id;
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark; // 備考
  private boolean isDeleted; // 削除
  private String telephone; //電話番号
}
