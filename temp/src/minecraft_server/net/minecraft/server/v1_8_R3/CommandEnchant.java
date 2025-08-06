package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionInvalidNumber;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class CommandEnchant extends CommandAbstract {
   public String getCommand() {
      return "enchant";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.enchant.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[0]);
         p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);

         int i;
         try {
            i = a(p_execute_2_[1], 0);
         } catch (ExceptionInvalidNumber exceptioninvalidnumber) {
            Enchantment enchantment = Enchantment.getByName(p_execute_2_[1]);
            if(enchantment == null) {
               throw exceptioninvalidnumber;
            }

            i = enchantment.id;
         }

         int j = 1;
         ItemStack itemstack = entityplayer.bZ();
         if(itemstack == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
         } else {
            Enchantment enchantment1 = Enchantment.getById(i);
            if(enchantment1 == null) {
               throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[]{Integer.valueOf(i)});
            } else if(!enchantment1.canEnchant(itemstack)) {
               throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
            } else {
               if(p_execute_2_.length >= 3) {
                  j = a(p_execute_2_[2], enchantment1.getStartLevel(), enchantment1.getMaxLevel());
               }

               if(itemstack.hasTag()) {
                  NBTTagList nbttaglist = itemstack.getEnchantments();
                  if(nbttaglist != null) {
                     for(int k = 0; k < nbttaglist.size(); ++k) {
                        short short1 = nbttaglist.get(k).getShort("id");
                        if(Enchantment.getById(short1) != null) {
                           Enchantment enchantment2 = Enchantment.getById(short1);
                           if(!enchantment2.a(enchantment1)) {
                              throw new CommandException("commands.enchant.cantCombine", new Object[]{enchantment1.d(j), enchantment2.d(nbttaglist.get(k).getShort("lvl"))});
                           }
                        }
                     }
                  }
               }

               itemstack.addEnchantment(enchantment1, j);
               a(p_execute_1_, this, "commands.enchant.success", new Object[0]);
               p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 1);
            }
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, this.d()):(p_tabComplete_2_.length == 2?a(p_tabComplete_2_, Enchantment.getEffects()):null);
   }

   protected String[] d() {
      return MinecraftServer.getServer().getPlayers();
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}
