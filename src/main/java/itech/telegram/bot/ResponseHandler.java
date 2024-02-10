package itech.telegram.bot;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import java.util.Map;

import static itech.telegram.bot.Constants.START_TEXT;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, UserState> chatStates;

    public ResponseHandler(SilentSender sender, DBContext db) {
        this.sender = sender;
        chatStates = db.getMap(Constants.CHAT_STATES);
    }
    public void replyToStart(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(START_TEXT);
        sender.execute(message);
        chatStates.put(chatId, UserState.AWAITING_NAME);
    }
    public void replyToButtons(long chatId, Message message) {
        if (message.getText().equalsIgnoreCase("stop")) {
            stopChat(chatId);
        }

        switch (chatStates.get(chatId)) {
            case AWAITING_NAME -> replyToName(chatId, message);
            case AWAITING_NOT -> replyToNot(chatId, message);
            case FIRST_CHOICES -> replyToFirstChoices(chatId, message);
            default -> unexpectedMessage(chatId);
        }
    }
    private void unexpectedMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("I did not expect that.");
        sender.execute(sendMessage);
    }

    private void replyToFirstChoices(long chatId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (message.getText().matches("1")) {
            sendMessage.setText("We have the following solution\n  Alpha Bank\n Alpha Microfinance\n Alpha Monetique\n I-collect \n Alpha Xpress \n Alpha web \n Alpha Xpress Bank \n My collect)");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            sender.execute(sendMessage);
        } else if (message.getText().matches("2")) {
            sendMessage.setText("A team of qualified engineers to meet your needs for digital solutions. \nSoftware Engineering\n IT Audit & Consultinge \n Formation \n Training");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            sender.execute(sendMessage);
        } else if (message.getText().matches("3")) {
            sendMessage.setText("With nearly 250 banners served, our achievements can be broken down by our customers' business sector \n " +
                    "Mupeci\n BACCCUL \n SOCECBA \n CPCD\n MitaCCUL\n SOWOCCUL \n Alpha Xpress Bank \n My collect\n PECCU \n NkwenCCUL\n CAPOCOP\n \n" +
                    "TICCUL \n \n" +
                    "ChoCCUL\n TomCCUL");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            sender.execute(sendMessage);
        }else if(message.getText().matches("4"))  {
            sendMessage.setText("Innovative Technology \n I-TECH is a Cameroonian-owned company specialising in the supply of technological software solutions adapted to the information systems of private and public organisations and in IT consulting.\n" +
                    "\n" +
                    "Based in Cameroon, we have 15 years of proven expertise and serve the Cameroonian market and that of the CEMAC ZONE (CHAD). We are positioned as the main technical partners of EMFs, with more than 250 brands in our customer portfolio.");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            sender.execute(sendMessage);
        }else if(message.getText().matches("5"))  {
            sendMessage.setText("phone number: (+237) 696 61 39 46"+" \n" +". Email: contacts@i-techsarl.com.");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            sender.execute(sendMessage);
        }else {
            sendMessage.setText("Option not found. Please select from the options above.");
            sendMessage.setReplyMarkup(keyBoardFactory.getFirstChoices());
            chatStates.put(chatId, UserState.AWAITING_NOT);
            sender.execute(sendMessage);

        }
    }

    private void promptWithKeyboardForState(long chatId, String text, ReplyKeyboard YesOrNo, UserState awaitingReorder) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(YesOrNo);
        sender.execute(sendMessage);
        chatStates.put(chatId, awaitingReorder);
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Thank you for your  Visit. See you soon!");
        chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }
    private void replyToName(long chatId, Message message) {
        promptWithKeyboardForState(chatId, "Hello " + message.getText() + ". Enter the number of  your choice\n " +
                        "1. List of our solution \n 2. Our areas of expertise\n 3. List of our clients \n 4. WHO WE ARE \n 5. Contact us",
                keyBoardFactory.getFirstChoices(),
                UserState.FIRST_CHOICES);
    }
    private void replyToNot(long chatId, Message message) {
        promptWithKeyboardForState(chatId, "Hello  Enter the number of  your choice\n " +
                        "1. List of our solution \n 2. Our areas of expertise\n 3. List of our clients \n 4. WHO WE ARE \n 5. Contact us",
                keyBoardFactory.getFirstChoices(),
                UserState.FIRST_CHOICES);
    }
    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }
}

