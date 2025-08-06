package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPlant extends Block
{
    protected BlockPlant()
    {
        this(Material.PLANT);
    }

    protected BlockPlant(Material p_i383_1_)
    {
        this(p_i383_1_, p_i383_1_.r());
    }

    protected BlockPlant(Material p_i384_1_, MaterialMapColor p_i384_2_)
    {
        super(p_i384_1_, p_i384_2_);
        this.a(true);
        float f = 0.2F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        this.a(CreativeModeTab.c);
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) && this.c(p_canPlace_1_.getType(p_canPlace_2_.down()).getBlock());
    }

    protected boolean c(Block p_c_1_)
    {
        return p_c_1_ == Blocks.GRASS || p_c_1_ == Blocks.DIRT || p_c_1_ == Blocks.FARMLAND;
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        super.doPhysics(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, p_doPhysics_4_);
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        this.e(p_b_1_, p_b_2_, p_b_3_);
    }

    protected void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!this.f(p_e_1_, p_e_2_, p_e_3_))
        {
            org.bukkit.block.Block block = p_e_1_.getWorld().getBlockAt(p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ());
            BlockPhysicsEvent blockphysicsevent = new BlockPhysicsEvent(block, block.getTypeId());
            p_e_1_.getServer().getPluginManager().callEvent(blockphysicsevent);

            if (blockphysicsevent.isCancelled())
            {
                return;
            }

            this.b(p_e_1_, p_e_2_, p_e_3_, 0);
            p_e_1_.setTypeAndData(p_e_2_, Blocks.AIR.getBlockData(), 3);
        }
    }

    public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        return this.c(p_f_1_.getType(p_f_2_.down()).getBlock());
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
}
