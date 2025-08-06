package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ExceptionInvalidNumber;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class CommandEffect extends CommandAbstract {
   public String getCommand() {
      return "effect";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.effect.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage("commands.effect.usage", new Object[0]);
      } else {
         EntityLiving entityliving = (EntityLiving)a(p_execute_1_, p_execute_2_[0], EntityLiving.class);
         if(p_execute_2_[1].equals("clear")) {
            if(entityliving.getEffects().isEmpty()) {
               throw new CommandException("commands.effect.failure.notActive.all", new Object[]{entityliving.getName()});
            } else {
               entityliving.removeAllEffects();
               a(p_execute_1_, this, "commands.effect.success.removed.all", new Object[]{entityliving.getName()});
            }
         } else {
            int i;
            try {
               i = a(p_execute_2_[1], 1);
            } catch (ExceptionInvalidNumber exceptioninvalidnumber) {
               MobEffectList mobeffectlist = MobEffectList.b(p_execute_2_[1]);
               if(mobeffectlist == null) {
                  throw exceptioninvalidnumber;
               }

               i = mobeffectlist.id;
            }

            int j = 600;
            int l = 30;
            int k = 0;
            if(i >= 0 && i < MobEffectList.byId.length && MobEffectList.byId[i] != null) {
               MobEffectList mobeffectlist1 = MobEffectList.byId[i];
               if(p_execute_2_.length >= 3) {
                  l = a(p_execute_2_[2], 0, 1000000);
                  if(mobeffectlist1.isInstant()) {
                     j = l;
                  } else {
                     j = l * 20;
                  }
               } else if(mobeffectlist1.isInstant()) {
                  j = 1;
               }

               if(p_execute_2_.length >= 4) {
                  k = a(p_execute_2_[3], 0, 255);
               }

               boolean flag = true;
               if(p_execute_2_.length >= 5 && "true".equalsIgnoreCase(p_execute_2_[4])) {
                  flag = false;
               }

               if(l > 0) {
                  MobEffect mobeffect = new MobEffect(i, j, k, false, flag);
                  entityliving.addEffect(mobeffect);
                  a(p_execute_1_, this, "commands.effect.success", new Object[]{new ChatMessage(mobeffect.g(), new Object[0]), Integer.valueOf(i), Integer.valueOf(k), entityliving.getName(), Integer.valueOf(l)});
               } else if(entityliving.hasEffect(i)) {
                  entityliving.removeEffect(i);
                  a(p_execute_1_, this, "commands.effect.success.removed", new Object[]{new ChatMessage(mobeffectlist1.a(), new Object[0]), entityliving.getName()});
               } else {
                  throw new CommandException("commands.effect.failure.notActive", new Object[]{new ChatMessage(mobeffectlist1.a(), new Object[0]), entityliving.getName()});
               }
            } else {
               throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[]{Integer.valueOf(i)});
            }
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, this.d()):(p_tabComplete_2_.length == 2?a(p_tabComplete_2_, MobEffectList.c()):(p_tabComplete_2_.length == 5?a(p_tabComplete_2_, new String[]{"true", "false"}):null));
   }

   protected String[] d() {
      return MinecraftServer.getServer().getPlayers();
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}
