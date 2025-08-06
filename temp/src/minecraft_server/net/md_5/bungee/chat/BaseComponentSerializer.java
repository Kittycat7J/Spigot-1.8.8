package net.md_5.bungee.chat;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.HashSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.chat.ComponentSerializer;

public class BaseComponentSerializer {
   protected void deserialize(JsonObject object, BaseComponent component, JsonDeserializationContext context) {
      if(object.has("color")) {
         component.setColor(ChatColor.valueOf(object.get("color").getAsString().toUpperCase()));
      }

      if(object.has("bold")) {
         component.setBold(Boolean.valueOf(object.get("bold").getAsBoolean()));
      }

      if(object.has("italic")) {
         component.setItalic(Boolean.valueOf(object.get("italic").getAsBoolean()));
      }

      if(object.has("underlined")) {
         component.setUnderlined(Boolean.valueOf(object.get("underlined").getAsBoolean()));
      }

      if(object.has("strikethrough")) {
         component.setStrikethrough(Boolean.valueOf(object.get("strikethrough").getAsBoolean()));
      }

      if(object.has("obfuscated")) {
         component.setObfuscated(Boolean.valueOf(object.get("obfuscated").getAsBoolean()));
      }

      if(object.has("insertion")) {
         component.setInsertion(object.get("insertion").getAsString());
      }

      if(object.has("extra")) {
         component.setExtra(Arrays.asList((Object[])context.deserialize(object.get("extra"), BaseComponent[].class)));
      }

      if(object.has("clickEvent")) {
         JsonObject jsonobject = object.getAsJsonObject("clickEvent");
         component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(jsonobject.get("action").getAsString().toUpperCase()), jsonobject.get("value").getAsString()));
      }

      if(object.has("hoverEvent")) {
         JsonObject jsonobject1 = object.getAsJsonObject("hoverEvent");
         BaseComponent[] abasecomponent;
         if(jsonobject1.get("value").isJsonArray()) {
            abasecomponent = (BaseComponent[])context.deserialize(jsonobject1.get("value"), BaseComponent[].class);
         } else {
            abasecomponent = new BaseComponent[]{(BaseComponent)context.deserialize(jsonobject1.get("value"), BaseComponent.class)};
         }

         component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(jsonobject1.get("action").getAsString().toUpperCase()), abasecomponent));
      }

   }

   protected void serialize(JsonObject object, BaseComponent component, JsonSerializationContext context) {
      boolean flag = false;
      if(ComponentSerializer.serializedComponents.get() == null) {
         flag = true;
         ComponentSerializer.serializedComponents.set(new HashSet());
      }

      try {
         Preconditions.checkArgument(!((HashSet)ComponentSerializer.serializedComponents.get()).contains(component), "Component loop");
         ((HashSet)ComponentSerializer.serializedComponents.get()).add(component);
         if(component.getColorRaw() != null) {
            object.addProperty("color", component.getColorRaw().getName());
         }

         if(component.isBoldRaw() != null) {
            object.addProperty("bold", component.isBoldRaw());
         }

         if(component.isItalicRaw() != null) {
            object.addProperty("italic", component.isItalicRaw());
         }

         if(component.isUnderlinedRaw() != null) {
            object.addProperty("underlined", component.isUnderlinedRaw());
         }

         if(component.isStrikethroughRaw() != null) {
            object.addProperty("strikethrough", component.isStrikethroughRaw());
         }

         if(component.isObfuscatedRaw() != null) {
            object.addProperty("obfuscated", component.isObfuscatedRaw());
         }

         if(component.getInsertion() != null) {
            object.addProperty("insertion", component.getInsertion());
         }

         if(component.getExtra() != null) {
            object.add("extra", context.serialize(component.getExtra()));
         }

         if(component.getClickEvent() != null) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("action", component.getClickEvent().getAction().toString().toLowerCase());
            jsonobject.addProperty("value", component.getClickEvent().getValue());
            object.add("clickEvent", jsonobject);
         }

         if(component.getHoverEvent() != null) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("action", component.getHoverEvent().getAction().toString().toLowerCase());
            jsonobject1.add("value", context.serialize(component.getHoverEvent().getValue()));
            object.add("hoverEvent", jsonobject1);
         }
      } finally {
         ((HashSet)ComponentSerializer.serializedComponents.get()).remove(component);
         if(flag) {
            ComponentSerializer.serializedComponents.set((Object)null);
         }

      }

   }
}
