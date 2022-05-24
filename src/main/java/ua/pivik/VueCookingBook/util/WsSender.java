package ua.pivik.VueCookingBook.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ua.pivik.VueCookingBook.dto.EventType;
import ua.pivik.VueCookingBook.dto.ObjectType;
import ua.pivik.VueCookingBook.dto.WsEventDto;

import java.util.function.BiConsumer;

/**
 * @autor Alexander Pivovarov
 */
@Component
public class WsSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WsSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    // используем метод для отправки любого типа объекта Message, User, Other с разным JsonView
    // на момент отправки мы знаем какой объект мы отправляем - аргументы метода
    // Consumer - замыкание с одним аргуметом
    // BiConsumer - замыкание с двумя аргументами
    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view) {

        // view идет в mapper для создания нового writera
        ObjectWriter writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);
        // замыкание!! - возвращаем из метода функцию которая ничего не возвращаем а обрабатывает агргументы
        // в данном случае BiConsumer - замыкание с двумя аргументами
        return (EventType eventType, T payload) -> {
            String value = null;

            // перехватываем ошибку кторая упадет пользователю в консоль
            try {
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // создаем новый объект с данными и передаем через SimpMessagingTemplate
            template.convertAndSend(
                    "/topic/activity",   // куда
                    new WsEventDto(objectType, eventType, value)  //что
            );
        };
    }
}
