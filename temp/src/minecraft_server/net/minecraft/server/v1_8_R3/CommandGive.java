package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;

public class CommandGive extends CommandAbstract {
   public String getCommand() {
      return "give";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.give.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 2) {
         throw new ExceptionUsage("commands.give.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer = a(p_execute_1_, p_execute_2_[0]);
         Item item = f(p_execute_1_, p_execute_2_[1]);
         int i = p_execute_2_.length >= 3?a(p_execute_2_[2], 1, 64):1;
         int j = p_execute_2_.length >= 4?a(p_execute_2_[3]):0;
         ItemStack itemstack = new ItemStack(item, i, j);
         if(p_execute_2_.length >= 5) {
            String s = a(p_execute_1_, p_execute_2_, 4).c();

            try {
               itemstack.setTag(MojangsonParser.parse(s));
            } catch (MojangsonParseException mojangsonparseexception) {
               throw new CommandException("commands.give.tagError", new Object[]{mojangsonparseexception.getMessage()});
            }
         }

         boolean flag = entityplayer.inventory.pickup(itemstack);
         if(flag) {
            entityplayer.world.makeSound(entityplayer, "random.pop", 0.2F, ((entityplayer.bc().nextFloat() - entityplayer.bc().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.defaultContainer.b();
         }

         if(flag && itemstack.count <= 0) {
            itemstack.count = 1;
            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, i);
            EntityItem entityitem1 = entityplayer.drop(itemstack, false);
            if(entityitem1 != null) {
               entityitem1.v();
            }
         } else {
            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, i - itemstack.count);
            EntityItem entityitem = entityplayer.drop(itemstack, false);
            if(entityitem != null) {
               entityitem.q();
               entityitem.b(entityplayer.getName());
            }
         }

         a(p_execute_1_, this, "commands.give.success", new Object[]{itemstack.C(), Integer.valueOf(i), entityplayer.getName()});
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, this.d()):(p_tabComplete_2_.length == 2?a(p_tabComplete_2_, Item.REGISTRY.keySet()):null);
   }

   protected String[] d() {
      return MinecraftServer.getServer().getPlayers();
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }
}
