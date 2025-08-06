package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandParticle extends CommandAbstract
{
    public String getCommand()
    {
        return "particle";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.particle.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 8)
        {
            throw new ExceptionUsage("commands.particle.usage", new Object[0]);
        }
        else
        {
            boolean flag = false;
            EnumParticle enumparticle = null;

            for (EnumParticle enumparticle1 : EnumParticle.values())
            {
                if (enumparticle1.f())
                {
                    if (p_execute_2_[0].startsWith(enumparticle1.b()))
                    {
                        flag = true;
                        enumparticle = enumparticle1;
                        break;
                    }
                }
                else if (p_execute_2_[0].equals(enumparticle1.b()))
                {
                    flag = true;
                    enumparticle = enumparticle1;
                    break;
                }
            }

            if (!flag)
            {
                throw new CommandException("commands.particle.notFound", new Object[] {p_execute_2_[0]});
            }
            else
            {
                String s = p_execute_2_[0];
                Vec3D vec3d = p_execute_1_.d();
                double d0 = (double)((float)b(vec3d.a, p_execute_2_[1], true));
                double d1 = (double)((float)b(vec3d.b, p_execute_2_[2], true));
                double d2 = (double)((float)b(vec3d.c, p_execute_2_[3], true));
                double d3 = (double)((float)c(p_execute_2_[4]));
                double d4 = (double)((float)c(p_execute_2_[5]));
                double d5 = (double)((float)c(p_execute_2_[6]));
                double d6 = (double)((float)c(p_execute_2_[7]));
                int i = 0;

                if (p_execute_2_.length > 8)
                {
                    i = a(p_execute_2_[8], 0);
                }

                boolean flag1 = false;

                if (p_execute_2_.length > 9 && "force".equals(p_execute_2_[9]))
                {
                    flag1 = true;
                }

                World world = p_execute_1_.getWorld();

                if (world instanceof WorldServer)
                {
                    WorldServer worldserver = (WorldServer)world;
                    int[] aint = new int[enumparticle.d()];

                    if (enumparticle.f())
                    {
                        String[] astring = p_execute_2_[0].split("_", 3);

                        for (int j = 1; j < astring.length; ++j)
                        {
                            try
                            {
                                aint[j - 1] = Integer.parseInt(astring[j]);
                            }
                            catch (NumberFormatException var31)
                            {
                                throw new CommandException("commands.particle.notFound", new Object[] {p_execute_2_[0]});
                            }
                        }
                    }

                    worldserver.a(enumparticle, flag1, d0, d1, d2, i, d3, d4, d5, d6, aint);
                    a(p_execute_1_, this, "commands.particle.success", new Object[] {s, Integer.valueOf(Math.max(i, 1))});
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, EnumParticle.a()) : (p_tabComplete_2_.length > 1 && p_tabComplete_2_.length <= 4 ? a(p_tabComplete_2_, 1, p_tabComplete_3_) : (p_tabComplete_2_.length == 10 ? a(p_tabComplete_2_, new String[] {"normal", "force"}): null));
    }
}
