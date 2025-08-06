package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandSummon extends CommandAbstract
{
    public String getCommand()
    {
        return "summon";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.summon.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.summon.usage", new Object[0]);
        }
        else
        {
            String s = p_execute_2_[0];
            BlockPosition blockposition = p_execute_1_.getChunkCoordinates();
            Vec3D vec3d = p_execute_1_.d();
            double d0 = vec3d.a;
            double d1 = vec3d.b;
            double d2 = vec3d.c;

            if (p_execute_2_.length >= 4)
            {
                d0 = b(d0, p_execute_2_[1], true);
                d1 = b(d1, p_execute_2_[2], false);
                d2 = b(d2, p_execute_2_[3], true);
                blockposition = new BlockPosition(d0, d1, d2);
            }

            World world = p_execute_1_.getWorld();

            if (!world.isLoaded(blockposition))
            {
                throw new CommandException("commands.summon.outOfWorld", new Object[0]);
            }
            else if ("LightningBolt".equals(s))
            {
                world.strikeLightning(new EntityLightning(world, d0, d1, d2));
                a(p_execute_1_, this, "commands.summon.success", new Object[0]);
            }
            else
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag = false;

                if (p_execute_2_.length >= 5)
                {
                    IChatBaseComponent ichatbasecomponent = a(p_execute_1_, p_execute_2_, 4);

                    try
                    {
                        nbttagcompound = MojangsonParser.parse(ichatbasecomponent.c());
                        flag = true;
                    }
                    catch (MojangsonParseException mojangsonparseexception)
                    {
                        throw new CommandException("commands.summon.tagError", new Object[] {mojangsonparseexception.getMessage()});
                    }
                }

                nbttagcompound.setString("id", s);
                Entity entity2;

                try
                {
                    entity2 = EntityTypes.a(nbttagcompound, world);
                }
                catch (RuntimeException var19)
                {
                    throw new CommandException("commands.summon.failed", new Object[0]);
                }

                if (entity2 == null)
                {
                    throw new CommandException("commands.summon.failed", new Object[0]);
                }
                else
                {
                    entity2.setPositionRotation(d0, d1, d2, entity2.yaw, entity2.pitch);

                    if (!flag && entity2 instanceof EntityInsentient)
                    {
                        ((EntityInsentient)entity2).prepare(world.E(new BlockPosition(entity2)), (GroupDataEntity)null);
                    }

                    world.addEntity(entity2);
                    Entity entity = entity2;

                    for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity != null && nbttagcompound1.hasKeyOfType("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompound("Riding"))
                    {
                        Entity entity1 = EntityTypes.a(nbttagcompound1.getCompound("Riding"), world);

                        if (entity1 != null)
                        {
                            entity1.setPositionRotation(d0, d1, d2, entity1.yaw, entity1.pitch);
                            world.addEntity(entity1);
                            entity.mount(entity1);
                        }

                        entity = entity1;
                    }

                    a(p_execute_1_, this, "commands.summon.success", new Object[0]);
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, EntityTypes.b()) : (p_tabComplete_2_.length > 1 && p_tabComplete_2_.length <= 4 ? a(p_tabComplete_2_, 1, p_tabComplete_3_) : null);
    }
}
