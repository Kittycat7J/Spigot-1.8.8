package net.minecraft.server.v1_8_R3;

import java.util.Random;

public abstract class BlockFluids extends Block
{
    public static final BlockStateInteger LEVEL = BlockStateInteger.of("level", 0, 15);

    protected BlockFluids(Material p_i628_1_)
    {
        super(p_i628_1_);
        this.j(this.blockStateList.getBlockData().set(LEVEL, Integer.valueOf(0)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.a(true);
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_)
    {
        return this.material != Material.LAVA;
    }

    public static float b(int p_b_0_)
    {
        if (p_b_0_ >= 8)
        {
            p_b_0_ = 0;
        }

        return (float)(p_b_0_ + 1) / 9.0F;
    }

    protected int e(IBlockAccess p_e_1_, BlockPosition p_e_2_)
    {
        return p_e_1_.getType(p_e_2_).getBlock().getMaterial() == this.material ? ((Integer)p_e_1_.getType(p_e_2_).get(LEVEL)).intValue() : -1;
    }

    protected int f(IBlockAccess p_f_1_, BlockPosition p_f_2_)
    {
        int i = this.e(p_f_1_, p_f_2_);
        return i >= 8 ? 0 : i;
    }

    public boolean d()
    {
        return false;
    }

    public boolean c()
    {
        return false;
    }

    public boolean a(IBlockData p_a_1_, boolean p_a_2_)
    {
        return p_a_2_ && ((Integer)p_a_1_.get(LEVEL)).intValue() == 0;
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_, EnumDirection p_b_3_)
    {
        Material material = p_b_1_.getType(p_b_2_).getBlock().getMaterial();
        return material == this.material ? false : (p_b_3_ == EnumDirection.UP ? true : (material == Material.ICE ? false : super.b(p_b_1_, p_b_2_, p_b_3_)));
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public int b()
    {
        return 1;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    protected Vec3D h(IBlockAccess p_h_1_, BlockPosition p_h_2_)
    {
        Vec3D vec3d = new Vec3D(0.0D, 0.0D, 0.0D);
        int i = this.f(p_h_1_, p_h_2_);

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            BlockPosition blockposition = p_h_2_.shift(enumdirection);
            int j = this.f(p_h_1_, blockposition);

            if (j < 0)
            {
                if (!p_h_1_.getType(blockposition).getBlock().getMaterial().isSolid())
                {
                    j = this.f(p_h_1_, blockposition.down());

                    if (j >= 0)
                    {
                        int k = j - (i - 8);
                        vec3d = vec3d.add((double)((blockposition.getX() - p_h_2_.getX()) * k), (double)((blockposition.getY() - p_h_2_.getY()) * k), (double)((blockposition.getZ() - p_h_2_.getZ()) * k));
                    }
                }
            }
            else if (j >= 0)
            {
                int l = j - i;
                vec3d = vec3d.add((double)((blockposition.getX() - p_h_2_.getX()) * l), (double)((blockposition.getY() - p_h_2_.getY()) * l), (double)((blockposition.getZ() - p_h_2_.getZ()) * l));
            }
        }

        if (((Integer)p_h_1_.getType(p_h_2_).get(LEVEL)).intValue() >= 8)
        {
            for (EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                BlockPosition blockposition1 = p_h_2_.shift(enumdirection1);

                if (this.b(p_h_1_, blockposition1, enumdirection1) || this.b(p_h_1_, blockposition1.up(), enumdirection1))
                {
                    vec3d = vec3d.a().add(0.0D, -6.0D, 0.0D);
                    break;
                }
            }
        }

        return vec3d.a();
    }

    public Vec3D a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_, Vec3D p_a_4_)
    {
        return p_a_4_.e(this.h(p_a_1_, p_a_2_));
    }

    public int a(World p_a_1_)
    {
        return this.material == Material.WATER ? 5 : (this.material == Material.LAVA ? (p_a_1_.worldProvider.o() ? 10 : 30) : 0);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    public boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (this.material == Material.LAVA)
        {
            boolean flag = false;

            for (EnumDirection enumdirection : EnumDirection.values())
            {
                if (enumdirection != EnumDirection.DOWN && p_e_1_.getType(p_e_2_.shift(enumdirection)).getBlock().getMaterial() == Material.WATER)
                {
                    flag = true;
                    break;
                }
            }

            if (flag)
            {
                Integer integer = (Integer)p_e_3_.get(LEVEL);

                if (integer.intValue() == 0)
                {
                    p_e_1_.setTypeUpdate(p_e_2_, Blocks.OBSIDIAN.getBlockData());
                    this.fizz(p_e_1_, p_e_2_);
                    return true;
                }

                if (integer.intValue() <= 4)
                {
                    p_e_1_.setTypeUpdate(p_e_2_, Blocks.COBBLESTONE.getBlockData());
                    this.fizz(p_e_1_, p_e_2_);
                    return true;
                }
            }
        }

        return false;
    }

    protected void fizz(World p_fizz_1_, BlockPosition p_fizz_2_)
    {
        double d0 = (double)p_fizz_2_.getX();
        double d1 = (double)p_fizz_2_.getY();
        double d2 = (double)p_fizz_2_.getZ();
        p_fizz_1_.makeSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (p_fizz_1_.random.nextFloat() - p_fizz_1_.random.nextFloat()) * 0.8F);

        for (int i = 0; i < 8; ++i)
        {
            p_fizz_1_.addParticle(EnumParticle.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(LEVEL, Integer.valueOf(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(LEVEL)).intValue();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {LEVEL});
    }

    public static BlockFlowing a(Material p_a_0_)
    {
        if (p_a_0_ == Material.WATER)
        {
            return Blocks.FLOWING_WATER;
        }
        else if (p_a_0_ == Material.LAVA)
        {
            return Blocks.FLOWING_LAVA;
        }
        else
        {
            throw new IllegalArgumentException("Invalid material");
        }
    }

    public static BlockStationary b(Material p_b_0_)
    {
        if (p_b_0_ == Material.WATER)
        {
            return Blocks.WATER;
        }
        else if (p_b_0_ == Material.LAVA)
        {
            return Blocks.LAVA;
        }
        else
        {
            throw new IllegalArgumentException("Invalid material");
        }
    }
}
