package net.minecraft.server.v1_8_R3;

import java.util.Collection;
import java.util.Random;

public class WeightedRandom {
   public static int a(Collection<? extends WeightedRandom.WeightedRandomChoice> p_a_0_) {
      int i = 0;

      for(WeightedRandom.WeightedRandomChoice weightedrandom$weightedrandomchoice : p_a_0_) {
         i += weightedrandom$weightedrandomchoice.a;
      }

      return i;
   }

   public static <T extends WeightedRandom.WeightedRandomChoice> T a(Random p_a_0_, Collection<T> p_a_1_, int p_a_2_) {
      if(p_a_2_ <= 0) {
         throw new IllegalArgumentException();
      } else {
         int i = p_a_0_.nextInt(p_a_2_);
         return a(p_a_1_, i);
      }
   }

   public static <T extends WeightedRandom.WeightedRandomChoice> T a(Collection<T> p_a_0_, int p_a_1_) {
      for(WeightedRandom.WeightedRandomChoice weightedrandom$weightedrandomchoice : p_a_0_) {
         p_a_1_ -= weightedrandom$weightedrandomchoice.a;
         if(p_a_1_ < 0) {
            return (T)weightedrandom$weightedrandomchoice;
         }
      }

      return (T)null;
   }

   public static <T extends WeightedRandom.WeightedRandomChoice> T a(Random p_a_0_, Collection<T> p_a_1_) {
      return a(p_a_0_, p_a_1_, a(p_a_1_));
   }

   public static class WeightedRandomChoice {
      protected int a;

      public WeightedRandomChoice(int p_i1123_1_) {
         this.a = p_i1123_1_;
      }
   }
}
