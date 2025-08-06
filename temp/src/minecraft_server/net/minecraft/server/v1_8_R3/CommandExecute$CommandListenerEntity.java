package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandExecute;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

class CommandExecute$CommandListenerEntity implements ICommandListener {
   CommandExecute$CommandListenerEntity(CommandExecute p_i545_1_, Entity p_i545_2_, ICommandListener p_i545_3_, BlockPosition p_i545_4_, double p_i545_5_, double p_i545_7_, double p_i545_9_) {
      this.g = p_i545_1_;
      this.a = p_i545_2_;
      this.b = p_i545_3_;
      this.c = p_i545_4_;
      this.d = p_i545_5_;
      this.e = p_i545_7_;
      this.f = p_i545_9_;
   }

   public String getName() {
      return this.a.getName();
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return this.a.getScoreboardDisplayName();
   }

   public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
      this.b.sendMessage(p_sendMessage_1_);
   }

   public boolean a(int p_a_1_, String p_a_2_) {
      return this.b.a(p_a_1_, p_a_2_);
   }

   public BlockPosition getChunkCoordinates() {
      return this.c;
   }

   public Vec3D d() {
      return new Vec3D(this.d, this.e, this.f);
   }

   public World getWorld() {
      return this.a.world;
   }

   public Entity f() {
      return this.a;
   }

   public boolean getSendCommandFeedback() {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      return minecraftserver == null || minecraftserver.worldServer[0].getGameRules().getBoolean("commandBlockOutput");
   }

   public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
      this.a.a(p_a_1_, p_a_2_);
   }
}
