package by.edc.telegrambot.service;

public class TableModel {

    private String studentGroupInformation;

    private String name;
    private String lessonTime;
    private String subject;
    private String lessonType;
    private String auditory;
    private String subgroup;
    private String studentGroup;
    private int id;
    private String fio;

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    private int numSubgroup;

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }



    public int getNumSubgroup() {
        return numSubgroup;
    }

    public void setNumSubgroup(int numSubgroup) {
        this.numSubgroup = numSubgroup;
    }


    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
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

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentGroupInformation() {
        return studentGroupInformation;
    }

    public void setStudentGroupInformation(String studentGroupInformation) {
        this.studentGroupInformation = studentGroupInformation;
    }

}
