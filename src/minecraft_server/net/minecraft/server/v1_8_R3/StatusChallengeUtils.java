package net.minecraft.server.v1_8_R3;

import com.google.common.base.Charsets;

public class StatusChallengeUtils {
   public static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String a(byte[] p_a_0_, int p_a_1_, int p_a_2_) {
      int i = p_a_2_ - 1;

      int j;
      for(j = p_a_1_ > i?i:p_a_1_; 0 != p_a_0_[j] && j < i; ++j) {
         ;
      }

      return new String(p_a_0_, p_a_1_, j - p_a_1_, Charsets.UTF_8);
   }

   public static int b(byte[] p_b_0_, int p_b_1_) {
      return b(p_b_0_, p_b_1_, p_b_0_.length);
   }

   public static int b(byte[] p_b_0_, int p_b_1_, int p_b_2_) {
      return 0 > p_b_2_ - p_b_1_ - 4?0:p_b_0_[p_b_1_ + 3] << 24 | (p_b_0_[p_b_1_ + 2] & 255) << 16 | (p_b_0_[p_b_1_ + 1] & 255) << 8 | p_b_0_[p_b_1_] & 255;
   }

   public static int c(byte[] p_c_0_, int p_c_1_, int p_c_2_) {
      return 0 > p_c_2_ - p_c_1_ - 4?0:p_c_0_[p_c_1_] << 24 | (p_c_0_[p_c_1_ + 1] & 255) << 16 | (p_c_0_[p_c_1_ + 2] & 255) << 8 | p_c_0_[p_c_1_ + 3] & 255;
   }

   public static String a(byte p_a_0_) {
      return "" + a[(p_a_0_ & 240) >>> 4] + a[p_a_0_ & 15];
   }
}
