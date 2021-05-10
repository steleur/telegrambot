package by.edc.telegrambot.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
@PropertySource("application.properties")
public class TelegramBot extends TelegramLongPollingBot {

  String word;
  int result;
  String group;
  @Autowired
  ObjectMapper objectMapper;
  @Value("${bot.username}")
  private String botusername;
  @Value("${bot.token}")
  private String botToken;

  @Override
  public String getBotUsername() {
    return botusername;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override

  public void onUpdateReceived(Update update) {
    TableModel tableModel = new TableModel();
    ExamModel examModel = new ExamModel();
    Message message = update.getMessage();
    if (message != null && message.hasText()) {
      if (message.getText().startsWith("/table") && message.getText().length() == 13) {
        try {
          sendMsg(message, Table.getTable(message.getText().substring(7, 13), tableModel));
        } catch (IOException e) {
          sendMsg(message, "Группа не найдена");
        }

      } else if (message.getText().startsWith("/exams") && message.getText().length() == 13) {
        try {
          sendMsg(message, ExamTable.getExamTable(message.getText().substring(7, 13), examModel));
        } catch (IOException e) {
          sendMsg(message, "Группа не найдена");
        }
      } else if (message.getText().startsWith("/table") && message.getText().length() == 6) {
        sendMsg(message, "Введите номер группы");
      } else if (message.getText().equals("/lecture_table")) {
        sendMsg(message, "Введите Фамилию И. О. преподавателя");
      } else if (message.getText().startsWith("/random")) {
        word = message.getText();
        try {
          findNumber();
          sendMsg(message, rand());
        } catch (IllegalStateException e) {
          sendMsg(message, "Неверное значение. Помощь: /help");
        }
      } else if (message.getText().startsWith("/lecture_table")
          && message.getText().length() >= 15) {
        try {
          sendMsg(message,
              LectureTable.getLectureTable(message.getText().substring(15), tableModel));
        } catch (IOException e) {
          sendMsg(message, "Преподаватель не найден");
        }


      } else if (message.getText().equals("/start")) {
        sendMsg(message, "Привет!");
        sendMsg(message, "Список команд: /help");
      } else if (message.getText().equals("/help")) {
        sendMsg(message, "Список команд бота: (будет дополнен)\n" +
            "/start - вызывает бота\n" +
            "/help - вызывает это окно\n" +
            "/random *число* - генерирует случайное значение в диапазоне от 0 до *число*" + "\n" +
            "/table *номер_группы* -  вывыодит расписание группы на следующий день");
      } else {
        sendMsg(message, "Неизвестная команда. Список команд: /help");
      }

//            try {
//                objectMapper.writeValue(new File("src/test/resources/update.json"), update);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

    }


  }


  private void sendMsg(Message message, String text) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(message.getChatId().toString());
    sendMessage.setText(text);

    try {
      //setButtons(sendMessage);
      execute(sendMessage);

    } catch (TelegramApiException e) {
      e.printStackTrace();
    }


  }

  // bot abilities
  public String rand() {
    Random random = new Random();
    int k = random.nextInt(result);
    String N = String.valueOf(k);
    return N;
  }
//    public String table() throws IOException {
//        String url = "https://journal.bsuir.by/api/v1/studentGroup/schedule?studentGroup=" + group;
//
//        URL obj = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
//
//        connection.setRequestMethod("GET");
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        System.out.println(response.toString());
//        return response.toString();
//
//    }


  public void findNumber() {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(word);
    int start = 0;
    matcher.find(start);
    String value = word.substring(matcher.start(), matcher.end());
    result = Integer.parseInt(value);
    start = matcher.end();
  }

//    public void setButtons(SendMessage sendMessage) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();        //Создаем клавиатуру
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);                  //Параметр, который выводит клаву определенным пользователям или всем, на выбор
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);           //Скрывать клавиатуру или нет(поставили нет)
//
////Создаем кнопки
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//
//        KeyboardRow keyboardRowFirstRow = new KeyboardRow();                 //Инициализация строки
//        keyboardRowFirstRow.add(new KeyboardButton("Минск"));
//        keyboardRowFirstRow.add(new KeyboardButton("Брест"));                  //Создаем первую строку
//
//        KeyboardRow keyboardRowSecondRow = new KeyboardRow();                 //Инициализация строки
//        keyboardRowSecondRow.add(new KeyboardButton("Витебск"));
//        keyboardRowSecondRow.add(new KeyboardButton("Гомель"));
//
//        KeyboardRow keyboardRowThirdRow = new KeyboardRow();                 //Инициализация строки
//        keyboardRowThirdRow.add(new KeyboardButton("Гродно"));
//        keyboardRowThirdRow.add(new KeyboardButton("Могилев"));
//
//        KeyboardRow keyboardRowFourthRow = new KeyboardRow();
//        keyboardRowFourthRow.add(new KeyboardButton("Donate"));
//
//        KeyboardRow keyboardRowFifthRow = new KeyboardRow();
//        keyboardRowFifthRow.add(new KeyboardButton("Помощь"));
//        keyboardRowFifthRow.add(new KeyboardButton("О проекте:"));
//
//
//
//
//        keyboardRowList.add(keyboardRowFirstRow);        //Добавляем строки клавы в список
//        keyboardRowList.add(keyboardRowSecondRow);
//        keyboardRowList.add(keyboardRowThirdRow);        //Добавляем строки клавы в список
//        keyboardRowList.add(keyboardRowFourthRow);       //Добавляем строки клавы в список
//        keyboardRowList.add(keyboardRowFifthRow);        //Добавляем строки клавы в список
//        replyKeyboardMarkup.setKeyboard(keyboardRowList);       //Устанавливаем список на клавиатуру
//    }
}