package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockLever;
import net.minecraft.server.v1_8_R3.EnumDirection;

// $FF: synthetic class
class BlockLever$1 {
   static {
      try {
         c[EnumDirection.EnumAxis.X.ordinal()] = 1;
      } catch (NoSuchFieldError var16) {
         ;
      }

      try {
         c[EnumDirection.EnumAxis.Z.ordinal()] = 2;
      } catch (NoSuchFieldError var15) {
         ;
      }

      b = new int[BlockLever.EnumLeverPosition.values().length];

      try {
         b[BlockLever.EnumLeverPosition.EAST.ordinal()] = 1;
      } catch (NoSuchFieldError var14) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.WEST.ordinal()] = 2;
      } catch (NoSuchFieldError var13) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.SOUTH.ordinal()] = 3;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.NORTH.ordinal()] = 4;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.UP_Z.ordinal()] = 5;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.UP_X.ordinal()] = 6;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.DOWN_X.ordinal()] = 7;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         b[BlockLever.EnumLeverPosition.DOWN_Z.ordinal()] = 8;
      } catch (NoSuchFieldError var7) {
         ;
      }

      a = new int[EnumDirection.values().length];

      try {
         a[EnumDirection.DOWN.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         a[EnumDirection.UP.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         a[EnumDirection.NORTH.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[EnumDirection.SOUTH.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[EnumDirection.WEST.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[EnumDirection.EAST.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
