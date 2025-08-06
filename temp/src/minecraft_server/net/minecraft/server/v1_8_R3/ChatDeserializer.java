package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

public class ChatDeserializer {
   public static boolean d(JsonObject p_d_0_, String p_d_1_) {
      return !g(p_d_0_, p_d_1_)?false:p_d_0_.get(p_d_1_).isJsonArray();
   }

   public static boolean g(JsonObject p_g_0_, String p_g_1_) {
      return p_g_0_ == null?false:p_g_0_.get(p_g_1_) != null;
   }

   public static String a(JsonElement p_a_0_, String p_a_1_) {
      if(p_a_0_.isJsonPrimitive()) {
         return p_a_0_.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + p_a_1_ + " to be a string, was " + d(p_a_0_));
      }
   }

   public static String h(JsonObject p_h_0_, String p_h_1_) {
      if(p_h_0_.has(p_h_1_)) {
         return a(p_h_0_.get(p_h_1_), p_h_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_h_1_ + ", expected to find a string");
      }
   }

   public static boolean b(JsonElement p_b_0_, String p_b_1_) {
      if(p_b_0_.isJsonPrimitive()) {
         return p_b_0_.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + p_b_1_ + " to be a Boolean, was " + d(p_b_0_));
      }
   }

   public static boolean a(JsonObject p_a_0_, String p_a_1_, boolean p_a_2_) {
      return p_a_0_.has(p_a_1_)?b(p_a_0_.get(p_a_1_), p_a_1_):p_a_2_;
   }

   public static float d(JsonElement p_d_0_, String p_d_1_) {
      if(p_d_0_.isJsonPrimitive() && p_d_0_.getAsJsonPrimitive().isNumber()) {
         return p_d_0_.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + p_d_1_ + " to be a Float, was " + d(p_d_0_));
      }
   }

   public static float a(JsonObject p_a_0_, String p_a_1_, float p_a_2_) {
      return p_a_0_.has(p_a_1_)?d(p_a_0_.get(p_a_1_), p_a_1_):p_a_2_;
   }

   public static int f(JsonElement p_f_0_, String p_f_1_) {
      if(p_f_0_.isJsonPrimitive() && p_f_0_.getAsJsonPrimitive().isNumber()) {
         return p_f_0_.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + p_f_1_ + " to be a Int, was " + d(p_f_0_));
      }
   }

   public static int m(JsonObject p_m_0_, String p_m_1_) {
      if(p_m_0_.has(p_m_1_)) {
         return f(p_m_0_.get(p_m_1_), p_m_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_m_1_ + ", expected to find a Int");
      }
   }

   public static int a(JsonObject p_a_0_, String p_a_1_, int p_a_2_) {
      return p_a_0_.has(p_a_1_)?f(p_a_0_.get(p_a_1_), p_a_1_):p_a_2_;
   }

   public static JsonObject l(JsonElement p_l_0_, String p_l_1_) {
      if(p_l_0_.isJsonObject()) {
         return p_l_0_.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + p_l_1_ + " to be a JsonObject, was " + d(p_l_0_));
      }
   }

   public static JsonArray m(JsonElement p_m_0_, String p_m_1_) {
      if(p_m_0_.isJsonArray()) {
         return p_m_0_.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + p_m_1_ + " to be a JsonArray, was " + d(p_m_0_));
      }
   }

   public static JsonArray t(JsonObject p_t_0_, String p_t_1_) {
      if(p_t_0_.has(p_t_1_)) {
         return m(p_t_0_.get(p_t_1_), p_t_1_);
      } else {
         throw new JsonSyntaxException("Missing " + p_t_1_ + ", expected to find a JsonArray");
      }
   }

   public static String d(JsonElement p_d_0_) {
      String s = StringUtils.abbreviateMiddle(String.valueOf(p_d_0_), "...", 10);
      if(p_d_0_ == null) {
         return "null (missing)";
      } else if(p_d_0_.isJsonNull()) {
         return "null (json)";
      } else if(p_d_0_.isJsonArray()) {
         return "an array (" + s + ")";
      } else if(p_d_0_.isJsonObject()) {
         return "an object (" + s + ")";
      } else {
         if(p_d_0_.isJsonPrimitive()) {
            JsonPrimitive jsonprimitive = p_d_0_.getAsJsonPrimitive();
            if(jsonprimitive.isNumber()) {
               return "a number (" + s + ")";
            }

            if(jsonprimitive.isBoolean()) {
               return "a boolean (" + s + ")";
            }
         }

         return s;
      }
   }
}
