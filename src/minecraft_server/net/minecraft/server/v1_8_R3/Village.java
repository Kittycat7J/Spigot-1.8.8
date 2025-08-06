package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class Village
{
    private World a;
    private final List<VillageDoor> b = Lists.<VillageDoor>newArrayList();
    private BlockPosition c = BlockPosition.ZERO;
    private BlockPosition d = BlockPosition.ZERO;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private TreeMap<String, Integer> j = new TreeMap();
    private List<Village.Aggressor> k = Lists.<Village.Aggressor>newArrayList();
    private int l;

    public Village()
    {
    }

    public Village(World p_i374_1_)
    {
        this.a = p_i374_1_;
    }

    public void a(World p_a_1_)
    {
        this.a = p_a_1_;
    }

    public void a(int p_a_1_)
    {
        this.g = p_a_1_;
        this.m();
        this.l();

        if (p_a_1_ % 20 == 0)
        {
            this.k();
        }

        if (p_a_1_ % 30 == 0)
        {
            this.j();
        }

        int i = this.h / 10;

        if (this.l < i && this.b.size() > 20 && this.a.random.nextInt(7000) == 0)
        {
            Vec3D vec3d = this.a(this.d, 2, 4, 2);

            if (vec3d != null)
            {
                EntityIronGolem entityirongolem = new EntityIronGolem(this.a);
                entityirongolem.setPosition(vec3d.a, vec3d.b, vec3d.c);
                this.a.addEntity(entityirongolem, SpawnReason.VILLAGE_DEFENSE);
                ++this.l;
            }
        }
    }

    private Vec3D a(BlockPosition p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        for (int i = 0; i < 10; ++i)
        {
            BlockPosition blockposition = p_a_1_.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);

            if (this.a(blockposition) && this.a(new BlockPosition(p_a_2_, p_a_3_, p_a_4_), blockposition))
            {
                return new Vec3D((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
            }
        }

        return null;
    }

    private boolean a(BlockPosition p_a_1_, BlockPosition p_a_2_)
    {
        if (!World.a((IBlockAccess)this.a, (BlockPosition)p_a_2_.down()))
        {
            return false;
        }
        else
        {
            int i = p_a_2_.getX() - p_a_1_.getX() / 2;
            int j = p_a_2_.getZ() - p_a_1_.getZ() / 2;

            for (int k = i; k < i + p_a_1_.getX(); ++k)
            {
                for (int l = p_a_2_.getY(); l < p_a_2_.getY() + p_a_1_.getY(); ++l)
                {
                    for (int i1 = j; i1 < j + p_a_1_.getZ(); ++i1)
                    {
                        if (this.a.getType(new BlockPosition(k, l, i1)).getBlock().isOccluding())
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private void j()
    {
        List list = this.a.a(EntityIronGolem.class, new AxisAlignedBB((double)(this.d.getX() - this.e), (double)(this.d.getY() - 4), (double)(this.d.getZ() - this.e), (double)(this.d.getX() + this.e), (double)(this.d.getY() + 4), (double)(this.d.getZ() + this.e)));
        this.l = list.size();
    }

    private void k()
    {
        List list = this.a.a(EntityVillager.class, new AxisAlignedBB((double)(this.d.getX() - this.e), (double)(this.d.getY() - 4), (double)(this.d.getZ() - this.e), (double)(this.d.getX() + this.e), (double)(this.d.getY() + 4), (double)(this.d.getZ() + this.e)));
        this.h = list.size();

        if (this.h == 0)
        {
            this.j.clear();
        }
    }

    public BlockPosition a()
    {
        return this.d;
    }

    public int b()
    {
        return this.e;
    }

    public int c()
    {
        return this.b.size();
    }

    public int d()
    {
        return this.g - this.f;
    }

    public int e()
    {
        return this.h;
    }

    public boolean a(BlockPosition p_a_1_)
    {
        return this.d.i(p_a_1_) < (double)(this.e * this.e);
    }

    public List<VillageDoor> f()
    {
        return this.b;
    }

    public VillageDoor b(BlockPosition p_b_1_)
    {
        VillageDoor villagedoor = null;
        int i = Integer.MAX_VALUE;

        for (VillageDoor villagedoor1 : this.b)
        {
            int j = villagedoor1.a(p_b_1_);

            if (j < i)
            {
                villagedoor = villagedoor1;
                i = j;
            }
        }

        return villagedoor;
    }

    public VillageDoor c(BlockPosition p_c_1_)
    {
        VillageDoor villagedoor = null;
        int i = Integer.MAX_VALUE;

        for (VillageDoor villagedoor1 : this.b)
        {
            int j = villagedoor1.a(p_c_1_);

            if (j > 256)
            {
                j = j * 1000;
            }
            else
            {
                j = villagedoor1.c();
            }

            if (j < i)
            {
                villagedoor = villagedoor1;
                i = j;
            }
        }

        return villagedoor;
    }

    public VillageDoor e(BlockPosition p_e_1_)
    {
        if (this.d.i(p_e_1_) > (double)(this.e * this.e))
        {
            return null;
        }
        else
        {
            for (VillageDoor villagedoor : this.b)
            {
                if (villagedoor.d().getX() == p_e_1_.getX() && villagedoor.d().getZ() == p_e_1_.getZ() && Math.abs(villagedoor.d().getY() - p_e_1_.getY()) <= 1)
                {
                    return villagedoor;
                }
            }

            return null;
        }
    }

    public void a(VillageDoor p_a_1_)
    {
        this.b.add(p_a_1_);
        this.c = this.c.a(p_a_1_.d());
        this.n();
        this.f = p_a_1_.h();
    }

    public boolean g()
    {
        return this.b.isEmpty();
    }

    public void a(EntityLiving p_a_1_)
    {
        for (Village.Aggressor village$aggressor : this.k)
        {
            if (village$aggressor.a == p_a_1_)
            {
                village$aggressor.b = this.g;
                return;
            }
        }

        this.k.add(new Village.Aggressor(p_a_1_, this.g));
    }

    public EntityLiving b(EntityLiving p_b_1_)
    {
        double d0 = Double.MAX_VALUE;
        Village.Aggressor village$aggressor = null;

        for (int i = 0; i < this.k.size(); ++i)
        {
            Village.Aggressor village$aggressor1 = (Village.Aggressor)this.k.get(i);
            double d1 = village$aggressor1.a.h(p_b_1_);

            if (d1 <= d0)
            {
                village$aggressor = village$aggressor1;
                d0 = d1;
            }
        }

        return village$aggressor != null ? village$aggressor.a : null;
    }

    public EntityHuman c(EntityLiving p_c_1_)
    {
        double d0 = Double.MAX_VALUE;
        EntityHuman entityhuman = null;

        for (String s : this.j.keySet())
        {
            if (this.d(s))
            {
                EntityHuman entityhuman1 = this.a.a(s);

                if (entityhuman1 != null)
                {
                    double d1 = entityhuman1.h(p_c_1_);

                    if (d1 <= d0)
                    {
                        entityhuman = entityhuman1;
                        d0 = d1;
                    }
                }
            }
        }

        return entityhuman;
    }

    private void l()
    {
        Iterator iterator = this.k.iterator();

        while (iterator.hasNext())
        {
            Village.Aggressor village$aggressor = (Village.Aggressor)iterator.next();

            if (!village$aggressor.a.isAlive() || Math.abs(this.g - village$aggressor.b) > 300)
            {
                iterator.remove();
            }
        }
    }

    private void m()
    {
        boolean flag = false;
        boolean flag1 = this.a.random.nextInt(50) == 0;
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext())
        {
            VillageDoor villagedoor = (VillageDoor)iterator.next();

            if (flag1)
            {
                villagedoor.a();
            }

            if (!this.f(villagedoor.d()) || Math.abs(this.g - villagedoor.h()) > 1200)
            {
                this.c = this.c.b(villagedoor.d());
                flag = true;
                villagedoor.a(true);
                iterator.remove();
            }
        }

        if (flag)
        {
            this.n();
        }
    }

    private boolean f(BlockPosition p_f_1_)
    {
        Block block = this.a.getType(p_f_1_).getBlock();
        return block instanceof BlockDoor ? block.getMaterial() == Material.WOOD : false;
    }

    private void n()
    {
        int i = this.b.size();

        if (i == 0)
        {
            this.d = new BlockPosition(0, 0, 0);
            this.e = 0;
        }
        else
        {
            this.d = new BlockPosition(this.c.getX() / i, this.c.getY() / i, this.c.getZ() / i);
            int j = 0;

            for (VillageDoor villagedoor : this.b)
            {
                j = Math.max(villagedoor.a(this.d), j);
            }

            this.e = Math.max(32, (int)Math.sqrt((double)j) + 1);
        }
    }

    public int a(String p_a_1_)
    {
        Integer integer = (Integer)this.j.get(p_a_1_);
        return integer != null ? integer.intValue() : 0;
    }

    public int a(String p_a_1_, int p_a_2_)
    {
        int i = this.a(p_a_1_);
        int j = MathHelper.clamp(i + p_a_2_, -30, 10);
        this.j.put(p_a_1_, Integer.valueOf(j));
        return j;
    }

    public boolean d(String p_d_1_)
    {
        return this.a(p_d_1_) <= -15;
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.h = p_a_1_.getInt("PopSize");
        this.e = p_a_1_.getInt("Radius");
        this.l = p_a_1_.getInt("Golems");
        this.f = p_a_1_.getInt("Stable");
        this.g = p_a_1_.getInt("Tick");
        this.i = p_a_1_.getInt("MTick");
        this.d = new BlockPosition(p_a_1_.getInt("CX"), p_a_1_.getInt("CY"), p_a_1_.getInt("CZ"));
        this.c = new BlockPosition(p_a_1_.getInt("ACX"), p_a_1_.getInt("ACY"), p_a_1_.getInt("ACZ"));
        NBTTagList nbttaglist = p_a_1_.getList("Doors", 10);

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            VillageDoor villagedoor = new VillageDoor(new BlockPosition(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z")), nbttagcompound.getInt("IDX"), nbttagcompound.getInt("IDZ"), nbttagcompound.getInt("TS"));
            this.b.add(villagedoor);
        }

        NBTTagList nbttaglist1 = p_a_1_.getList("Players", 10);

        for (int j = 0; j < nbttaglist1.size(); ++j)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist1.get(j);

            if (nbttagcompound1.hasKey("UUID"))
            {
                UserCache usercache = MinecraftServer.getServer().getUserCache();
                GameProfile gameprofile = usercache.a(UUID.fromString(nbttagcompound1.getString("UUID")));

                if (gameprofile != null)
                {
                    this.j.put(gameprofile.getName(), Integer.valueOf(nbttagcompound1.getInt("S")));
                }
            }
            else
            {
                this.j.put(nbttagcompound1.getString("Name"), Integer.valueOf(nbttagcompound1.getInt("S")));
            }
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setInt("PopSize", this.h);
        p_b_1_.setInt("Radius", this.e);
        p_b_1_.setInt("Golems", this.l);
        p_b_1_.setInt("Stable", this.f);
        p_b_1_.setInt("Tick", this.g);
        p_b_1_.setInt("MTick", this.i);
        p_b_1_.setInt("CX", this.d.getX());
        p_b_1_.setInt("CY", this.d.getY());
        p_b_1_.setInt("CZ", this.d.getZ());
        p_b_1_.setInt("ACX", this.c.getX());
        p_b_1_.setInt("ACY", this.c.getY());
        p_b_1_.setInt("ACZ", this.c.getZ());
        NBTTagList nbttaglist = new NBTTagList();

        for (VillageDoor villagedoor : this.b)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("X", villagedoor.d().getX());
            nbttagcompound.setInt("Y", villagedoor.d().getY());
            nbttagcompound.setInt("Z", villagedoor.d().getZ());
            nbttagcompound.setInt("IDX", villagedoor.f());
            nbttagcompound.setInt("IDZ", villagedoor.g());
            nbttagcompound.setInt("TS", villagedoor.h());
            nbttaglist.add(nbttagcompound);
        }

        p_b_1_.set("Doors", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (String s : this.j.keySet())
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            UserCache usercache = MinecraftServer.getServer().getUserCache();
            GameProfile gameprofile = usercache.getProfile(s);

            if (gameprofile != null)
            {
                nbttagcompound1.setString("UUID", gameprofile.getId().toString());
                nbttagcompound1.setInt("S", ((Integer)this.j.get(s)).intValue());
                nbttaglist1.add(nbttagcompound1);
            }
        }

        p_b_1_.set("Players", nbttaglist1);
    }

    public void h()
    {
        this.i = this.g;
    }

    public boolean i()
    {
        return this.i == 0 || this.g - this.i >= 3600;
    }

    public void b(int p_b_1_)
    {
        for (String s : this.j.keySet())
        {
            this.a(s, p_b_1_);
        }
    }

    class Aggressor
    {
        public EntityLiving a;
        public int b;

        Aggressor(EntityLiving p_i146_2_, int p_i146_3_)
        {
            this.a = p_i146_2_;
            this.b = p_i146_3_;
        }
    }
}
