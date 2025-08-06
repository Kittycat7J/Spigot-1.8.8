package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class PotionBrewer {
   public static final String a = null;
   public static final String b = "-0+1-2-3&4-4+13";
   public static final String c = "+0-1-2-3&4-4+13";
   public static final String d = "-0-1+2-3&4-4+13";
   public static final String e = "-0+3-4+13";
   public static final String f = "+0-1+2-3&4-4+13";
   public static final String g = "+0-1-2+3&4-4+13";
   public static final String h = "+0+1-2-3&4-4+13";
   public static final String i = "-5+6-7";
   public static final String j = "+5-6-7";
   public static final String k = "+14&13-13";
   public static final String l = "-0+1+2-3+13&4-4";
   public static final String m = "+0-1+2+3+13&4-4";
   public static final String n = "+0+1-2+3&4-4+13";
   private static final Map<Integer, String> effectDurations = Maps.<Integer, String>newHashMap();
   private static final Map<Integer, String> effectAmplifiers = Maps.<Integer, String>newHashMap();
   private static final Map<Integer, Integer> q = Maps.<Integer, Integer>newHashMap();
   private static final String[] appearances = new String[]{"potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky"};

   public static boolean a(int p_a_0_, int p_a_1_) {
      return (p_a_0_ & 1 << p_a_1_) != 0;
   }

   private static int c(int p_c_0_, int p_c_1_) {
      return a(p_c_0_, p_c_1_)?1:0;
   }

   private static int d(int p_d_0_, int p_d_1_) {
      return a(p_d_0_, p_d_1_)?0:1;
   }

   public static int a(int p_a_0_) {
      return a(p_a_0_, 5, 4, 3, 2, 1);
   }

   public static int a(Collection<MobEffect> p_a_0_) {
      int ix = 3694022;
      if(p_a_0_ != null && !p_a_0_.isEmpty()) {
         float fx = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;

         for(MobEffect mobeffect : p_a_0_) {
            if(mobeffect.isShowParticles()) {
               int jx = MobEffectList.byId[mobeffect.getEffectId()].k();

               for(int kx = 0; kx <= mobeffect.getAmplifier(); ++kx) {
                  fx += (float)(jx >> 16 & 255) / 255.0F;
                  f1 += (float)(jx >> 8 & 255) / 255.0F;
                  f2 += (float)(jx >> 0 & 255) / 255.0F;
                  ++f3;
               }
            }
         }

         if(f3 == 0.0F) {
            return 0;
         } else {
            fx = fx / f3 * 255.0F;
            f1 = f1 / f3 * 255.0F;
            f2 = f2 / f3 * 255.0F;
            return (int)fx << 16 | (int)f1 << 8 | (int)f2;
         }
      } else {
         return ix;
      }
   }

   public static boolean b(Collection<MobEffect> p_b_0_) {
      for(MobEffect mobeffect : p_b_0_) {
         if(!mobeffect.isAmbient()) {
            return false;
         }
      }

      return true;
   }

   public static String c(int p_c_0_) {
      int ix = a(p_c_0_);
      return appearances[ix];
   }

   private static int a(boolean p_a_0_, boolean p_a_1_, boolean p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_) {
      int ix = 0;
      if(p_a_0_) {
         ix = d(p_a_6_, p_a_4_);
      } else if(p_a_3_ != -1) {
         if(p_a_3_ == 0 && h(p_a_6_) == p_a_4_) {
            ix = 1;
         } else if(p_a_3_ == 1 && h(p_a_6_) > p_a_4_) {
            ix = 1;
         } else if(p_a_3_ == 2 && h(p_a_6_) < p_a_4_) {
            ix = 1;
         }
      } else {
         ix = c(p_a_6_, p_a_4_);
      }

      if(p_a_1_) {
         ix *= p_a_5_;
      }

      if(p_a_2_) {
         ix *= -1;
      }

      return ix;
   }

   private static int h(int p_h_0_) {
      int ix;
      for(ix = 0; p_h_0_ > 0; ++ix) {
         p_h_0_ &= p_h_0_ - 1;
      }

      return ix;
   }

   private static int a(String p_a_0_, int p_a_1_, int p_a_2_, int p_a_3_) {
      if(p_a_1_ < p_a_0_.length() && p_a_2_ >= 0 && p_a_1_ < p_a_2_) {
         int ix = p_a_0_.indexOf(124, p_a_1_);
         if(ix >= 0 && ix < p_a_2_) {
            int k1 = a(p_a_0_, p_a_1_, ix - 1, p_a_3_);
            if(k1 > 0) {
               return k1;
            } else {
               int i2 = a(p_a_0_, ix + 1, p_a_2_, p_a_3_);
               return i2 > 0?i2:0;
            }
         } else {
            int jx = p_a_0_.indexOf(38, p_a_1_);
            if(jx >= 0 && jx < p_a_2_) {
               int l1 = a(p_a_0_, p_a_1_, jx - 1, p_a_3_);
               if(l1 <= 0) {
                  return 0;
               } else {
                  int j2 = a(p_a_0_, jx + 1, p_a_2_, p_a_3_);
                  return j2 <= 0?0:(l1 > j2?l1:j2);
               }
            } else {
               boolean flag = false;
               boolean flag1 = false;
               boolean flag2 = false;
               boolean flag3 = false;
               boolean flag4 = false;
               byte b0 = -1;
               int kx = 0;
               int lx = 0;
               int i1 = 0;

               for(int j1 = p_a_1_; j1 < p_a_2_; ++j1) {
                  char c0 = p_a_0_.charAt(j1);
                  if(c0 >= 48 && c0 <= 57) {
                     if(flag) {
                        lx = c0 - 48;
                        flag1 = true;
                     } else {
                        kx = kx * 10;
                        kx = kx + (c0 - 48);
                        flag2 = true;
                     }
                  } else if(c0 == 42) {
                     flag = true;
                  } else if(c0 == 33) {
                     if(flag2) {
                        i1 += a(flag3, flag1, flag4, b0, kx, lx, p_a_3_);
                        flag3 = false;
                        flag4 = false;
                        flag = false;
                        flag1 = false;
                        flag2 = false;
                        lx = 0;
                        kx = 0;
                        b0 = -1;
                     }

                     flag3 = true;
                  } else if(c0 == 45) {
                     if(flag2) {
                        i1 += a(flag3, flag1, flag4, b0, kx, lx, p_a_3_);
                        flag3 = false;
                        flag4 = false;
                        flag = false;
                        flag1 = false;
                        flag2 = false;
                        lx = 0;
                        kx = 0;
                        b0 = -1;
                     }

                     flag4 = true;
                  } else if(c0 != 61 && c0 != 60 && c0 != 62) {
                     if(c0 == 43 && flag2) {
                        i1 += a(flag3, flag1, flag4, b0, kx, lx, p_a_3_);
                        flag3 = false;
                        flag4 = false;
                        flag = false;
                        flag1 = false;
                        flag2 = false;
                        lx = 0;
                        kx = 0;
                        b0 = -1;
                     }
                  } else {
                     if(flag2) {
                        i1 += a(flag3, flag1, flag4, b0, kx, lx, p_a_3_);
                        flag3 = false;
                        flag4 = false;
                        flag = false;
                        flag1 = false;
                        flag2 = false;
                        lx = 0;
                        kx = 0;
                        b0 = -1;
                     }

                     if(c0 == 61) {
                        b0 = 0;
                     } else if(c0 == 60) {
                        b0 = 2;
                     } else if(c0 == 62) {
                        b0 = 1;
                     }
                  }
               }

               if(flag2) {
                  i1 += a(flag3, flag1, flag4, b0, kx, lx, p_a_3_);
               }

               return i1;
            }
         }
      } else {
         return 0;
      }
   }

   public static List<MobEffect> getEffects(int p_getEffects_0_, boolean p_getEffects_1_) {
      ArrayList arraylist = null;

      for(MobEffectList mobeffectlist : MobEffectList.byId) {
         if(mobeffectlist != null && (!mobeffectlist.j() || p_getEffects_1_)) {
            String s = (String)effectDurations.get(Integer.valueOf(mobeffectlist.getId()));
            if(s != null) {
               int ix = a(s, 0, s.length(), p_getEffects_0_);
               if(ix > 0) {
                  int jx = 0;
                  String s1 = (String)effectAmplifiers.get(Integer.valueOf(mobeffectlist.getId()));
                  if(s1 != null) {
                     jx = a(s1, 0, s1.length(), p_getEffects_0_);
                     if(jx < 0) {
                        jx = 0;
                     }
                  }

                  if(mobeffectlist.isInstant()) {
                     ix = 1;
                  } else {
                     ix = 1200 * (ix * 3 + (ix - 1) * 2);
                     ix = ix >> jx;
                     ix = (int)Math.round((double)ix * mobeffectlist.getDurationModifier());
                     if((p_getEffects_0_ & 16384) != 0) {
                        ix = (int)Math.round((double)ix * 0.75D + 0.5D);
                     }
                  }

                  if(arraylist == null) {
                     arraylist = Lists.newArrayList();
                  }

                  MobEffect mobeffect = new MobEffect(mobeffectlist.getId(), ix, jx);
                  if((p_getEffects_0_ & 16384) != 0) {
                     mobeffect.setSplash(true);
                  }

                  arraylist.add(mobeffect);
               }
            }
         }
      }

      return arraylist;
   }

   private static int a(int p_a_0_, int p_a_1_, boolean p_a_2_, boolean p_a_3_, boolean p_a_4_) {
      if(p_a_4_) {
         if(!a(p_a_0_, p_a_1_)) {
            return 0;
         }
      } else if(p_a_2_) {
         p_a_0_ &= ~(1 << p_a_1_);
      } else if(p_a_3_) {
         if((p_a_0_ & 1 << p_a_1_) == 0) {
            p_a_0_ |= 1 << p_a_1_;
         } else {
            p_a_0_ &= ~(1 << p_a_1_);
         }
      } else {
         p_a_0_ |= 1 << p_a_1_;
      }

      return p_a_0_;
   }

   public static int a(int p_a_0_, String p_a_1_) {
      byte b0 = 0;
      int ix = p_a_1_.length();
      boolean flag = false;
      boolean flag1 = false;
      boolean flag2 = false;
      boolean flag3 = false;
      int jx = 0;

      for(int kx = b0; kx < ix; ++kx) {
         char c0 = p_a_1_.charAt(kx);
         if(c0 >= 48 && c0 <= 57) {
            jx = jx * 10;
            jx = jx + (c0 - 48);
            flag = true;
         } else if(c0 == 33) {
            if(flag) {
               p_a_0_ = a(p_a_0_, jx, flag2, flag1, flag3);
               flag3 = false;
               flag1 = false;
               flag2 = false;
               flag = false;
               jx = 0;
            }

            flag1 = true;
         } else if(c0 == 45) {
            if(flag) {
               p_a_0_ = a(p_a_0_, jx, flag2, flag1, flag3);
               flag3 = false;
               flag1 = false;
               flag2 = false;
               flag = false;
               jx = 0;
            }

            flag2 = true;
         } else if(c0 == 43) {
            if(flag) {
               p_a_0_ = a(p_a_0_, jx, flag2, flag1, flag3);
               flag3 = false;
               flag1 = false;
               flag2 = false;
               flag = false;
               jx = 0;
            }
         } else if(c0 == 38) {
            if(flag) {
               p_a_0_ = a(p_a_0_, jx, flag2, flag1, flag3);
               flag3 = false;
               flag1 = false;
               flag2 = false;
               flag = false;
               jx = 0;
            }

            flag3 = true;
         }
      }

      if(flag) {
         p_a_0_ = a(p_a_0_, jx, flag2, flag1, flag3);
      }

      return p_a_0_ & 32767;
   }

   public static int a(int p_a_0_, int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_) {
      return (a(p_a_0_, p_a_1_)?16:0) | (a(p_a_0_, p_a_2_)?8:0) | (a(p_a_0_, p_a_3_)?4:0) | (a(p_a_0_, p_a_4_)?2:0) | (a(p_a_0_, p_a_5_)?1:0);
   }

   static {
      effectDurations.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "0 & !1 & !2 & !3 & 0+6");
      effectDurations.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "!0 & 1 & !2 & !3 & 1+6");
      effectDurations.put(Integer.valueOf(MobEffectList.FIRE_RESISTANCE.getId()), "0 & 1 & !2 & !3 & 0+6");
      effectDurations.put(Integer.valueOf(MobEffectList.HEAL.getId()), "0 & !1 & 2 & !3");
      effectDurations.put(Integer.valueOf(MobEffectList.POISON.getId()), "!0 & !1 & 2 & !3 & 2+6");
      effectDurations.put(Integer.valueOf(MobEffectList.WEAKNESS.getId()), "!0 & !1 & !2 & 3 & 3+6");
      effectDurations.put(Integer.valueOf(MobEffectList.HARM.getId()), "!0 & !1 & 2 & 3");
      effectDurations.put(Integer.valueOf(MobEffectList.SLOWER_MOVEMENT.getId()), "!0 & 1 & !2 & 3 & 3+6");
      effectDurations.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "0 & !1 & !2 & 3 & 3+6");
      effectDurations.put(Integer.valueOf(MobEffectList.NIGHT_VISION.getId()), "!0 & 1 & 2 & !3 & 2+6");
      effectDurations.put(Integer.valueOf(MobEffectList.INVISIBILITY.getId()), "!0 & 1 & 2 & 3 & 2+6");
      effectDurations.put(Integer.valueOf(MobEffectList.WATER_BREATHING.getId()), "0 & !1 & 2 & 3 & 2+6");
      effectDurations.put(Integer.valueOf(MobEffectList.JUMP.getId()), "0 & 1 & !2 & 3 & 3+6");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_MOVEMENT.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.FASTER_DIG.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.INCREASE_DAMAGE.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.REGENERATION.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.HARM.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.HEAL.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.RESISTANCE.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.POISON.getId()), "5");
      effectAmplifiers.put(Integer.valueOf(MobEffectList.JUMP.getId()), "5");
   }
}
