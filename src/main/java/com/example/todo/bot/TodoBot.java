package com.example.todo.bot;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TodoBot extends TelegramLongPollingBot {

    private final TodoRepository todoRepository;

    public TodoBot(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public String getBotUsername() {
        return "TodoHelperBot"; // 🔹 Имя бота (из BotFather)
    }

    @Override
    public String getBotToken() {
        return "8280805304:AAEOlBP0tz2smL66MBRJYEfKk2X0MR7JgSQ"; // 🔹 Токен от BotFather
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            String response;
            if (message.startsWith("/add ")) {
                String title = message.substring(5);
                TodoItem item = new TodoItem();
                item.setTitle(title);
                item.setCompleted(false);
                todoRepository.save(item);
                response = "✅ Задача добавлена: " + title;
            } else if (message.equals("/list")) {
                List<TodoItem> tasks = todoRepository.findAll();
                if (tasks.isEmpty()) {
                    response = "Нет задач 👌";
                } else {
                    StringBuilder sb = new StringBuilder("📋 Список задач:\n");
                    tasks.forEach(t -> sb.append(t.getId())
                            .append(". ")
                            .append(t.getTitle())
                            .append(t.isCompleted() ? " ✅" : " ❌")
                            .append("\n"));
                    response = sb.toString();
                }
            } else if (message.startsWith("/done ")) {
                try {
                    Long id = Long.parseLong(message.substring(6).trim());
                    TodoItem item = todoRepository.findById(id).orElse(null);
                    if (item != null) {
                        item.setCompleted(true);
                        todoRepository.save(item);
                        response = "✅ Задача " + id + " выполнена!";
                    } else {
                        response = "⚠️ Задача с id=" + id + " не найдена.";
                    }
                } catch (Exception e) {
                    response = "⚠️ Используй: /done <id>";
                }
            } else if (message.startsWith("/delete ")) {
                try {
                    Long id = Long.parseLong(message.substring(8).trim());
                    if (todoRepository.existsById(id)) {
                        todoRepository.deleteById(id);
                        response = "🗑 Задача " + id + " удалена.";
                    } else {
                        response = "⚠️ Задача с id=" + id + " не найдена.";
                    }
                } catch (Exception e) {
                    response = "⚠️ Используй: /delete <id>";
                }
            } else {
                response = "Доступные команды:\n" +
                        "/add <текст> — добавить задачу\n" +
                        "/list — список задач\n" +
                        "/done <id> — отметить выполненной\n" +
                        "/delete <id> — удалить задачу";
            }

            try {
                execute(new SendMessage(chatId.toString(), response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}