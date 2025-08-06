package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IDataManager;
import net.minecraft.server.v1_8_R3.MethodProfiler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;

public class DemoWorldServer extends WorldServer {
   private static final long I = (long)"North Carolina".hashCode();
   public static final WorldSettings a = (new WorldSettings(I, WorldSettings.EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL)).a();

   public DemoWorldServer(MinecraftServer p_i1073_1_, IDataManager p_i1073_2_, WorldData p_i1073_3_, int p_i1073_4_, MethodProfiler p_i1073_5_) {
      super(p_i1073_1_, p_i1073_2_, p_i1073_3_, p_i1073_4_, p_i1073_5_);
      this.worldData.a(a);
   }
}
