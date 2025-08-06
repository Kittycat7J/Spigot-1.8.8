package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.PluginManager;

public abstract class BlockPressurePlateAbstract extends Block
{
    protected BlockPressurePlateAbstract(Material p_i8_1_)
    {
        this(p_i8_1_, p_i8_1_.r());
    }

    protected BlockPressurePlateAbstract(Material p_i9_1_, MaterialMapColor p_i9_2_)
    {
        super(p_i9_1_, p_i9_2_);
        this.a(CreativeModeTab.d);
        this.a(true);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.d(p_updateShape_1_.getType(p_updateShape_2_));
    }

    protected void d(IBlockData p_d_1_)
    {
        boolean flag = this.e(p_d_1_) > 0;

        if (flag)
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
        }
        else
        {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
        }
    }

    public int a(World p_a_1_)
    {
        return 20;
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_)
    {
        return true;
    }

    public boolean g()
    {
        return true;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return this.m(p_canPlace_1_, p_canPlace_2_.down());
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!this.m(p_doPhysics_1_, p_doPhysics_2_.down()))
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    private boolean m(World p_m_1_, BlockPosition p_m_2_)
    {
        return World.a((IBlockAccess)p_m_1_, (BlockPosition)p_m_2_) || p_m_1_.getType(p_m_2_).getBlock() instanceof BlockFence;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_)
    {
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide)
        {
            int i = this.e(p_b_3_);

            if (i > 0)
            {
                this.a(p_b_1_, p_b_2_, p_b_3_, i);
            }
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        if (!p_a_1_.isClientSide)
        {
            int i = this.e(p_a_3_);

            if (i == 0)
            {
                this.a(p_a_1_, p_a_2_, p_a_3_, i);
            }
        }
    }

    protected void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_)
    {
        int i = this.f(p_a_1_, p_a_2_);
        boolean flag = p_a_4_ > 0;
        boolean flag1 = i > 0;
        org.bukkit.World world = p_a_1_.getWorld();
        PluginManager pluginmanager = p_a_1_.getServer().getPluginManager();

        if (flag != flag1)
        {
            BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(world.getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ()), p_a_4_, i);
            pluginmanager.callEvent(blockredstoneevent);
            flag1 = blockredstoneevent.getNewCurrent() > 0;
            i = blockredstoneevent.getNewCurrent();
        }

        if (p_a_4_ != i)
        {
            p_a_3_ = this.a(p_a_3_, i);
            p_a_1_.setTypeAndData(p_a_2_, p_a_3_, 2);
            this.e(p_a_1_, p_a_2_);
            p_a_1_.b(p_a_2_, p_a_2_);
        }

        if (!flag1 && flag)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
        }
        else if (flag1 && !flag)
        {
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.1D, (double)p_a_2_.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1)
        {
            p_a_1_.a((BlockPosition)p_a_2_, (Block)this, this.a(p_a_1_));
        }
    }

    protected AxisAlignedBB getBoundingBox(BlockPosition p_getBoundingBox_1_)
    {
        return new AxisAlignedBB((double)((float)p_getBoundingBox_1_.getX() + 0.125F), (double)p_getBoundingBox_1_.getY(), (double)((float)p_getBoundingBox_1_.getZ() + 0.125F), (double)((float)(p_getBoundingBox_1_.getX() + 1) - 0.125F), (double)p_getBoundingBox_1_.getY() + 0.25D, (double)((float)(p_getBoundingBox_1_.getZ() + 1) - 0.125F));
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        if (this.e(p_remove_3_) > 0)
        {
            this.e(p_remove_1_, p_remove_2_);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    protected void e(World p_e_1_, BlockPosition p_e_2_)
    {
        p_e_1_.applyPhysics(p_e_2_, this);
        p_e_1_.applyPhysics(p_e_2_.down(), this);
    }

    public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_)
    {
        return this.e(p_a_3_);
    }

    public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_)
    {
        return p_b_4_ == EnumDirection.UP ? this.e(p_b_3_) : 0;
    }

    public boolean isPowerSource()
    {
        return true;
    }

    public void j()
    {
        this.a(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
    }

    public int k()
    {
        return 1;
    }

    protected abstract int f(World var1, BlockPosition var2);

    protected abstract int e(IBlockData var1);

    protected abstract IBlockData a(IBlockData var1, int var2);
}
