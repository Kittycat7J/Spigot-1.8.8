package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class PersistentVillage extends PersistentBase
{
    private World world;
    private final List<BlockPosition> c = Lists.<BlockPosition>newArrayList();
    private final List<VillageDoor> d = Lists.<VillageDoor>newArrayList();
    private final List<Village> villages = Lists.<Village>newArrayList();
    private int time;

    public PersistentVillage(String message)
    {
        super(message);
    }

    public PersistentVillage(World p_i1208_1_)
    {
        super(a(p_i1208_1_.worldProvider));
        this.world = p_i1208_1_;
        this.c();
    }

    public void a(World p_a_1_)
    {
        this.world = p_a_1_;

        for (Village village : this.villages)
        {
            village.a(p_a_1_);
        }
    }

    public void a(BlockPosition p_a_1_)
    {
        if (this.c.size() <= 64)
        {
            if (!this.e(p_a_1_))
            {
                this.c.add(p_a_1_);
            }
        }
    }

    public void tick()
    {
        ++this.time;

        for (Village village : this.villages)
        {
            village.a(this.time);
        }

        this.e();
        this.f();
        this.g();

        if (this.time % 400 == 0)
        {
            this.c();
        }
    }

    private void e()
    {
        Iterator iterator = this.villages.iterator();

        while (iterator.hasNext())
        {
            Village village = (Village)iterator.next();

            if (village.g())
            {
                iterator.remove();
                this.c();
            }
        }
    }

    public List<Village> getVillages()
    {
        return this.villages;
    }

    public Village getClosestVillage(BlockPosition p_getClosestVillage_1_, int p_getClosestVillage_2_)
    {
        Village village = null;
        double d0 = 3.4028234663852886E38D;

        for (Village village1 : this.villages)
        {
            double d1 = village1.a().i(p_getClosestVillage_1_);

            if (d1 < d0)
            {
                float f = (float)(p_getClosestVillage_2_ + village1.b());

                if (d1 <= (double)(f * f))
                {
                    village = village1;
                    d0 = d1;
                }
            }
        }

        return village;
    }

    private void f()
    {
        if (!this.c.isEmpty())
        {
            this.b((BlockPosition)this.c.remove(0));
        }
    }

    private void g()
    {
        for (int i = 0; i < this.d.size(); ++i)
        {
            VillageDoor villagedoor = (VillageDoor)this.d.get(i);
            Village village = this.getClosestVillage(villagedoor.d(), 32);

            if (village == null)
            {
                village = new Village(this.world);
                this.villages.add(village);
                this.c();
            }

            village.a(villagedoor);
        }

        this.d.clear();
    }

    private void b(BlockPosition p_b_1_)
    {
        byte b0 = 16;
        byte b1 = 4;
        byte b2 = 16;

        for (int i = -b0; i < b0; ++i)
        {
            for (int j = -b1; j < b1; ++j)
            {
                for (int k = -b2; k < b2; ++k)
                {
                    BlockPosition blockposition = p_b_1_.a(i, j, k);

                    if (this.f(blockposition))
                    {
                        VillageDoor villagedoor = this.c(blockposition);

                        if (villagedoor == null)
                        {
                            this.d(blockposition);
                        }
                        else
                        {
                            villagedoor.a(this.time);
                        }
                    }
                }
            }
        }
    }

    private VillageDoor c(BlockPosition p_c_1_)
    {
        for (VillageDoor villagedoor : this.d)
        {
            if (villagedoor.d().getX() == p_c_1_.getX() && villagedoor.d().getZ() == p_c_1_.getZ() && Math.abs(villagedoor.d().getY() - p_c_1_.getY()) <= 1)
            {
                return villagedoor;
            }
        }

        for (Village village : this.villages)
        {
            VillageDoor villagedoor1 = village.e(p_c_1_);

            if (villagedoor1 != null)
            {
                return villagedoor1;
            }
        }

        return null;
    }

    private void d(BlockPosition p_d_1_)
    {
        EnumDirection enumdirection = BlockDoor.h(this.world, p_d_1_);
        EnumDirection enumdirection1 = enumdirection.opposite();
        int i = this.a(p_d_1_, enumdirection, 5);
        int j = this.a(p_d_1_, enumdirection1, i + 1);

        if (i != j)
        {
            this.d.add(new VillageDoor(p_d_1_, i < j ? enumdirection : enumdirection1, this.time));
        }
    }

    private int a(BlockPosition p_a_1_, EnumDirection p_a_2_, int p_a_3_)
    {
        int i = 0;

        for (int j = 1; j <= 5; ++j)
        {
            if (this.world.i(p_a_1_.shift(p_a_2_, j)))
            {
                ++i;

                if (i >= p_a_3_)
                {
                    return i;
                }
            }
        }

        return i;
    }

    private boolean e(BlockPosition p_e_1_)
    {
        for (BlockPosition blockposition : this.c)
        {
            if (blockposition.equals(p_e_1_))
            {
                return true;
            }
        }

        return false;
    }

    private boolean f(BlockPosition p_f_1_)
    {
        Block block = this.world.getType(p_f_1_).getBlock();
        return block instanceof BlockDoor ? block.getMaterial() == Material.WOOD : false;
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.time = p_a_1_.getInt("Tick");
        NBTTagList nbttaglist = p_a_1_.getList("Villages", 10);

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            Village village = new Village();
            village.a(nbttagcompound);
            this.villages.add(village);
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setInt("Tick", this.time);
        NBTTagList nbttaglist = new NBTTagList();

        for (Village village : this.villages)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            village.b(nbttagcompound);
            nbttaglist.add(nbttagcompound);
        }

        p_b_1_.set("Villages", nbttaglist);
    }

    public static String a(WorldProvider p_a_0_)
    {
        return "villages" + p_a_0_.getSuffix();
    }
}
