package net.minecraft.server.v1_8_R3;

public class SharedConstants {
   public static final char[] allowedCharacters = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

   public static boolean isAllowedChatCharacter(char p_isAllowedChatCharacter_0_) {
      return p_isAllowedChatCharacter_0_ != 167 && p_isAllowedChatCharacter_0_ >= 32 && p_isAllowedChatCharacter_0_ != 127;
   }

   public static String a(String p_a_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(char c0 : p_a_0_.toCharArray()) {
         if(isAllowedChatCharacter(c0)) {
            stringbuilder.append(c0);
         }
      }

      return stringbuilder.toString();
   }
}
