package by.edc.telegrambot.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExamTable {

  public static String getExamTable(String message, ExamModel examModel) throws IOException {
    URL getTableAPI = new URL(
        "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=" + message);
    Scanner in = new Scanner((InputStream) getTableAPI.getContent(), "UTF-8");
    String result = "";
    String table = "";
    while (in.hasNext()) {
      table += in.nextLine();
    }

    JSONObject object = new JSONObject(table);
    JSONArray getSchedulesArray = object.getJSONArray("schedules");
    JSONObject getSchedulesObject = getSchedulesArray.getJSONObject(0);
    JSONArray getScheduleArray = getSchedulesObject.getJSONArray("schedule");
    JSONObject getScheduleObject = getScheduleArray.getJSONObject(0);
    JSONArray getStudentGroupInformation = getScheduleObject
        .getJSONArray("studentGroupInformation");
    examModel.setStudentGroupInformation(getStudentGroupInformation.toString());

    JSONArray getExamSchedulesArray = object.getJSONArray("examSchedules");
    for (int i = 0; i < getExamSchedulesArray.length(); i++) {
      JSONObject getExamSchedulesObject = getExamSchedulesArray.getJSONObject(i);
      examModel.setWeekDay(getExamSchedulesObject.getString("weekDay"));
      JSONArray getExamScheduleArray = getExamSchedulesObject.getJSONArray("schedule");
      JSONObject getExamScheduleObject = getExamScheduleArray.getJSONObject(0);
      JSONArray getAuditoryArray = getExamScheduleObject.getJSONArray("auditory");
      examModel.setAuditory(getAuditoryArray.getString(0));
      examModel.setStartLessonTime(getExamScheduleObject.getString("startLessonTime"));
      examModel.setEndLessonTime(getExamScheduleObject.getString("endLessonTime"));
      examModel.setSubject(getExamScheduleObject.getString("subject"));
      examModel.setLessonType(getExamScheduleObject.getString("lessonType"));
      JSONArray getEmployeeArray = getExamScheduleObject.getJSONArray("employee");
      JSONObject getEmployeeObject = getEmployeeArray.getJSONObject(0);
      examModel.setFio(getEmployeeObject.getString("fio"));
      if (examModel.getLessonType().equals("Экзамен")) {

        result += "*" + examModel.getWeekDay() + " " + examModel.getStartLessonTime() +
            "-" + examModel.getEndLessonTime() + " " + examModel
            .getSubject() + " " + examModel.
            getAuditory() + " " + "(" + examModel.getLessonType() + ")" + "*" + "\n";
      } else {
        result += examModel.getWeekDay() + " " + examModel.getStartLessonTime() +
            "-" + examModel.getEndLessonTime() + " " + examModel
            .getSubject() + " " + examModel.
            getAuditory() + " " + "(" + examModel.getLessonType() + ")" + "\n";
      }

    }

    return "Ищу расписание экзаменов для группы " + message + "\n" + examModel
        .getStudentGroupInformation() + "\n" +
        result;
  }
}