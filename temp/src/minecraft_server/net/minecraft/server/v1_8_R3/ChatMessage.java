package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.v1_8_R3.ChatBaseComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessageException;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.LocaleI18n;

public class ChatMessage extends ChatBaseComponent {
   private final String d;
   private final Object[] e;
   private final Object f = new Object();
   private long g = -1L;
   List<IChatBaseComponent> b = Lists.<IChatBaseComponent>newArrayList();
   public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public ChatMessage(String p_i942_1_, Object... p_i942_2_) {
      this.d = p_i942_1_;
      this.e = p_i942_2_;

      for(Object object : p_i942_2_) {
         if(object instanceof IChatBaseComponent) {
            ((IChatBaseComponent)object).getChatModifier().setChatModifier(this.getChatModifier());
         }
      }

   }

   synchronized void g() {
      synchronized(this.f) {
         long i = LocaleI18n.a();
         if(i == this.g) {
            return;
         }

         this.g = i;
         this.b.clear();
      }

      try {
         this.b(LocaleI18n.get(this.d));
      } catch (ChatMessageException chatmessageexception) {
         this.b.clear();

         try {
            this.b(LocaleI18n.b(this.d));
         } catch (ChatMessageException var6) {
            throw chatmessageexception;
         }
      }

   }

   protected void b(String p_b_1_) {
      boolean flag = false;
      Matcher matcher = c.matcher(p_b_1_);
      int i = 0;
      int j = 0;

      try {
         int l;
         for(; matcher.find(j); j = l) {
            int k = matcher.start();
            l = matcher.end();
            if(k > j) {
               ChatComponentText chatcomponenttext = new ChatComponentText(String.format(p_b_1_.substring(j, k), new Object[0]));
               chatcomponenttext.getChatModifier().setChatModifier(this.getChatModifier());
               this.b.add(chatcomponenttext);
            }

            String s2 = matcher.group(2);
            String s = p_b_1_.substring(k, l);
            if("%".equals(s2) && "%%".equals(s)) {
               ChatComponentText chatcomponenttext2 = new ChatComponentText("%");
               chatcomponenttext2.getChatModifier().setChatModifier(this.getChatModifier());
               this.b.add(chatcomponenttext2);
            } else {
               if(!"s".equals(s2)) {
                  throw new ChatMessageException(this, "Unsupported format: \'" + s + "\'");
               }

               String s1 = matcher.group(1);
               int i1 = s1 != null?Integer.parseInt(s1) - 1:i++;
               if(i1 < this.e.length) {
                  this.b.add(this.a(i1));
               }
            }
         }

         if(j < p_b_1_.length()) {
            ChatComponentText chatcomponenttext1 = new ChatComponentText(String.format(p_b_1_.substring(j), new Object[0]));
            chatcomponenttext1.getChatModifier().setChatModifier(this.getChatModifier());
            this.b.add(chatcomponenttext1);
         }

      } catch (IllegalFormatException illegalformatexception) {
         throw new ChatMessageException(this, illegalformatexception);
      }
   }

   private IChatBaseComponent a(int p_a_1_) {
      if(p_a_1_ >= this.e.length) {
         throw new ChatMessageException(this, p_a_1_);
      } else {
         Object object = this.e[p_a_1_];
         Object object1;
         if(object instanceof IChatBaseComponent) {
            object1 = (IChatBaseComponent)object;
         } else {
            object1 = new ChatComponentText(object == null?"null":object.toString());
            ((IChatBaseComponent)object1).getChatModifier().setChatModifier(this.getChatModifier());
         }

         return (IChatBaseComponent)object1;
      }
   }

   public IChatBaseComponent setChatModifier(ChatModifier p_setChatModifier_1_) {
      super.setChatModifier(p_setChatModifier_1_);

      for(Object object : this.e) {
         if(object instanceof IChatBaseComponent) {
            ((IChatBaseComponent)object).getChatModifier().setChatModifier(this.getChatModifier());
         }
      }

      if(this.g > -1L) {
         for(IChatBaseComponent ichatbasecomponent : this.b) {
            ichatbasecomponent.getChatModifier().setChatModifier(p_setChatModifier_1_);
         }
      }

      return this;
   }

   public Iterator<IChatBaseComponent> iterator() {
      this.g();
      return Iterators.<IChatBaseComponent>concat(a(this.b), a(this.a));
   }

   public String getText() {
      this.g();
      StringBuilder stringbuilder = new StringBuilder();

      for(IChatBaseComponent ichatbasecomponent : this.b) {
         stringbuilder.append(ichatbasecomponent.getText());
      }

      return stringbuilder.toString();
   }

   public ChatMessage h() {
      Object[] aobject = new Object[this.e.length];

      for(int i = 0; i < this.e.length; ++i) {
         if(this.e[i] instanceof IChatBaseComponent) {
            aobject[i] = ((IChatBaseComponent)this.e[i]).f();
         } else {
            aobject[i] = this.e[i];
         }
      }

      ChatMessage chatmessage = new ChatMessage(this.d, aobject);
      chatmessage.setChatModifier(this.getChatModifier().clone());

      for(IChatBaseComponent ichatbasecomponent : this.a()) {
         chatmessage.addSibling(ichatbasecomponent.f());
      }

      return chatmessage;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChatMessage)) {
         return false;
      } else {
         ChatMessage chatmessage = (ChatMessage)p_equals_1_;
         return Arrays.equals(this.e, chatmessage.e) && this.d.equals(chatmessage.d) && super.equals(p_equals_1_);
      }
   }

   public int hashCode() {
      int i = super.hashCode();
      i = 31 * i + this.d.hashCode();
      i = 31 * i + Arrays.hashCode(this.e);
      return i;
   }

   public String toString() {
      return "TranslatableComponent{key=\'" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + this.getChatModifier() + '}';
   }

   public String i() {
      return this.d;
   }

   public Object[] j() {
      return this.e;
   }
}
