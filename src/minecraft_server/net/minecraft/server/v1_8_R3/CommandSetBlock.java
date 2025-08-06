package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandSetBlock extends CommandAbstract
{
    public String getCommand()
    {
        return "setblock";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.setblock.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 4)
        {
            throw new ExceptionUsage("commands.setblock.usage", new Object[0]);
        }
        else
        {
            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
            BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
            Block block = CommandAbstract.g(p_execute_1_, p_execute_2_[3]);
            int i = 0;

            if (p_execute_2_.length >= 5)
            {
                i = a(p_execute_2_[4], 0, 15);
            }

            World world = p_execute_1_.getWorld();

            if (!world.isLoaded(blockposition))
            {
                throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
            }
            else
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag = false;

                if (p_execute_2_.length >= 7 && block.isTileEntity())
                {
                    String s = a(p_execute_1_, p_execute_2_, 6).c();

                    try
                    {
                        nbttagcompound = MojangsonParser.parse(s);
                        flag = true;
                    }
                    catch (MojangsonParseException mojangsonparseexception)
                    {
                        throw new CommandException("commands.setblock.tagError", new Object[] {mojangsonparseexception.getMessage()});
                    }
                }

                if (p_execute_2_.length >= 6)
                {
                    if (p_execute_2_[5].equals("destroy"))
                    {
                        world.setAir(blockposition, true);

                        if (block == Blocks.AIR)
                        {
                            a(p_execute_1_, this, "commands.setblock.success", new Object[0]);
                            return;
                        }
                    }
                    else if (p_execute_2_[5].equals("keep") && !world.isEmpty(blockposition))
                    {
                        throw new CommandException("commands.setblock.noChange", new Object[0]);
                    }
                }

                TileEntity tileentity1 = world.getTileEntity(blockposition);

                if (tileentity1 != null)
                {
                    if (tileentity1 instanceof IInventory)
                    {
                        ((IInventory)tileentity1).l();
                    }

                    world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), block == Blocks.AIR ? 2 : 4);
                }

                IBlockData iblockdata = block.fromLegacyData(i);

                if (!world.setTypeAndData(blockposition, iblockdata, 2))
                {
                    throw new CommandException("commands.setblock.noChange", new Object[0]);
                }
                else
                {
                    if (flag)
                    {
                        TileEntity tileentity = world.getTileEntity(blockposition);

                        if (tileentity != null)
                        {
                            nbttagcompound.setInt("x", blockposition.getX());
                            nbttagcompound.setInt("y", blockposition.getY());
                            nbttagcompound.setInt("z", blockposition.getZ());
                            tileentity.a(nbttagcompound);
                        }
                    }

                    world.update(blockposition, iblockdata.getBlock());
                    p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
                    a(p_execute_1_, this, "commands.setblock.success", new Object[0]);
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3 ? a(p_tabComplete_2_, 0, p_tabComplete_3_) : (p_tabComplete_2_.length == 4 ? a(p_tabComplete_2_, Block.REGISTRY.keySet()) : (p_tabComplete_2_.length == 6 ? a(p_tabComplete_2_, new String[] {"replace", "destroy", "keep"}): null));
    }
}
