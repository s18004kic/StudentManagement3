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
  //public String getId() {
  //  return id;
  //}
//
  //public void setId(String id) {
  //  this.id = id;
  //}
//
  //public String getName() {
  //  return name;
  //}
//
  //public void setName(String name) {
  //  this.name = name;
  //}
//
  //public String getKanaName() {
  //  return kanaName;
  //}
//
  //public void setKanaName(String kanaName) {
  //  this.kanaName = kanaName;
  //}
//
  //public String getNickname() {
  //  return nickname;
  //}
//
  //public void setNickname(String nickname) {
  //  this.nickname = nickname;
  //}
//
  //public String getEmail() {
  //  return email;
  //}
//
  //public void setEmail(String email) {
  //  this.email = email;
  //}
//
  //public String getArea() {
  //  return area;
  //}
//
  //public void setArea(String area) {
  //  this.area = area;
  //}
//
  //public int getAge() {
  //  return age;
  //}
//
  //public void setAge(int age) {
  //  this.age = age;
  //}
//
  //public String getSex() {
  //  return sex;
  //}
//
  //public void setSex(String sex) {
  //  this.sex = sex;
  //}