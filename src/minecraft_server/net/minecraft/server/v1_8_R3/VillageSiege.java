package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class VillageSiege
{
    private World a;
    private boolean b;
    private int c = -1;
    private int d;
    private int e;
    private Village f;
    private int g;
    private int h;
    private int i;

    public VillageSiege(World p_i85_1_)
    {
        this.a = p_i85_1_;
    }

    public void a()
    {
        if (this.a.w())
        {
            this.c = 0;
        }
        else if (this.c != 2)
        {
            if (this.c == 0)
            {
                float f = this.a.c(0.0F);

                if ((double)f < 0.5D || (double)f > 0.501D)
                {
                    return;
                }

                this.c = this.a.random.nextInt(10) == 0 ? 1 : 2;
                this.b = false;

                if (this.c == 2)
                {
                    return;
                }
            }

            if (this.c != -1)
            {
                if (!this.b)
                {
                    if (!this.b())
                    {
                        return;
                    }

                    this.b = true;
                }

                if (this.e > 0)
                {
                    --this.e;
                }
                else
                {
                    this.e = 2;

                    if (this.d > 0)
                    {
                        this.c();
                        --this.d;
                    }
                    else
                    {
                        this.c = 2;
                    }
                }
            }
        }
    }

    private boolean b()
    {
        List list = this.a.players;
        Iterator iterator = list.iterator();

        while (true)
        {
            if (!iterator.hasNext())
            {
                return false;
            }

            EntityHuman entityhuman = (EntityHuman)iterator.next();

            if (!entityhuman.isSpectator())
            {
                this.f = this.a.ae().getClosestVillage(new BlockPosition(entityhuman), 1);

                if (this.f != null && this.f.c() >= 10 && this.f.d() >= 20 && this.f.e() >= 20)
                {
                    BlockPosition blockposition = this.f.a();
                    float f = (float)this.f.b();
                    boolean flag = false;

                    for (int i = 0; i < 10; ++i)
                    {
                        float f1 = this.a.random.nextFloat() * (float)Math.PI * 2.0F;
                        this.g = blockposition.getX() + (int)((double)(MathHelper.cos(f1) * f) * 0.9D);
                        this.h = blockposition.getY();
                        this.i = blockposition.getZ() + (int)((double)(MathHelper.sin(f1) * f) * 0.9D);
                        flag = false;

                        for (Village village : this.a.ae().getVillages())
                        {
                            if (village != this.f && village.a(new BlockPosition(this.g, this.h, this.i)))
                            {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag)
                        {
                            break;
                        }
                    }

                    if (flag)
                    {
                        return false;
                    }

                    Vec3D vec3d = this.a(new BlockPosition(this.g, this.h, this.i));

                    if (vec3d != null)
                    {
                        break;
                    }
                }
            }
        }

        this.e = 0;
        this.d = 20;
        return true;
    }

    private boolean c()
    {
        Vec3D vec3d = this.a(new BlockPosition(this.g, this.h, this.i));

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            EntityZombie entityzombie;

            try
            {
                entityzombie = new EntityZombie(this.a);
                entityzombie.prepare(this.a.E(new BlockPosition(entityzombie)), (GroupDataEntity)null);
                entityzombie.setVillager(false);
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
                return false;
            }

            entityzombie.setPositionRotation(vec3d.a, vec3d.b, vec3d.c, this.a.random.nextFloat() * 360.0F, 0.0F);
            this.a.addEntity(entityzombie, SpawnReason.VILLAGE_INVASION);
            BlockPosition blockposition = this.f.a();
            entityzombie.a(blockposition, this.f.b());
            return true;
        }
    }

    private Vec3D a(BlockPosition p_a_1_)
    {
        for (int i = 0; i < 10; ++i)
        {
            BlockPosition blockposition = p_a_1_.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);

            if (this.f.a(blockposition) && SpawnerCreature.a(EntityInsentient.EnumEntityPositionType.ON_GROUND, this.a, blockposition))
            {
                return new Vec3D((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
            }
        }

        return null;
    }
}
