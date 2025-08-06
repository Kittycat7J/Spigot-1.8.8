package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandTestForBlock extends CommandAbstract
{
    public String getCommand()
    {
        return "testforblock";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.testforblock.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 4)
        {
            throw new ExceptionUsage("commands.testforblock.usage", new Object[0]);
        }
        else
        {
            p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
            BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 0, false);
            Block block = Block.getByName(p_execute_2_[3]);

            if (block == null)
            {
                throw new ExceptionInvalidNumber("commands.setblock.notFound", new Object[] {p_execute_2_[3]});
            }
            else
            {
                int i = -1;

                if (p_execute_2_.length >= 5)
                {
                    i = a(p_execute_2_[4], -1, 15);
                }

                World world = p_execute_1_.getWorld();

                if (!world.isLoaded(blockposition))
                {
                    throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
                }
                else
                {
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    boolean flag = false;

                    if (p_execute_2_.length >= 6 && block.isTileEntity())
                    {
                        String s = a(p_execute_1_, p_execute_2_, 5).c();

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

                    IBlockData iblockdata = world.getType(blockposition);
                    Block block1 = iblockdata.getBlock();

                    if (block1 != block)
                    {
                        throw new CommandException("commands.testforblock.failed.tile", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ()), block1.getName(), block.getName()});
                    }
                    else
                    {
                        if (i > -1)
                        {
                            int j = iblockdata.getBlock().toLegacyData(iblockdata);

                            if (j != i)
                            {
                                throw new CommandException("commands.testforblock.failed.data", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ()), Integer.valueOf(j), Integer.valueOf(i)});
                            }
                        }

                        if (flag)
                        {
                            TileEntity tileentity = world.getTileEntity(blockposition);

                            if (tileentity == null)
                            {
                                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
                            }

                            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                            tileentity.b(nbttagcompound1);

                            if (!GameProfileSerializer.a(nbttagcompound, nbttagcompound1, true))
                            {
                                throw new CommandException("commands.testforblock.failed.nbt", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
                            }
                        }

                        p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
                        a(p_execute_1_, this, "commands.testforblock.success", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
                    }
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3 ? a(p_tabComplete_2_, 0, p_tabComplete_3_) : (p_tabComplete_2_.length == 4 ? a(p_tabComplete_2_, Block.REGISTRY.keySet()) : null);
    }
}
