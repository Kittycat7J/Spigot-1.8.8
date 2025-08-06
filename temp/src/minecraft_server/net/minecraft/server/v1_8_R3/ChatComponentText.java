package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public class ChatComponentText extends ChatBaseComponent {
   private final String b;

   public ChatComponentText(String p_i941_1_) {
      this.b = p_i941_1_;
   }

   public String g() {
      return this.b;
   }

   public String getText() {
      return this.b;
   }

   public ChatComponentText h() {
      ChatComponentText chatcomponenttext = new ChatComponentText(this.b);
      chatcomponenttext.setChatModifier(this.getChatModifier().clone());

      for(IChatBaseComponent ichatbasecomponent : this.a()) {
         chatcomponenttext.addSibling(ichatbasecomponent.f());
      }

      return chatcomponenttext;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChatComponentText)) {
         return false;
      } else {
         ChatComponentText chatcomponenttext = (ChatComponentText)p_equals_1_;
         return this.b.equals(chatcomponenttext.g()) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "TextComponent{text=\'" + this.b + '\'' + ", siblings=" + this.a + ", style=" + this.getChatModifier() + '}';
   }
}
