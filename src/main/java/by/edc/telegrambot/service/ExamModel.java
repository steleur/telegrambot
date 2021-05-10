package by.edc.telegrambot.service;

public class ExamModel {

  String weekDay;
  String studentGroupInformation;
  String auditory;
  String startLessonTime;
  String endLessonTime;
  String subject;
  String lessonType;
  String fio;

  public String getWeekDay() {
    return weekDay;
  }

  public void setWeekDay(String weekDay) {
    this.weekDay = weekDay;
  }

  public String getStudentGroupInformation() {
    return studentGroupInformation;
  }

  public void setStudentGroupInformation(String studentGroupInformation) {
    this.studentGroupInformation = studentGroupInformation;
  }

  public String getAuditory() {
    return auditory;
  }

  public void setAuditory(String auditory) {
    this.auditory = auditory;
  }

  public String getStartLessonTime() {
    return startLessonTime;
  }

  public void setStartLessonTime(String startLessonTime) {
    this.startLessonTime = startLessonTime;
  }

  public String getEndLessonTime() {
    return endLessonTime;
  }

  public void setEndLessonTime(String endLessonTime) {
    this.endLessonTime = endLessonTime;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getLessonType() {
    return lessonType;
  }

  public void setLessonType(String lessonType) {
    this.lessonType = lessonType;
  }

  public String getFio() {
    return fio;
  }

  public void setFio(String fio) {
    this.fio = fio;
  }
}
