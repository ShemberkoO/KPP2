package main;

import com.google.gson.*;
import models.SuperTask;
import models.Task;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class TaskDeserializer implements JsonDeserializer<Task> {

    @Override
    public Task deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Task task;

        if (jsonObject.has("subTasks")) {
            // Якщо є поле "subTasks", це SuperTask
            task = context.deserialize(json, SuperTask.class);
        } else {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();
            task =gson.fromJson(json, Task.class);
        }
        return task;
    }
}
