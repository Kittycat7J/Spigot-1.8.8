package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatMessage;

public class ChatMessageException extends IllegalArgumentException {
   public ChatMessageException(ChatMessage p_i943_1_, String p_i943_2_) {
      super(String.format("Error parsing: %s: %s", new Object[]{p_i943_1_, p_i943_2_}));
   }

   public ChatMessageException(ChatMessage p_i944_1_, int p_i944_2_) {
      super(String.format("Invalid index %d requested for %s", new Object[]{Integer.valueOf(p_i944_2_), p_i944_1_}));
   }

   public ChatMessageException(ChatMessage p_i945_1_, Throwable p_i945_2_) {
      super(String.format("Error while parsing: %s", new Object[]{p_i945_1_}), p_i945_2_);
   }
}
