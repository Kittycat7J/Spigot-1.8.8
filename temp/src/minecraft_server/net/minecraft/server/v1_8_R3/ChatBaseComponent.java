package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public abstract class ChatBaseComponent implements IChatBaseComponent {
   protected List<IChatBaseComponent> a = Lists.<IChatBaseComponent>newArrayList();
   private ChatModifier b;

   public IChatBaseComponent addSibling(IChatBaseComponent p_addSibling_1_) {
      p_addSibling_1_.getChatModifier().setChatModifier(this.getChatModifier());
      this.a.add(p_addSibling_1_);
      return this;
   }

   public List<IChatBaseComponent> a() {
      return this.a;
   }

   public IChatBaseComponent a(String p_a_1_) {
      return this.addSibling(new ChatComponentText(p_a_1_));
   }

   public IChatBaseComponent setChatModifier(ChatModifier p_setChatModifier_1_) {
      this.b = p_setChatModifier_1_;

      for(IChatBaseComponent ichatbasecomponent : this.a) {
         ichatbasecomponent.getChatModifier().setChatModifier(this.getChatModifier());
      }

      return this;
   }

   public ChatModifier getChatModifier() {
      if(this.b == null) {
         this.b = new ChatModifier();

         for(IChatBaseComponent ichatbasecomponent : this.a) {
            ichatbasecomponent.getChatModifier().setChatModifier(this.b);
         }
      }

      return this.b;
   }

   public Iterator<IChatBaseComponent> iterator() {
      return Iterators.<IChatBaseComponent>concat(Iterators.<IChatBaseComponent>forArray(new ChatBaseComponent[]{this}), a((Iterable)this.a));
   }

   public final String c() {
      StringBuilder stringbuilder = new StringBuilder();

      for(IChatBaseComponent ichatbasecomponent : this) {
         stringbuilder.append(ichatbasecomponent.getText());
      }

      return stringbuilder.toString();
   }

   public static Iterator<IChatBaseComponent> a(Iterable<IChatBaseComponent> p_a_0_) {
      Iterator iterator = Iterators.concat(Iterators.transform(p_a_0_.iterator(), new Function() {
         public Iterator<IChatBaseComponent> a(IChatBaseComponent p_a_1_) {
            return p_a_1_.iterator();
         }

         public Object apply(Object p_apply_1_) {
            return this.a((IChatBaseComponent)p_apply_1_);
         }
      }));
      iterator = Iterators.transform(iterator, new Function() {
         public IChatBaseComponent a(IChatBaseComponent p_a_1_) {
            IChatBaseComponent ichatbasecomponent = p_a_1_.f();
            ichatbasecomponent.setChatModifier(ichatbasecomponent.getChatModifier().n());
            return ichatbasecomponent;
         }

         public Object apply(Object p_apply_1_) {
            return this.a((IChatBaseComponent)p_apply_1_);
         }
      });
      return iterator;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChatBaseComponent)) {
         return false;
      } else {
         ChatBaseComponent chatbasecomponent = (ChatBaseComponent)p_equals_1_;
         return this.a.equals(chatbasecomponent.a) && this.getChatModifier().equals(chatbasecomponent.getChatModifier());
      }
   }

   public int hashCode() {
      return 31 * this.getChatModifier().hashCode() + this.a.hashCode();
   }

   public String toString() {
      return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
   }
}
