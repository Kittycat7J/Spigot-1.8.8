package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class CommandReplaceItem extends CommandAbstract
{
    private static final Map<String, Integer> a = Maps.<String, Integer>newHashMap();

    public String getCommand()
    {
        return "replaceitem";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.replaceitem.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.replaceitem.usage", new Object[0]);
        }
        else
        {
            boolean flag;

            if (p_execute_2_[0].equals("entity"))
            {
                flag = false;
            }
            else
            {
                if (!p_execute_2_[0].equals("block"))
                {
                    throw new ExceptionUsage("commands.replaceitem.usage", new Object[0]);
                }

                flag = true;
            }

            int i;

            if (flag)
            {
                if (p_execute_2_.length < 6)
                {
                    throw new ExceptionUsage("commands.replaceitem.block.usage", new Object[0]);
                }

                i = 4;
            }
            else
            {
                if (p_execute_2_.length < 4)
                {
                    throw new ExceptionUsage("commands.replaceitem.entity.usage", new Object[0]);
                }

                i = 2;
            }

            int j = this.e(p_execute_2_[i++]);
            Item item;

            try
            {
                item = f(p_execute_1_, p_execute_2_[i]);
            }
            catch (ExceptionInvalidNumber exceptioninvalidnumber)
            {
                if (Block.getByName(p_execute_2_[i]) != Blocks.AIR)
                {
                    throw exceptioninvalidnumber;
                }

                item = null;
            }

            ++i;
            int k = p_execute_2_.length > i ? a(p_execute_2_[i++], 1, 64) : 1;
            int l = p_execute_2_.length > i ? a(p_execute_2_[i++]) : 0;
            ItemStack itemstack = new ItemStack(item, k, l);

            if (p_execute_2_.length > i)
            {
                String s = a(p_execute_1_, p_execute_2_, i).c();

                try
                {
                    itemstack.setTag(MojangsonParser.parse(s));
                }
                catch (MojangsonParseException mojangsonparseexception)
                {
                    throw new CommandException("commands.replaceitem.tagError", new Object[] {mojangsonparseexception.getMessage()});
                }
            }

            if (itemstack.getItem() == null)
            {
                itemstack = null;
            }

            if (flag)
            {
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);
                BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 1, false);
                World world = p_execute_1_.getWorld();
                TileEntity tileentity = world.getTileEntity(blockposition);

                if (tileentity == null || !(tileentity instanceof IInventory))
                {
                    throw new CommandException("commands.replaceitem.noContainer", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
                }

                IInventory iinventory = (IInventory)tileentity;

                if (j >= 0 && j < iinventory.getSize())
                {
                    iinventory.setItem(j, itemstack);
                }
            }
            else
            {
                Entity entity = b(p_execute_1_, p_execute_2_[1]);
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);

                if (entity instanceof EntityHuman)
                {
                    ((EntityHuman)entity).defaultContainer.b();
                }

                if (!entity.d(j, itemstack))
                {
                    throw new CommandException("commands.replaceitem.failed", new Object[] {Integer.valueOf(j), Integer.valueOf(k), itemstack == null ? "Air" : itemstack.C()});
                }

                if (entity instanceof EntityHuman)
                {
                    ((EntityHuman)entity).defaultContainer.b();
                }
            }

            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, k);
            a(p_execute_1_, this, "commands.replaceitem.success", new Object[] {Integer.valueOf(j), Integer.valueOf(k), itemstack == null ? "Air" : itemstack.C()});
        }
    }

    private int e(String p_e_1_) throws CommandException
    {
        if (!a.containsKey(p_e_1_))
        {
            throw new CommandException("commands.generic.parameter.invalid", new Object[] {p_e_1_});
        }
        else
        {
            return ((Integer)a.get(p_e_1_)).intValue();
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"entity", "block"}): (p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("entity") ? a(p_tabComplete_2_, this.d()) : (p_tabComplete_2_.length >= 2 && p_tabComplete_2_.length <= 4 && p_tabComplete_2_[0].equals("block") ? a(p_tabComplete_2_, 1, p_tabComplete_3_) : ((p_tabComplete_2_.length != 3 || !p_tabComplete_2_[0].equals("entity")) && (p_tabComplete_2_.length != 5 || !p_tabComplete_2_[0].equals("block")) ? ((p_tabComplete_2_.length != 4 || !p_tabComplete_2_[0].equals("entity")) && (p_tabComplete_2_.length != 6 || !p_tabComplete_2_[0].equals("block")) ? null : a(p_tabComplete_2_, Item.REGISTRY.keySet())) : a(p_tabComplete_2_, a.keySet()))));
    }

    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_1_.length > 0 && p_isListStart_1_[0].equals("entity") && p_isListStart_2_ == 1;
    }

    static
    {
        for (int i = 0; i < 54; ++i)
        {
            a.put("slot.container." + i, Integer.valueOf(i));
        }

        for (int j = 0; j < 9; ++j)
        {
            a.put("slot.hotbar." + j, Integer.valueOf(j));
        }

        for (int k = 0; k < 27; ++k)
        {
            a.put("slot.inventory." + k, Integer.valueOf(9 + k));
        }

        for (int l = 0; l < 27; ++l)
        {
            a.put("slot.enderchest." + l, Integer.valueOf(200 + l));
        }

        for (int i1 = 0; i1 < 8; ++i1)
        {
            a.put("slot.villager." + i1, Integer.valueOf(300 + i1));
        }

        for (int j1 = 0; j1 < 15; ++j1)
        {
            a.put("slot.horse." + j1, Integer.valueOf(500 + j1));
        }

        a.put("slot.weapon", Integer.valueOf(99));
        a.put("slot.armor.head", Integer.valueOf(103));
        a.put("slot.armor.chest", Integer.valueOf(102));
        a.put("slot.armor.legs", Integer.valueOf(101));
        a.put("slot.armor.feet", Integer.valueOf(100));
        a.put("slot.horse.saddle", Integer.valueOf(400));
        a.put("slot.horse.armor", Integer.valueOf(401));
        a.put("slot.horse.chest", Integer.valueOf(499));
    }
}
