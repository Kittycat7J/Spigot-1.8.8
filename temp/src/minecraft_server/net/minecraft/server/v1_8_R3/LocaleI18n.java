package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.LocaleLanguage;

public class LocaleI18n {
   private static LocaleLanguage a = LocaleLanguage.a();
   private static LocaleLanguage b = new LocaleLanguage();

   public static String get(String p_get_0_) {
      return a.a(p_get_0_);
   }

   public static String a(String p_a_0_, Object... p_a_1_) {
      return a.a(p_a_0_, p_a_1_);
   }

   public static String b(String p_b_0_) {
      return b.a(p_b_0_);
   }

   public static boolean c(String p_c_0_) {
      return a.b(p_c_0_);
   }

   public static long a() {
      return a.c();
   }
}
