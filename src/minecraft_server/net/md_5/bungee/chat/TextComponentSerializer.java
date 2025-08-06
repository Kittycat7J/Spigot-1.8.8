package net.md_5.bungee.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentSerializer extends BaseComponentSerializer implements JsonSerializer<TextComponent>, JsonDeserializer<TextComponent>
{
    public TextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        TextComponent textcomponent = new TextComponent();
        JsonObject jsonobject = json.getAsJsonObject();
        this.deserialize((JsonObject)jsonobject, (BaseComponent)textcomponent, context);
        textcomponent.setText(jsonobject.get("text").getAsString());
        return textcomponent;
    }

    public JsonElement serialize(TextComponent src, Type typeOfSrc, JsonSerializationContext context)
    {
        List<BaseComponent> list = src.getExtra();

        if (src.hasFormatting() || list != null && !list.isEmpty())
        {
            JsonObject jsonobject = new JsonObject();
            this.serialize((JsonObject)jsonobject, (BaseComponent)src, context);
            jsonobject.addProperty("text", src.getText());
            return jsonobject;
        }
        else
        {
            return new JsonPrimitive(src.getText());
        }
    }
}
