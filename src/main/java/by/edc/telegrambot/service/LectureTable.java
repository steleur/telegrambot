package by.edc.telegrambot.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class LectureTable {
    public static String getLectureTable(String message, TableModel tableModel) throws IOException {
        URL getLectures = new URL("https://journal.bsuir.by/api/v1/employees");
        URL getCurrentWeekAPI = new URL("https://journal.bsuir.by/api/v1/week");


        Scanner in = new Scanner((InputStream) getLectures.getContent(), "UTF-8");
        Calendar calendar = new GregorianCalendar();
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String result = "";
        String lectures = "";
        String lectureTable = "";
        boolean workDay = false;
        while (in.hasNext()) {
            lectures += in.nextLine();
        }
        in = new Scanner((InputStream) getCurrentWeekAPI.getContent(), "UTF-8");
        int currentWeek = in.nextInt();
        if (weekDay == 0) {
            if (currentWeek == 4) {
                currentWeek = 1;
            } else {
                currentWeek += 1;
            }

        }
        String[] weekdays = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};


        JSONArray lectureTableArray = new JSONArray(lectures);

        for (int i = 0; i < lectureTableArray.length(); i++) {
            JSONObject lectureTableObject = lectureTableArray.getJSONObject(i);
            String fio = lectureTableObject.getString("fio");
            if (fio.contains(message)) {
                tableModel.setId(lectureTableObject.getInt("id"));
            }

        }
        URL getLecturesTable = new URL("https://journal.bsuir.by/api/v1/portal/employeeSchedule?employeeId=" + tableModel.getId());
        Scanner in1 = new Scanner((InputStream) getLecturesTable.getContent(), "UTF-8");
        while (in1.hasNext()) {
            lectureTable += in1.nextLine();
        }


        JSONObject lectureTableObject = new JSONObject(lectureTable);
        tableModel.setFio(lectureTableObject.getJSONObject("employee").getString("fio"));
        JSONArray getSchedulesArray = lectureTableObject.getJSONArray("schedules");

        for (int k = 0; k < getSchedulesArray.length(); k++) {
            JSONObject obj = getSchedulesArray.getJSONObject(k);
            if (obj.getString("weekDay").equals(weekdays[weekDay])) {
                JSONArray getScheduleArray = obj.getJSONArray("schedule");
                workDay = true;

                for (int i = 0; i < getScheduleArray.length(); i++) {

                    JSONObject obj1 = getScheduleArray.getJSONObject(i);
                    JSONArray getAuditory = obj1.getJSONArray("auditory");
                    JSONArray getStudentGroupInformationArray = obj1.getJSONArray("studentGroupInformation");
                    JSONArray getWeekNumber = obj1.getJSONArray("weekNumber");
                    JSONArray getStudentGroup = obj1.getJSONArray("studentGroup");

                    for (int j = 0; j < getWeekNumber.length(); j++) {

                        if ((int) getWeekNumber.get(j) == currentWeek) {

                            tableModel.setLessonTime(obj1.getString("lessonTime"));
                            try {
                                tableModel.setAuditory(getAuditory.getString(0));
                            } catch (org.json.JSONException e) {
                                tableModel.setAuditory("");
                            }
                            tableModel.setSubject(obj1.getString("subject"));
                            tableModel.setLessonType(obj1.getString("lessonType"));
                            tableModel.setNumSubgroup(obj1.getInt("numSubgroup"));
                            if (tableModel.getNumSubgroup() == 0) {
                                tableModel.setSubgroup("");
                            } else {
                                tableModel.setSubgroup("(подрг. " + tableModel.getNumSubgroup() + ")");
                            }

                            tableModel.setStudentGroupInformation(getStudentGroupInformationArray.getString(0));
                            tableModel.setStudentGroup(getStudentGroup.getString(0));

                            result += "*" + tableModel.getStudentGroup() + "*" + " " + tableModel.getLessonTime() + " " +
                                    tableModel.getSubject() + " (" + tableModel.getLessonType() + ") " +
                                    tableModel.getAuditory() + "\t" + tableModel.getSubgroup() + "\n";

                        }
                    }

                }


            }

        }
        if (workDay) {
            return "Ищу расписание для преподавателя " + tableModel.getFio() + " на завтра, учебная неделя №" + currentWeek + "\n"
                    + "\n" + result;
        } else {
            return tableModel.getFio() + " завтра отдыхает :)";
        }

    }
}

