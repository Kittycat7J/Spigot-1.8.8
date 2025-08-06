package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.ChatComponentScore;
import net.minecraft.server.v1_8_R3.ChatComponentSelector;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.ExceptionEntityNotFound;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.PlayerSelector;

public class ChatComponentUtils {
   public static IChatBaseComponent filterForDisplay(ICommandListener p_filterForDisplay_0_, IChatBaseComponent p_filterForDisplay_1_, Entity p_filterForDisplay_2_) throws CommandException {
      Object object = null;
      if(p_filterForDisplay_1_ instanceof ChatComponentScore) {
         ChatComponentScore chatcomponentscore = (ChatComponentScore)p_filterForDisplay_1_;
         String s = chatcomponentscore.g();
         if(PlayerSelector.isPattern(s)) {
            List list = PlayerSelector.getPlayers(p_filterForDisplay_0_, s, Entity.class);
            if(list.size() != 1) {
               throw new ExceptionEntityNotFound();
            }

            s = ((Entity)list.get(0)).getName();
         }

         object = p_filterForDisplay_2_ != null && s.equals("*")?new ChatComponentScore(p_filterForDisplay_2_.getName(), chatcomponentscore.h()):new ChatComponentScore(s, chatcomponentscore.h());
         ((ChatComponentScore)object).b(chatcomponentscore.getText());
      } else if(p_filterForDisplay_1_ instanceof ChatComponentSelector) {
         String s1 = ((ChatComponentSelector)p_filterForDisplay_1_).g();
         object = PlayerSelector.getPlayerNames(p_filterForDisplay_0_, s1);
         if(object == null) {
            object = new ChatComponentText("");
         }
      } else if(p_filterForDisplay_1_ instanceof ChatComponentText) {
         object = new ChatComponentText(((ChatComponentText)p_filterForDisplay_1_).g());
      } else {
         if(!(p_filterForDisplay_1_ instanceof ChatMessage)) {
            return p_filterForDisplay_1_;
         }

         Object[] aobject = ((ChatMessage)p_filterForDisplay_1_).j();

         for(int i = 0; i < aobject.length; ++i) {
            Object object1 = aobject[i];
            if(object1 instanceof IChatBaseComponent) {
               aobject[i] = filterForDisplay(p_filterForDisplay_0_, (IChatBaseComponent)object1, p_filterForDisplay_2_);
            }
         }

         object = new ChatMessage(((ChatMessage)p_filterForDisplay_1_).i(), aobject);
      }

      ChatModifier chatmodifier = p_filterForDisplay_1_.getChatModifier();
      if(chatmodifier != null) {
         ((IChatBaseComponent)object).setChatModifier(chatmodifier.clone());
      }

      for(IChatBaseComponent ichatbasecomponent : p_filterForDisplay_1_.a()) {
         ((IChatBaseComponent)object).addSibling(filterForDisplay(p_filterForDisplay_0_, ichatbasecomponent, p_filterForDisplay_2_));
      }

      return (IChatBaseComponent)object;
   }
}
