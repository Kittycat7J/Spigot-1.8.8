package net.minecraft.server.v1_8_R3;

import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.MinecraftServer;

class MinecraftServer$4 implements Callable<String> {
   MinecraftServer$4(MinecraftServer p_i1107_1_) {
      this.a = p_i1107_1_;
   }

   public String a() {
      return MinecraftServer.a((MinecraftServer)this.a).getPlayerCount() + " / " + MinecraftServer.a((MinecraftServer)this.a).getMaxPlayers() + "; " + MinecraftServer.a((MinecraftServer)this.a).v();
   }
}
