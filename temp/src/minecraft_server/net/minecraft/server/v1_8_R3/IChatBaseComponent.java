package net.minecraft.server.v1_8_R3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.ChatComponentScore;
import net.minecraft.server.v1_8_R3.ChatComponentSelector;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatDeserializer;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.ChatTypeAdapterFactory;

public interface IChatBaseComponent extends Iterable<IChatBaseComponent> {
   IChatBaseComponent setChatModifier(ChatModifier var1);

   ChatModifier getChatModifier();

   IChatBaseComponent a(String var1);

   IChatBaseComponent addSibling(IChatBaseComponent var1);

   String getText();

   String c();

   List<IChatBaseComponent> a();

   IChatBaseComponent f();

   public static class ChatSerializer implements JsonDeserializer<IChatBaseComponent>, JsonSerializer<IChatBaseComponent> {
      private static final Gson a;

      public IChatBaseComponent a(JsonElement p_a_1_, Type p_a_2_, JsonDeserializationContext p_a_3_) throws JsonParseException {
         if(p_a_1_.isJsonPrimitive()) {
            return new ChatComponentText(p_a_1_.getAsString());
         } else if(!p_a_1_.isJsonObject()) {
            if(p_a_1_.isJsonArray()) {
               JsonArray jsonarray1 = p_a_1_.getAsJsonArray();
               IChatBaseComponent ichatbasecomponent = null;

               for(JsonElement jsonelement : jsonarray1) {
                  IChatBaseComponent ichatbasecomponent1 = this.a((JsonElement)jsonelement, (Type)jsonelement.getClass(), (JsonDeserializationContext)p_a_3_);
                  if(ichatbasecomponent == null) {
                     ichatbasecomponent = ichatbasecomponent1;
                  } else {
                     ichatbasecomponent.addSibling(ichatbasecomponent1);
                  }
               }

               return ichatbasecomponent;
            } else {
               throw new JsonParseException("Don\'t know how to turn " + p_a_1_.toString() + " into a Component");
            }
         } else {
            JsonObject jsonobject = p_a_1_.getAsJsonObject();
            Object object;
            if(jsonobject.has("text")) {
               object = new ChatComponentText(jsonobject.get("text").getAsString());
            } else if(jsonobject.has("translate")) {
               String s = jsonobject.get("translate").getAsString();
               if(jsonobject.has("with")) {
                  JsonArray jsonarray = jsonobject.getAsJsonArray("with");
                  Object[] aobject = new Object[jsonarray.size()];

                  for(int i = 0; i < aobject.length; ++i) {
                     aobject[i] = this.a(jsonarray.get(i), p_a_2_, p_a_3_);
                     if(aobject[i] instanceof ChatComponentText) {
                        ChatComponentText chatcomponenttext = (ChatComponentText)aobject[i];
                        if(chatcomponenttext.getChatModifier().g() && chatcomponenttext.a().isEmpty()) {
                           aobject[i] = chatcomponenttext.g();
                        }
                     }
                  }

                  object = new ChatMessage(s, aobject);
               } else {
                  object = new ChatMessage(s, new Object[0]);
               }
            } else if(jsonobject.has("score")) {
               JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
               if(!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               object = new ChatComponentScore(ChatDeserializer.h(jsonobject1, "name"), ChatDeserializer.h(jsonobject1, "objective"));
               if(jsonobject1.has("value")) {
                  ((ChatComponentScore)object).b(ChatDeserializer.h(jsonobject1, "value"));
               }
            } else {
               if(!jsonobject.has("selector")) {
                  throw new JsonParseException("Don\'t know how to turn " + p_a_1_.toString() + " into a Component");
               }

               object = new ChatComponentSelector(ChatDeserializer.h(jsonobject, "selector"));
            }

            if(jsonobject.has("extra")) {
               JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
               if(jsonarray2.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int j = 0; j < jsonarray2.size(); ++j) {
                  ((IChatBaseComponent)object).addSibling(this.a(jsonarray2.get(j), p_a_2_, p_a_3_));
               }
            }

            ((IChatBaseComponent)object).setChatModifier((ChatModifier)p_a_3_.deserialize(p_a_1_, ChatModifier.class));
            return (IChatBaseComponent)object;
         }
      }

      private void a(ChatModifier p_a_1_, JsonObject p_a_2_, JsonSerializationContext p_a_3_) {
         JsonElement jsonelement = p_a_3_.serialize(p_a_1_);
         if(jsonelement.isJsonObject()) {
            JsonObject jsonobject = (JsonObject)jsonelement;

            for(Entry entry : jsonobject.entrySet()) {
               p_a_2_.add((String)entry.getKey(), (JsonElement)entry.getValue());
            }
         }

      }

      public JsonElement a(IChatBaseComponent p_a_1_, Type p_a_2_, JsonSerializationContext p_a_3_) {
         if(p_a_1_ instanceof ChatComponentText && p_a_1_.getChatModifier().g() && p_a_1_.a().isEmpty()) {
            return new JsonPrimitive(((ChatComponentText)p_a_1_).g());
         } else {
            JsonObject jsonobject = new JsonObject();
            if(!p_a_1_.getChatModifier().g()) {
               this.a(p_a_1_.getChatModifier(), jsonobject, p_a_3_);
            }

            if(!p_a_1_.a().isEmpty()) {
               JsonArray jsonarray = new JsonArray();

               for(IChatBaseComponent ichatbasecomponent : p_a_1_.a()) {
                  jsonarray.add(this.a((IChatBaseComponent)ichatbasecomponent, (Type)ichatbasecomponent.getClass(), (JsonSerializationContext)p_a_3_));
               }

               jsonobject.add("extra", jsonarray);
            }

            if(p_a_1_ instanceof ChatComponentText) {
               jsonobject.addProperty("text", ((ChatComponentText)p_a_1_).g());
            } else if(p_a_1_ instanceof ChatMessage) {
               ChatMessage chatmessage = (ChatMessage)p_a_1_;
               jsonobject.addProperty("translate", chatmessage.i());
               if(chatmessage.j() != null && chatmessage.j().length > 0) {
                  JsonArray jsonarray1 = new JsonArray();

                  for(Object object : chatmessage.j()) {
                     if(object instanceof IChatBaseComponent) {
                        jsonarray1.add(this.a((IChatBaseComponent)((IChatBaseComponent)object), (Type)object.getClass(), (JsonSerializationContext)p_a_3_));
                     } else {
                        jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                     }
                  }

                  jsonobject.add("with", jsonarray1);
               }
            } else if(p_a_1_ instanceof ChatComponentScore) {
               ChatComponentScore chatcomponentscore = (ChatComponentScore)p_a_1_;
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("name", chatcomponentscore.g());
               jsonobject1.addProperty("objective", chatcomponentscore.h());
               jsonobject1.addProperty("value", chatcomponentscore.getText());
               jsonobject.add("score", jsonobject1);
            } else {
               if(!(p_a_1_ instanceof ChatComponentSelector)) {
                  throw new IllegalArgumentException("Don\'t know how to serialize " + p_a_1_ + " as a Component");
               }

               ChatComponentSelector chatcomponentselector = (ChatComponentSelector)p_a_1_;
               jsonobject.addProperty("selector", chatcomponentselector.g());
            }

            return jsonobject;
         }
      }

      public static String a(IChatBaseComponent p_a_0_) {
         return a.toJson((Object)p_a_0_);
      }

      public static IChatBaseComponent a(String p_a_0_) {
         return (IChatBaseComponent)a.fromJson(p_a_0_, IChatBaseComponent.class);
      }

      static {
         GsonBuilder gsonbuilder = new GsonBuilder();
         gsonbuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer());
         gsonbuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer());
         gsonbuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
         a = gsonbuilder.create();
      }
   }
}
