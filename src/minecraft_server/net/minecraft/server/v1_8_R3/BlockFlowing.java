package net.minecraft.server.v1_8_R3;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.Server;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFlowing extends BlockFluids
{
    int a;

    protected BlockFlowing(Material p_i243_1_)
    {
        super(p_i243_1_);
    }

    private void f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        p_f_1_.setTypeAndData(p_f_2_, b(this.material).getBlockData().set(LEVEL, (Integer)p_f_3_.get(LEVEL)), 2);
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        org.bukkit.World world = p_b_1_.getWorld();
        Server server = p_b_1_.getServer();
        org.bukkit.block.Block block = world == null ? null : world.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
        int i = ((Integer)p_b_3_.get(LEVEL)).intValue();
        byte b0 = 1;

        if (this.material == Material.LAVA && !p_b_1_.worldProvider.n())
        {
            b0 = 2;
        }

        int j = this.a(p_b_1_);

        if (i > 0)
        {
            int k = -100;
            this.a = 0;

            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                k = this.a(p_b_1_, p_b_2_.shift(enumdirection), k);
            }

            int i1 = k + b0;

            if (i1 >= 8 || k < 0)
            {
                i1 = -1;
            }

            if (this.e(p_b_1_, p_b_2_.up()) >= 0)
            {
                int l = this.e(p_b_1_, p_b_2_.up());

                if (l >= 8)
                {
                    i1 = l;
                }
                else
                {
                    i1 = l + 8;
                }
            }

            if (this.a >= 2 && this.material == Material.WATER)
            {
                IBlockData iblockdata = p_b_1_.getType(p_b_2_.down());

                if (iblockdata.getBlock().getMaterial().isBuildable())
                {
                    i1 = 0;
                }
                else if (iblockdata.getBlock().getMaterial() == this.material && ((Integer)iblockdata.get(LEVEL)).intValue() == 0)
                {
                    i1 = 0;
                }
            }

            if (this.material == Material.LAVA && i < 8 && i1 < 8 && i1 > i && p_b_4_.nextInt(4) != 0)
            {
                j *= 4;
            }

            if (i1 == i)
            {
                this.f(p_b_1_, p_b_2_, p_b_3_);
            }
            else
            {
                i = i1;

                if (i1 < 0)
                {
                    p_b_1_.setAir(p_b_2_);
                }
                else
                {
                    p_b_3_ = p_b_3_.set(LEVEL, Integer.valueOf(i1));
                    p_b_1_.setTypeAndData(p_b_2_, p_b_3_, 2);
                    p_b_1_.a((BlockPosition)p_b_2_, (Block)this, j);
                    p_b_1_.applyPhysics(p_b_2_, this);
                }
            }
        }
        else
        {
            this.f(p_b_1_, p_b_2_, p_b_3_);
        }

        IBlockData iblockdata1 = p_b_1_.getType(p_b_2_.down());

        if (this.h(p_b_1_, p_b_2_.down(), iblockdata1))
        {
            BlockFromToEvent blockfromtoevent1 = new BlockFromToEvent(block, BlockFace.DOWN);

            if (server != null)
            {
                server.getPluginManager().callEvent(blockfromtoevent1);
            }

            if (!blockfromtoevent1.isCancelled())
            {
                if (this.material == Material.LAVA && p_b_1_.getType(p_b_2_.down()).getBlock().getMaterial() == Material.WATER)
                {
                    p_b_1_.setTypeUpdate(p_b_2_.down(), Blocks.STONE.getBlockData());
                    this.fizz(p_b_1_, p_b_2_.down());
                    return;
                }

                if (i >= 8)
                {
                    this.flow(p_b_1_, p_b_2_.down(), iblockdata1, i);
                }
                else
                {
                    this.flow(p_b_1_, p_b_2_.down(), iblockdata1, i + 8);
                }
            }
        }
        else if (i >= 0 && (i == 0 || this.g(p_b_1_, p_b_2_.down(), iblockdata1)))
        {
            Set set = this.f(p_b_1_, p_b_2_);
            int j1 = i + b0;

            if (i >= 8)
            {
                j1 = 1;
            }

            if (j1 >= 8)
            {
                return;
            }

            for (EnumDirection enumdirection1 : set)
            {
                BlockFromToEvent blockfromtoevent = new BlockFromToEvent(block, CraftBlock.notchToBlockFace(enumdirection1));

                if (server != null)
                {
                    server.getPluginManager().callEvent(blockfromtoevent);
                }

                if (!blockfromtoevent.isCancelled())
                {
                    this.flow(p_b_1_, p_b_2_.shift(enumdirection1), p_b_1_.getType(p_b_2_.shift(enumdirection1)), j1);
                }
            }
        }
    }

    private void flow(World p_flow_1_, BlockPosition p_flow_2_, IBlockData p_flow_3_, int p_flow_4_)
    {
        if (p_flow_1_.isLoaded(p_flow_2_) && this.h(p_flow_1_, p_flow_2_, p_flow_3_))
        {
            if (p_flow_3_.getBlock() != Blocks.AIR)
            {
                if (this.material == Material.LAVA)
                {
                    this.fizz(p_flow_1_, p_flow_2_);
                }
                else
                {
                    p_flow_3_.getBlock().b(p_flow_1_, p_flow_2_, p_flow_3_, 0);
                }
            }

            p_flow_1_.setTypeAndData(p_flow_2_, this.getBlockData().set(LEVEL, Integer.valueOf(p_flow_4_)), 3);
        }
    }

    private int a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_, EnumDirection p_a_4_)
    {
        int i = 1000;

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            if (enumdirection != p_a_4_)
            {
                BlockPosition blockposition = p_a_2_.shift(enumdirection);
                IBlockData iblockdata = p_a_1_.getType(blockposition);

                if (!this.g(p_a_1_, blockposition, iblockdata) && (iblockdata.getBlock().getMaterial() != this.material || ((Integer)iblockdata.get(LEVEL)).intValue() > 0))
                {
                    if (!this.g(p_a_1_, blockposition.down(), iblockdata))
                    {
                        return p_a_3_;
                    }

                    if (p_a_3_ < 4)
                    {
                        int j = this.a(p_a_1_, blockposition, p_a_3_ + 1, enumdirection.opposite());

                        if (j < i)
                        {
                            i = j;
                        }
                    }
                }
            }
        }

        return i;
    }

    private Set<EnumDirection> f(World p_f_1_, BlockPosition p_f_2_)
    {
        int i = 1000;
        EnumSet enumset = EnumSet.noneOf(EnumDirection.class);

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            BlockPosition blockposition = p_f_2_.shift(enumdirection);
            IBlockData iblockdata = p_f_1_.getType(blockposition);

            if (!this.g(p_f_1_, blockposition, iblockdata) && (iblockdata.getBlock().getMaterial() != this.material || ((Integer)iblockdata.get(LEVEL)).intValue() > 0))
            {
                int j;

                if (this.g(p_f_1_, blockposition.down(), p_f_1_.getType(blockposition.down())))
                {
                    j = this.a(p_f_1_, blockposition, 1, enumdirection.opposite());
                }
                else
                {
                    j = 0;
                }

                if (j < i)
                {
                    enumset.clear();
                }

                if (j <= i)
                {
                    enumset.add(enumdirection);
                    i = j;
                }
            }
        }

        return enumset;
    }

    private boolean g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_)
    {
        Block block = p_g_1_.getType(p_g_2_).getBlock();
        return !(block instanceof BlockDoor) && block != Blocks.STANDING_SIGN && block != Blocks.LADDER && block != Blocks.REEDS ? (block.material == Material.PORTAL ? true : block.material.isSolid()) : true;
    }

    protected int a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_)
    {
        int i = this.e(p_a_1_, p_a_2_);

        if (i < 0)
        {
            return p_a_3_;
        }
        else
        {
            if (i == 0)
            {
                ++this.a;
            }

            if (i >= 8)
            {
                i = 0;
            }

            return p_a_3_ >= 0 && i >= p_a_3_ ? p_a_3_ : i;
        }
    }

    private boolean h(World p_h_1_, BlockPosition p_h_2_, IBlockData p_h_3_)
    {
        Material material = p_h_3_.getBlock().getMaterial();
        return material != this.material && material != Material.LAVA && !this.g(p_h_1_, p_h_2_, p_h_3_);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        if (!this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_))
        {
            p_onPlace_1_.a((BlockPosition)p_onPlace_2_, (Block)this, this.a(p_onPlace_1_));
        }
    }
}
