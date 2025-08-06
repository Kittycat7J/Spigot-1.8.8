package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R3.Vec3D;

public class CommandPlaySound extends CommandAbstract {
   public String getCommand() {
      return "playsound";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.playsound.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage(this.getUsage(p_execute_1_), new Object[0]);
      } else {
         int i = 0;
         String s = p_execute_2_[i++];
         EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[i++]);
         Vec3D vec3d = p_execute_1_.d();
         double d0 = vec3d.a;
         if(p_execute_2_.length > i) {
            d0 = b(d0, p_execute_2_[i++], true);
         }

         double d1 = vec3d.b;
         if(p_execute_2_.length > i) {
            d1 = b(d1, p_execute_2_[i++], 0, 0, false);
         }

         double d2 = vec3d.c;
         if(p_execute_2_.length > i) {
            d2 = b(d2, p_execute_2_[i++], true);
         }

         double d3 = 1.0D;
         if(p_execute_2_.length > i) {
            d3 = a(p_execute_2_[i++], 0.0D, 3.4028234663852886E38D);
         }

         double d4 = 1.0D;
         if(p_execute_2_.length > i) {
            d4 = a(p_execute_2_[i++], 0.0D, 2.0D);
         }

         double d5 = 0.0D;
         if(p_execute_2_.length > i) {
            d5 = a(p_execute_2_[i], 0.0D, 1.0D);
         }

         double d6 = d3 > 1.0D?d3 * 16.0D:16.0D;
         double d7 = entityplayer.f(d0, d1, d2);
         if(d7 > d6) {
            if(d5 <= 0.0D) {
               throw new CommandException("commands.playsound.playerTooFar", new Object[]{entityplayer.getName()});
            }

            double d8 = d0 - entityplayer.locX;
            double d9 = d1 - entityplayer.locY;
            double d10 = d2 - entityplayer.locZ;
            double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
            if(d11 > 0.0D) {
               d0 = entityplayer.locX + d8 / d11 * 2.0D;
               d1 = entityplayer.locY + d9 / d11 * 2.0D;
               d2 = entityplayer.locZ + d10 / d11 * 2.0D;
            }

            d3 = d5;
         }

         entityplayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(s, d0, d1, d2, (float)d3, (float)d4));
         a(p_execute_1_, this, "commands.playsound.success", new Object[]{s, entityplayer.getName()});
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 2?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()):(p_tabComplete_2_.length > 2 && p_tabComplete_2_.length <= 5?a(p_tabComplete_2_, 2, p_tabComplete_3_):null);
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 1;
   }
}
