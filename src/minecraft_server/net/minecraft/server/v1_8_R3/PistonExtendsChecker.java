package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class PistonExtendsChecker
{
    private final World a;
    private final BlockPosition b;
    private final BlockPosition c;
    private final EnumDirection d;
    private final List<BlockPosition> e = Lists.<BlockPosition>newArrayList();
    private final List<BlockPosition> f = Lists.<BlockPosition>newArrayList();

    public PistonExtendsChecker(World p_i660_1_, BlockPosition p_i660_2_, EnumDirection p_i660_3_, boolean p_i660_4_)
    {
        this.a = p_i660_1_;
        this.b = p_i660_2_;

        if (p_i660_4_)
        {
            this.d = p_i660_3_;
            this.c = p_i660_2_.shift(p_i660_3_);
        }
        else
        {
            this.d = p_i660_3_.opposite();
            this.c = p_i660_2_.shift(p_i660_3_, 2);
        }
    }

    public boolean a()
    {
        this.e.clear();
        this.f.clear();
        Block block = this.a.getType(this.c).getBlock();

        if (!BlockPiston.a(block, this.a, this.c, this.d, false))
        {
            if (block.k() != 1)
            {
                return false;
            }
            else
            {
                this.f.add(this.c);
                return true;
            }
        }
        else if (!this.a(this.c))
        {
            return false;
        }
        else
        {
            for (int i = 0; i < this.e.size(); ++i)
            {
                BlockPosition blockposition = (BlockPosition)this.e.get(i);

                if (this.a.getType(blockposition).getBlock() == Blocks.SLIME && !this.b(blockposition))
                {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean a(BlockPosition p_a_1_)
    {
        Block block = this.a.getType(p_a_1_).getBlock();

        if (block.getMaterial() == Material.AIR)
        {
            return true;
        }
        else if (!BlockPiston.a(block, this.a, p_a_1_, this.d, false))
        {
            return true;
        }
        else if (p_a_1_.equals(this.b))
        {
            return true;
        }
        else if (this.e.contains(p_a_1_))
        {
            return true;
        }
        else
        {
            int i = 1;

            if (i + this.e.size() > 12)
            {
                return false;
            }
            else
            {
                while (block == Blocks.SLIME)
                {
                    BlockPosition blockposition = p_a_1_.shift(this.d.opposite(), i);
                    block = this.a.getType(blockposition).getBlock();

                    if (block.getMaterial() == Material.AIR || !BlockPiston.a(block, this.a, blockposition, this.d, false) || blockposition.equals(this.b))
                    {
                        break;
                    }

                    ++i;

                    if (i + this.e.size() > 12)
                    {
                        return false;
                    }
                }

                int i1 = 0;

                for (int j = i - 1; j >= 0; --j)
                {
                    this.e.add(p_a_1_.shift(this.d.opposite(), j));
                    ++i1;
                }

                int j1 = 1;

                while (true)
                {
                    BlockPosition blockposition1 = p_a_1_.shift(this.d, j1);
                    int k = this.e.indexOf(blockposition1);

                    if (k > -1)
                    {
                        this.a(i1, k);

                        for (int l = 0; l <= k + i1; ++l)
                        {
                            BlockPosition blockposition2 = (BlockPosition)this.e.get(l);

                            if (this.a.getType(blockposition2).getBlock() == Blocks.SLIME && !this.b(blockposition2))
                            {
                                return false;
                            }
                        }

                        return true;
                    }

                    block = this.a.getType(blockposition1).getBlock();

                    if (block.getMaterial() == Material.AIR)
                    {
                        return true;
                    }

                    if (!BlockPiston.a(block, this.a, blockposition1, this.d, true) || blockposition1.equals(this.b))
                    {
                        return false;
                    }

                    if (block.k() == 1)
                    {
                        this.f.add(blockposition1);
                        return true;
                    }

                    if (this.e.size() >= 12)
                    {
                        return false;
                    }

                    this.e.add(blockposition1);
                    ++i1;
                    ++j1;
                }
            }
        }
    }

    private void a(int p_a_1_, int p_a_2_)
    {
        ArrayList arraylist = Lists.newArrayList();
        ArrayList arraylist1 = Lists.newArrayList();
        ArrayList arraylist2 = Lists.newArrayList();
        arraylist.addAll(this.e.subList(0, p_a_2_));
        arraylist1.addAll(this.e.subList(this.e.size() - p_a_1_, this.e.size()));
        arraylist2.addAll(this.e.subList(p_a_2_, this.e.size() - p_a_1_));
        this.e.clear();
        this.e.addAll(arraylist);
        this.e.addAll(arraylist1);
        this.e.addAll(arraylist2);
    }

    private boolean b(BlockPosition p_b_1_)
    {
        for (EnumDirection enumdirection : EnumDirection.values())
        {
            if (enumdirection.k() != this.d.k() && !this.a(p_b_1_.shift(enumdirection)))
            {
                return false;
            }
        }

        return true;
    }

    public List<BlockPosition> getMovedBlocks()
    {
        return this.e;
    }

    public List<BlockPosition> getBrokenBlocks()
    {
        return this.f;
    }
}
