package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;

public class ChatClickable {
   private final ChatClickable.EnumClickAction a;
   private final String b;

   public ChatClickable(ChatClickable.EnumClickAction p_i936_1_, String p_i936_2_) {
      this.a = p_i936_1_;
      this.b = p_i936_2_;
   }

   public ChatClickable.EnumClickAction a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         ChatClickable chatclickable = (ChatClickable)p_equals_1_;
         if(this.a != chatclickable.a) {
            return false;
         } else {
            if(this.b != null) {
               if(!this.b.equals(chatclickable.b)) {
                  return false;
               }
            } else if(chatclickable.b != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.a + ", value=\'" + this.b + '\'' + '}';
   }

   public int hashCode() {
      int i = this.a.hashCode();
      i = 31 * i + (this.b != null?this.b.hashCode():0);
      return i;
   }

   public static enum EnumClickAction {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      TWITCH_USER_INFO("twitch_user_info", false),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true);

      private static final Map<String, ChatClickable.EnumClickAction> g = Maps.<String, ChatClickable.EnumClickAction>newHashMap();
      private final boolean h;
      private final String i;

      private EnumClickAction(String p_i935_3_, boolean p_i935_4_) {
         this.i = p_i935_3_;
         this.h = p_i935_4_;
      }

      public boolean a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public static ChatClickable.EnumClickAction a(String p_a_0_) {
         return (ChatClickable.EnumClickAction)g.get(p_a_0_);
      }

      static {
         for(ChatClickable.EnumClickAction chatclickable$enumclickaction : values()) {
            g.put(chatclickable$enumclickaction.b(), chatclickable$enumclickaction);
         }

      }
   }
}
