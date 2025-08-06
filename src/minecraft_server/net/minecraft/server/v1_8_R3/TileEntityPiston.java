package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;

public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox
{
    private IBlockData a;
    private EnumDirection f;
    private boolean g;
    private boolean h;
    private float i;
    private float j;
    private List<Entity> k = Lists.<Entity>newArrayList();

    public TileEntityPiston()
    {
    }

    public TileEntityPiston(IBlockData p_i420_1_, EnumDirection p_i420_2_, boolean p_i420_3_, boolean p_i420_4_)
    {
        this.a = p_i420_1_;
        this.f = p_i420_2_;
        this.g = p_i420_3_;
        this.h = p_i420_4_;
    }

    public IBlockData b()
    {
        return this.a;
    }

    public int u()
    {
        return 0;
    }

    public boolean d()
    {
        return this.g;
    }

    public EnumDirection e()
    {
        return this.f;
    }

    public float a(float p_a_1_)
    {
        if (p_a_1_ > 1.0F)
        {
            p_a_1_ = 1.0F;
        }

        return this.j + (this.i - this.j) * p_a_1_;
    }

    private void a(float p_a_1_, float p_a_2_)
    {
        if (this.g)
        {
            p_a_1_ = 1.0F - p_a_1_;
        }
        else
        {
            --p_a_1_;
        }

        AxisAlignedBB axisalignedbb = Blocks.PISTON_EXTENSION.a(this.world, this.position, this.a, p_a_1_, this.f);

        if (axisalignedbb != null)
        {
            List list = this.world.getEntities((Entity)null, axisalignedbb);

            if (!list.isEmpty())
            {
                this.k.addAll(list);

                for (Entity entity : this.k)
                {
                    if (this.a.getBlock() == Blocks.SLIME && this.g)
                    {
                        switch (TileEntityPiston.SyntheticClass_1.a[this.f.k().ordinal()])
                        {
                            case 1:
                                entity.motX = (double)this.f.getAdjacentX();
                                break;

                            case 2:
                                entity.motY = (double)this.f.getAdjacentY();
                                break;

                            case 3:
                                entity.motZ = (double)this.f.getAdjacentZ();
                        }
                    }
                    else
                    {
                        entity.move((double)(p_a_2_ * (float)this.f.getAdjacentX()), (double)(p_a_2_ * (float)this.f.getAdjacentY()), (double)(p_a_2_ * (float)this.f.getAdjacentZ()));
                    }
                }

                this.k.clear();
            }
        }
    }

    public void h()
    {
        if (this.j < 1.0F && this.world != null)
        {
            this.j = this.i = 1.0F;
            this.world.t(this.position);
            this.y();

            if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION)
            {
                this.world.setTypeAndData(this.position, this.a, 3);
                this.world.d(this.position, this.a.getBlock());
            }
        }
    }

    public void c()
    {
        if (this.world != null)
        {
            this.j = this.i;

            if (this.j >= 1.0F)
            {
                this.a(1.0F, 0.25F);
                this.world.t(this.position);
                this.y();

                if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION)
                {
                    this.world.setTypeAndData(this.position, this.a, 3);
                    this.world.d(this.position, this.a.getBlock());
                }
            }
            else
            {
                this.i += 0.5F;

                if (this.i >= 1.0F)
                {
                    this.i = 1.0F;
                }

                if (this.g)
                {
                    this.a(this.i, this.i - this.j + 0.0625F);
                }
            }
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a = Block.getById(p_a_1_.getInt("blockId")).fromLegacyData(p_a_1_.getInt("blockData"));
        this.f = EnumDirection.fromType1(p_a_1_.getInt("facing"));
        this.j = this.i = p_a_1_.getFloat("progress");
        this.g = p_a_1_.getBoolean("extending");
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("blockId", Block.getId(this.a.getBlock()));
        p_b_1_.setInt("blockData", this.a.getBlock().toLegacyData(this.a));
        p_b_1_.setInt("facing", this.f.a());
        p_b_1_.setFloat("progress", this.j);
        p_b_1_.setBoolean("extending", this.g);
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.EnumAxis.values().length];

        static
        {
            try
            {
                a[EnumDirection.EnumAxis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[EnumDirection.EnumAxis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.EnumAxis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
