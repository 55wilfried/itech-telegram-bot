package itech.telegram.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class keyBoardFactory {


    public static ReplyKeyboard getFirstChoices() {
        KeyboardRow row = new KeyboardRow();
        row.add("stop");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getSolution() {
        KeyboardRow row = new KeyboardRow();
        row.add("main menu");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getExpertise() {
        KeyboardRow row = new KeyboardRow();
        row.add("main menu");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getClients() {
        KeyboardRow row = new KeyboardRow();
        row.add("main menu");
        return new ReplyKeyboardMarkup(List.of(row));
    }

}
