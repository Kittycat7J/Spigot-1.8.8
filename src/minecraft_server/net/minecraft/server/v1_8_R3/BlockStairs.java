package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockStairs extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
    public static final BlockStateEnum<BlockStairs.EnumHalf> HALF = BlockStateEnum.<BlockStairs.EnumHalf>of("half", BlockStairs.EnumHalf.class);
    public static final BlockStateEnum<BlockStairs.EnumStairShape> SHAPE = BlockStateEnum.<BlockStairs.EnumStairShape>of("shape", BlockStairs.EnumStairShape.class);
    private static final int[][] O = new int[][] {{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
    private final Block P;
    private final IBlockData Q;
    private boolean R;
    private int S;

    protected BlockStairs(IBlockData p_i646_1_)
    {
        super(p_i646_1_.getBlock().material);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(HALF, BlockStairs.EnumHalf.BOTTOM).set(SHAPE, BlockStairs.EnumStairShape.STRAIGHT));
        this.P = p_i646_1_.getBlock();
        this.Q = p_i646_1_;
        this.c(this.P.strength);
        this.b(this.P.durability / 3.0F);
        this.a((Block.StepSound)this.P.stepSound);
        this.e(255);
        this.a((CreativeModeTab)CreativeModeTab.b);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        if (this.R)
        {
            this.a(0.5F * (float)(this.S % 2), 0.5F * (float)(this.S / 4 % 2), 0.5F * (float)(this.S / 2 % 2), 0.5F + 0.5F * (float)(this.S % 2), 0.5F + 0.5F * (float)(this.S / 4 % 2), 0.5F + 0.5F * (float)(this.S / 2 % 2));
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public void e(IBlockAccess p_e_1_, BlockPosition p_e_2_)
    {
        if (p_e_1_.getType(p_e_2_).get(HALF) == BlockStairs.EnumHalf.TOP)
        {
            this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public static boolean c(Block p_c_0_)
    {
        return p_c_0_ instanceof BlockStairs;
    }

    public static boolean a(IBlockAccess p_a_0_, BlockPosition p_a_1_, IBlockData p_a_2_)
    {
        IBlockData iblockdata = p_a_0_.getType(p_a_1_);
        Block block = iblockdata.getBlock();
        return c(block) && iblockdata.get(HALF) == p_a_2_.get(HALF) && iblockdata.get(FACING) == p_a_2_.get(FACING);
    }

    public int f(IBlockAccess p_f_1_, BlockPosition p_f_2_)
    {
        IBlockData iblockdata = p_f_1_.getType(p_f_2_);
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockStairs.EnumHalf blockstairs$enumhalf = (BlockStairs.EnumHalf)iblockdata.get(HALF);
        boolean flag = blockstairs$enumhalf == BlockStairs.EnumHalf.TOP;

        if (enumdirection == EnumDirection.EAST)
        {
            IBlockData iblockdata1 = p_f_1_.getType(p_f_2_.east());
            Block block = iblockdata1.getBlock();

            if (c(block) && blockstairs$enumhalf == iblockdata1.get(HALF))
            {
                EnumDirection enumdirection1 = (EnumDirection)iblockdata1.get(FACING);

                if (enumdirection1 == EnumDirection.NORTH && !a(p_f_1_, p_f_2_.south(), iblockdata))
                {
                    return flag ? 1 : 2;
                }

                if (enumdirection1 == EnumDirection.SOUTH && !a(p_f_1_, p_f_2_.north(), iblockdata))
                {
                    return flag ? 2 : 1;
                }
            }
        }
        else if (enumdirection == EnumDirection.WEST)
        {
            IBlockData iblockdata2 = p_f_1_.getType(p_f_2_.west());
            Block block1 = iblockdata2.getBlock();

            if (c(block1) && blockstairs$enumhalf == iblockdata2.get(HALF))
            {
                EnumDirection enumdirection2 = (EnumDirection)iblockdata2.get(FACING);

                if (enumdirection2 == EnumDirection.NORTH && !a(p_f_1_, p_f_2_.south(), iblockdata))
                {
                    return flag ? 2 : 1;
                }

                if (enumdirection2 == EnumDirection.SOUTH && !a(p_f_1_, p_f_2_.north(), iblockdata))
                {
                    return flag ? 1 : 2;
                }
            }
        }
        else if (enumdirection == EnumDirection.SOUTH)
        {
            IBlockData iblockdata3 = p_f_1_.getType(p_f_2_.south());
            Block block2 = iblockdata3.getBlock();

            if (c(block2) && blockstairs$enumhalf == iblockdata3.get(HALF))
            {
                EnumDirection enumdirection3 = (EnumDirection)iblockdata3.get(FACING);

                if (enumdirection3 == EnumDirection.WEST && !a(p_f_1_, p_f_2_.east(), iblockdata))
                {
                    return flag ? 2 : 1;
                }

                if (enumdirection3 == EnumDirection.EAST && !a(p_f_1_, p_f_2_.west(), iblockdata))
                {
                    return flag ? 1 : 2;
                }
            }
        }
        else if (enumdirection == EnumDirection.NORTH)
        {
            IBlockData iblockdata4 = p_f_1_.getType(p_f_2_.north());
            Block block3 = iblockdata4.getBlock();

            if (c(block3) && blockstairs$enumhalf == iblockdata4.get(HALF))
            {
                EnumDirection enumdirection4 = (EnumDirection)iblockdata4.get(FACING);

                if (enumdirection4 == EnumDirection.WEST && !a(p_f_1_, p_f_2_.east(), iblockdata))
                {
                    return flag ? 1 : 2;
                }

                if (enumdirection4 == EnumDirection.EAST && !a(p_f_1_, p_f_2_.west(), iblockdata))
                {
                    return flag ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public int g(IBlockAccess p_g_1_, BlockPosition p_g_2_)
    {
        IBlockData iblockdata = p_g_1_.getType(p_g_2_);
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockStairs.EnumHalf blockstairs$enumhalf = (BlockStairs.EnumHalf)iblockdata.get(HALF);
        boolean flag = blockstairs$enumhalf == BlockStairs.EnumHalf.TOP;

        if (enumdirection == EnumDirection.EAST)
        {
            IBlockData iblockdata1 = p_g_1_.getType(p_g_2_.west());
            Block block = iblockdata1.getBlock();

            if (c(block) && blockstairs$enumhalf == iblockdata1.get(HALF))
            {
                EnumDirection enumdirection1 = (EnumDirection)iblockdata1.get(FACING);

                if (enumdirection1 == EnumDirection.NORTH && !a(p_g_1_, p_g_2_.north(), iblockdata))
                {
                    return flag ? 1 : 2;
                }

                if (enumdirection1 == EnumDirection.SOUTH && !a(p_g_1_, p_g_2_.south(), iblockdata))
                {
                    return flag ? 2 : 1;
                }
            }
        }
        else if (enumdirection == EnumDirection.WEST)
        {
            IBlockData iblockdata2 = p_g_1_.getType(p_g_2_.east());
            Block block1 = iblockdata2.getBlock();

            if (c(block1) && blockstairs$enumhalf == iblockdata2.get(HALF))
            {
                EnumDirection enumdirection2 = (EnumDirection)iblockdata2.get(FACING);

                if (enumdirection2 == EnumDirection.NORTH && !a(p_g_1_, p_g_2_.north(), iblockdata))
                {
                    return flag ? 2 : 1;
                }

                if (enumdirection2 == EnumDirection.SOUTH && !a(p_g_1_, p_g_2_.south(), iblockdata))
                {
                    return flag ? 1 : 2;
                }
            }
        }
        else if (enumdirection == EnumDirection.SOUTH)
        {
            IBlockData iblockdata3 = p_g_1_.getType(p_g_2_.north());
            Block block2 = iblockdata3.getBlock();

            if (c(block2) && blockstairs$enumhalf == iblockdata3.get(HALF))
            {
                EnumDirection enumdirection3 = (EnumDirection)iblockdata3.get(FACING);

                if (enumdirection3 == EnumDirection.WEST && !a(p_g_1_, p_g_2_.west(), iblockdata))
                {
                    return flag ? 2 : 1;
                }

                if (enumdirection3 == EnumDirection.EAST && !a(p_g_1_, p_g_2_.east(), iblockdata))
                {
                    return flag ? 1 : 2;
                }
            }
        }
        else if (enumdirection == EnumDirection.NORTH)
        {
            IBlockData iblockdata4 = p_g_1_.getType(p_g_2_.south());
            Block block3 = iblockdata4.getBlock();

            if (c(block3) && blockstairs$enumhalf == iblockdata4.get(HALF))
            {
                EnumDirection enumdirection4 = (EnumDirection)iblockdata4.get(FACING);

                if (enumdirection4 == EnumDirection.WEST && !a(p_g_1_, p_g_2_.west(), iblockdata))
                {
                    return flag ? 1 : 2;
                }

                if (enumdirection4 == EnumDirection.EAST && !a(p_g_1_, p_g_2_.east(), iblockdata))
                {
                    return flag ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public boolean h(IBlockAccess p_h_1_, BlockPosition p_h_2_)
    {
        IBlockData iblockdata = p_h_1_.getType(p_h_2_);
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockStairs.EnumHalf blockstairs$enumhalf = (BlockStairs.EnumHalf)iblockdata.get(HALF);
        boolean flag = blockstairs$enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag)
        {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.5F;
        boolean flag1 = true;

        if (enumdirection == EnumDirection.EAST)
        {
            f2 = 0.5F;
            f5 = 1.0F;
            IBlockData iblockdata1 = p_h_1_.getType(p_h_2_.east());
            Block block = iblockdata1.getBlock();

            if (c(block) && blockstairs$enumhalf == iblockdata1.get(HALF))
            {
                EnumDirection enumdirection1 = (EnumDirection)iblockdata1.get(FACING);

                if (enumdirection1 == EnumDirection.NORTH && !a(p_h_1_, p_h_2_.south(), iblockdata))
                {
                    f5 = 0.5F;
                    flag1 = false;
                }
                else if (enumdirection1 == EnumDirection.SOUTH && !a(p_h_1_, p_h_2_.north(), iblockdata))
                {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        }
        else if (enumdirection == EnumDirection.WEST)
        {
            f3 = 0.5F;
            f5 = 1.0F;
            IBlockData iblockdata2 = p_h_1_.getType(p_h_2_.west());
            Block block1 = iblockdata2.getBlock();

            if (c(block1) && blockstairs$enumhalf == iblockdata2.get(HALF))
            {
                EnumDirection enumdirection2 = (EnumDirection)iblockdata2.get(FACING);

                if (enumdirection2 == EnumDirection.NORTH && !a(p_h_1_, p_h_2_.south(), iblockdata))
                {
                    f5 = 0.5F;
                    flag1 = false;
                }
                else if (enumdirection2 == EnumDirection.SOUTH && !a(p_h_1_, p_h_2_.north(), iblockdata))
                {
                    f4 = 0.5F;
                    flag1 = false;
                }
            }
        }
        else if (enumdirection == EnumDirection.SOUTH)
        {
            f4 = 0.5F;
            f5 = 1.0F;
            IBlockData iblockdata3 = p_h_1_.getType(p_h_2_.south());
            Block block2 = iblockdata3.getBlock();

            if (c(block2) && blockstairs$enumhalf == iblockdata3.get(HALF))
            {
                EnumDirection enumdirection3 = (EnumDirection)iblockdata3.get(FACING);

                if (enumdirection3 == EnumDirection.WEST && !a(p_h_1_, p_h_2_.east(), iblockdata))
                {
                    f3 = 0.5F;
                    flag1 = false;
                }
                else if (enumdirection3 == EnumDirection.EAST && !a(p_h_1_, p_h_2_.west(), iblockdata))
                {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        }
        else if (enumdirection == EnumDirection.NORTH)
        {
            IBlockData iblockdata4 = p_h_1_.getType(p_h_2_.north());
            Block block3 = iblockdata4.getBlock();

            if (c(block3) && blockstairs$enumhalf == iblockdata4.get(HALF))
            {
                EnumDirection enumdirection4 = (EnumDirection)iblockdata4.get(FACING);

                if (enumdirection4 == EnumDirection.WEST && !a(p_h_1_, p_h_2_.east(), iblockdata))
                {
                    f3 = 0.5F;
                    flag1 = false;
                }
                else if (enumdirection4 == EnumDirection.EAST && !a(p_h_1_, p_h_2_.west(), iblockdata))
                {
                    f2 = 0.5F;
                    flag1 = false;
                }
            }
        }

        this.a(f2, f, f4, f3, f1, f5);
        return flag1;
    }

    public boolean i(IBlockAccess p_i_1_, BlockPosition p_i_2_)
    {
        IBlockData iblockdata = p_i_1_.getType(p_i_2_);
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        BlockStairs.EnumHalf blockstairs$enumhalf = (BlockStairs.EnumHalf)iblockdata.get(HALF);
        boolean flag = blockstairs$enumhalf == BlockStairs.EnumHalf.TOP;
        float f = 0.5F;
        float f1 = 1.0F;

        if (flag)
        {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = 0.5F;
        float f5 = 1.0F;
        boolean flag1 = false;

        if (enumdirection == EnumDirection.EAST)
        {
            IBlockData iblockdata1 = p_i_1_.getType(p_i_2_.west());
            Block block = iblockdata1.getBlock();

            if (c(block) && blockstairs$enumhalf == iblockdata1.get(HALF))
            {
                EnumDirection enumdirection1 = (EnumDirection)iblockdata1.get(FACING);

                if (enumdirection1 == EnumDirection.NORTH && !a(p_i_1_, p_i_2_.north(), iblockdata))
                {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                }
                else if (enumdirection1 == EnumDirection.SOUTH && !a(p_i_1_, p_i_2_.south(), iblockdata))
                {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        }
        else if (enumdirection == EnumDirection.WEST)
        {
            IBlockData iblockdata2 = p_i_1_.getType(p_i_2_.east());
            Block block1 = iblockdata2.getBlock();

            if (c(block1) && blockstairs$enumhalf == iblockdata2.get(HALF))
            {
                f2 = 0.5F;
                f3 = 1.0F;
                EnumDirection enumdirection2 = (EnumDirection)iblockdata2.get(FACING);

                if (enumdirection2 == EnumDirection.NORTH && !a(p_i_1_, p_i_2_.north(), iblockdata))
                {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    flag1 = true;
                }
                else if (enumdirection2 == EnumDirection.SOUTH && !a(p_i_1_, p_i_2_.south(), iblockdata))
                {
                    f4 = 0.5F;
                    f5 = 1.0F;
                    flag1 = true;
                }
            }
        }
        else if (enumdirection == EnumDirection.SOUTH)
        {
            IBlockData iblockdata3 = p_i_1_.getType(p_i_2_.north());
            Block block2 = iblockdata3.getBlock();

            if (c(block2) && blockstairs$enumhalf == iblockdata3.get(HALF))
            {
                f4 = 0.0F;
                f5 = 0.5F;
                EnumDirection enumdirection3 = (EnumDirection)iblockdata3.get(FACING);

                if (enumdirection3 == EnumDirection.WEST && !a(p_i_1_, p_i_2_.west(), iblockdata))
                {
                    flag1 = true;
                }
                else if (enumdirection3 == EnumDirection.EAST && !a(p_i_1_, p_i_2_.east(), iblockdata))
                {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        }
        else if (enumdirection == EnumDirection.NORTH)
        {
            IBlockData iblockdata4 = p_i_1_.getType(p_i_2_.south());
            Block block3 = iblockdata4.getBlock();

            if (c(block3) && blockstairs$enumhalf == iblockdata4.get(HALF))
            {
                EnumDirection enumdirection4 = (EnumDirection)iblockdata4.get(FACING);

                if (enumdirection4 == EnumDirection.WEST && !a(p_i_1_, p_i_2_.west(), iblockdata))
                {
                    flag1 = true;
                }
                else if (enumdirection4 == EnumDirection.EAST && !a(p_i_1_, p_i_2_.east(), iblockdata))
                {
                    f2 = 0.5F;
                    f3 = 1.0F;
                    flag1 = true;
                }
            }
        }

        if (flag1)
        {
            this.a(f2, f, f4, f3, f1, f5);
        }

        return flag1;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
        this.e(p_a_1_, p_a_2_);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        boolean flag = this.h(p_a_1_, p_a_2_);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);

        if (flag && this.i(p_a_1_, p_a_2_))
        {
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        }

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void attack(World p_attack_1_, BlockPosition p_attack_2_, EntityHuman p_attack_3_)
    {
        this.P.attack(p_attack_1_, p_attack_2_, p_attack_3_);
    }

    public void postBreak(World p_postBreak_1_, BlockPosition p_postBreak_2_, IBlockData p_postBreak_3_)
    {
        this.P.postBreak(p_postBreak_1_, p_postBreak_2_, p_postBreak_3_);
    }

    public float a(Entity p_a_1_)
    {
        return this.P.a(p_a_1_);
    }

    public int a(World p_a_1_)
    {
        return this.P.a(p_a_1_);
    }

    public Vec3D a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_, Vec3D p_a_4_)
    {
        return this.P.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
    }

    public boolean A()
    {
        return this.P.A();
    }

    public boolean a(IBlockData p_a_1_, boolean p_a_2_)
    {
        return this.P.a(p_a_1_, p_a_2_);
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return this.P.canPlace(p_canPlace_1_, p_canPlace_2_);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.doPhysics(p_onPlace_1_, p_onPlace_2_, this.Q, Blocks.AIR);
        this.P.onPlace(p_onPlace_1_, p_onPlace_2_, this.Q);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        this.P.remove(p_remove_1_, p_remove_2_, this.Q);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, Entity p_a_3_)
    {
        this.P.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        this.P.b(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        return this.P.interact(p_interact_1_, p_interact_2_, this.Q, p_interact_4_, EnumDirection.DOWN, 0.0F, 0.0F, 0.0F);
    }

    public void wasExploded(World p_wasExploded_1_, BlockPosition p_wasExploded_2_, Explosion p_wasExploded_3_)
    {
        this.P.wasExploded(p_wasExploded_1_, p_wasExploded_2_, p_wasExploded_3_);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return this.P.g(this.Q);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        IBlockData iblockdata = super.getPlacedState(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_, p_getPlacedState_4_, p_getPlacedState_5_, p_getPlacedState_6_, p_getPlacedState_7_, p_getPlacedState_8_);
        iblockdata = iblockdata.set(FACING, p_getPlacedState_8_.getDirection()).set(SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
        return p_getPlacedState_3_ != EnumDirection.DOWN && (p_getPlacedState_3_ == EnumDirection.UP || (double)p_getPlacedState_5_ <= 0.5D) ? iblockdata.set(HALF, BlockStairs.EnumHalf.BOTTOM) : iblockdata.set(HALF, BlockStairs.EnumHalf.TOP);
    }

    public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_)
    {
        MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
        IBlockData iblockdata = p_a_1_.getType(p_a_2_);
        int i = ((EnumDirection)iblockdata.get(FACING)).b();
        boolean flag = iblockdata.get(HALF) == BlockStairs.EnumHalf.TOP;
        int[] aint = O[i + (flag ? 4 : 0)];
        this.R = true;

        for (int j = 0; j < 8; ++j)
        {
            this.S = j;

            if (Arrays.binarySearch(aint, j) < 0)
            {
                amovingobjectposition[j] = super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
            }
        }

        for (int k : aint)
        {
            amovingobjectposition[k] = null;
        }

        MovingObjectPosition movingobjectposition1 = null;
        double d0 = 0.0D;

        for (MovingObjectPosition movingobjectposition : amovingobjectposition)
        {
            if (movingobjectposition != null)
            {
                double d1 = movingobjectposition.pos.distanceSquared(p_a_4_);

                if (d1 > d0)
                {
                    movingobjectposition1 = movingobjectposition;
                    d0 = d1;
                }
            }
        }

        return movingobjectposition1;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        IBlockData iblockdata = this.getBlockData().set(HALF, (p_fromLegacyData_1_ & 4) > 0 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);
        iblockdata = iblockdata.set(FACING, EnumDirection.fromType1(5 - (p_fromLegacyData_1_ & 3)));
        return iblockdata;
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        if (p_toLegacyData_1_.get(HALF) == BlockStairs.EnumHalf.TOP)
        {
            i |= 4;
        }

        i = i | 5 - ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();
        return i;
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        if (this.h(p_updateState_2_, p_updateState_3_))
        {
            switch (this.g(p_updateState_2_, p_updateState_3_))
            {
                case 0:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
                    break;

                case 1:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.INNER_RIGHT);
                    break;

                case 2:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.INNER_LEFT);
            }
        }
        else
        {
            switch (this.f(p_updateState_2_, p_updateState_3_))
            {
                case 0:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.STRAIGHT);
                    break;

                case 1:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.OUTER_RIGHT);
                    break;

                case 2:
                    p_updateState_1_ = p_updateState_1_.set(SHAPE, BlockStairs.EnumStairShape.OUTER_LEFT);
            }
        }

        return p_updateState_1_;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, HALF, SHAPE});
    }

    public static enum EnumHalf implements INamable
    {
        TOP("top"),
        BOTTOM("bottom");

        private final String c;

        private EnumHalf(String p_i644_3_)
        {
            this.c = p_i644_3_;
        }

        public String toString()
        {
            return this.c;
        }

        public String getName()
        {
            return this.c;
        }
    }

    public static enum EnumStairShape implements INamable
    {
        STRAIGHT("straight"),
        INNER_LEFT("inner_left"),
        INNER_RIGHT("inner_right"),
        OUTER_LEFT("outer_left"),
        OUTER_RIGHT("outer_right");

        private final String f;

        private EnumStairShape(String p_i645_3_)
        {
            this.f = p_i645_3_;
        }

        public String toString()
        {
            return this.f;
        }

        public String getName()
        {
            return this.f;
        }
    }
}
