package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class RemoteControlCommandListener implements ICommandListener {
   private static final RemoteControlCommandListener instance = new RemoteControlCommandListener();
   private StringBuffer b = new StringBuffer();

   public static RemoteControlCommandListener getInstance() {
      return instance;
   }

   public void i() {
      this.b.setLength(0);
   }

   public String j() {
      return this.b.toString();
   }

   public String getName() {
      return "Rcon";
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return new ChatComponentText(this.getName());
   }

   public void sendMessage(String p_sendMessage_1_) {
      this.b.append(p_sendMessage_1_);
   }

   public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
      this.b.append(p_sendMessage_1_.c());
   }

   public boolean a(int p_a_1_, String p_a_2_) {
      return true;
   }

   public BlockPosition getChunkCoordinates() {
      return new BlockPosition(0, 0, 0);
   }

   public Vec3D d() {
      return new Vec3D(0.0D, 0.0D, 0.0D);
   }

   public World getWorld() {
      return MinecraftServer.getServer().getWorld();
   }

   public Entity f() {
      return null;
   }

   public boolean getSendCommandFeedback() {
      return true;
   }

   public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
   }
}
