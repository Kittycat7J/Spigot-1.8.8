package net.minecraft.server.v1_8_R3;

import com.google.common.collect.ImmutableList;
import java.util.AbstractList;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockPiston extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
    public static final BlockStateBoolean EXTENDED = BlockStateBoolean.of("extended");
    private final boolean sticky;

    public BlockPiston(boolean p_i458_1_)
    {
        super(Material.PISTON);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(EXTENDED, Boolean.valueOf(false)));
        this.sticky = p_i458_1_;
        this.a(i);
        this.c(0.5F);
        this.a(CreativeModeTab.d);
    }

    public boolean c()
    {
        return false;
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_.set(FACING, a(p_postPlace_1_, p_postPlace_2_, p_postPlace_4_)), 2);

        if (!p_postPlace_1_.isClientSide)
        {
            this.e(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_);
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide)
        {
            this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        if (!p_onPlace_1_.isClientSide && p_onPlace_1_.getTileEntity(p_onPlace_2_) == null)
        {
            this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
        }
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_8_)).set(EXTENDED, Boolean.valueOf(false));
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        EnumDirection enumdirection = (EnumDirection)p_e_3_.get(FACING);
        boolean flag = this.a(p_e_1_, p_e_2_, enumdirection);

        if (flag && !((Boolean)p_e_3_.get(EXTENDED)).booleanValue())
        {
            if ((new PistonExtendsChecker(p_e_1_, p_e_2_, enumdirection, true)).a())
            {
                p_e_1_.playBlockAction(p_e_2_, this, 0, enumdirection.a());
            }
        }
        else if (!flag && ((Boolean)p_e_3_.get(EXTENDED)).booleanValue())
        {
            if (!this.sticky)
            {
                org.bukkit.block.Block block = p_e_1_.getWorld().getBlockAt(p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ());
                BlockPistonRetractEvent blockpistonretractevent = new BlockPistonRetractEvent(block, ImmutableList.of(), CraftBlock.notchToBlockFace(enumdirection));
                p_e_1_.getServer().getPluginManager().callEvent(blockpistonretractevent);

                if (blockpistonretractevent.isCancelled())
                {
                    return;
                }
            }

            p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(EXTENDED, Boolean.valueOf(false)), 2);
            p_e_1_.playBlockAction(p_e_2_, this, 1, enumdirection.a());
        }
    }

    private boolean a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_)
    {
        for (EnumDirection enumdirection : EnumDirection.values())
        {
            if (enumdirection != p_a_3_ && p_a_1_.isBlockFacePowered(p_a_2_.shift(enumdirection), enumdirection))
            {
                return true;
            }
        }

        if (p_a_1_.isBlockFacePowered(p_a_2_, EnumDirection.DOWN))
        {
            return true;
        }
        else
        {
            BlockPosition blockposition = p_a_2_.up();

            for (EnumDirection enumdirection1 : EnumDirection.values())
            {
                if (enumdirection1 != EnumDirection.DOWN && p_a_1_.isBlockFacePowered(blockposition.shift(enumdirection1), enumdirection1))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_, int p_a_5_)
    {
        EnumDirection enumdirection = (EnumDirection)p_a_3_.get(FACING);

        if (!p_a_1_.isClientSide)
        {
            boolean flag = this.a(p_a_1_, p_a_2_, enumdirection);

            if (flag && p_a_4_ == 1)
            {
                p_a_1_.setTypeAndData(p_a_2_, p_a_3_.set(EXTENDED, Boolean.valueOf(true)), 2);
                return false;
            }

            if (!flag && p_a_4_ == 0)
            {
                return false;
            }
        }

        if (p_a_4_ == 0)
        {
            if (!this.a(p_a_1_, p_a_2_, enumdirection, true))
            {
                return false;
            }

            p_a_1_.setTypeAndData(p_a_2_, p_a_3_.set(EXTENDED, Boolean.valueOf(true)), 2);
            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.5D, (double)p_a_2_.getZ() + 0.5D, "tile.piston.out", 0.5F, p_a_1_.random.nextFloat() * 0.25F + 0.6F);
        }
        else if (p_a_4_ == 1)
        {
            TileEntity tileentity1 = p_a_1_.getTileEntity(p_a_2_.shift(enumdirection));

            if (tileentity1 instanceof TileEntityPiston)
            {
                ((TileEntityPiston)tileentity1).h();
            }

            p_a_1_.setTypeAndData(p_a_2_, Blocks.PISTON_EXTENSION.getBlockData().set(BlockPistonMoving.FACING, enumdirection).set(BlockPistonMoving.TYPE, this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
            p_a_1_.setTileEntity(p_a_2_, BlockPistonMoving.a(this.fromLegacyData(p_a_5_), enumdirection, false, true));

            if (this.sticky)
            {
                BlockPosition blockposition = p_a_2_.a(enumdirection.getAdjacentX() * 2, enumdirection.getAdjacentY() * 2, enumdirection.getAdjacentZ() * 2);
                Block block = p_a_1_.getType(blockposition).getBlock();
                boolean flag1 = false;

                if (block == Blocks.PISTON_EXTENSION)
                {
                    TileEntity tileentity = p_a_1_.getTileEntity(blockposition);

                    if (tileentity instanceof TileEntityPiston)
                    {
                        TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;

                        if (tileentitypiston.e() == enumdirection && tileentitypiston.d())
                        {
                            tileentitypiston.h();
                            flag1 = true;
                        }
                    }
                }

                if (!flag1 && a(block, p_a_1_, blockposition, enumdirection.opposite(), false) && (block.k() == 0 || block == Blocks.PISTON || block == Blocks.STICKY_PISTON))
                {
                    this.a(p_a_1_, p_a_2_, enumdirection, false);
                }
            }
            else
            {
                p_a_1_.setAir(p_a_2_.shift(enumdirection));
            }

            p_a_1_.makeSound((double)p_a_2_.getX() + 0.5D, (double)p_a_2_.getY() + 0.5D, (double)p_a_2_.getZ() + 0.5D, "tile.piston.in", 0.5F, p_a_1_.random.nextFloat() * 0.15F + 0.6F);
        }

        return true;
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);

        if (iblockdata.getBlock() == this && ((Boolean)iblockdata.get(EXTENDED)).booleanValue())
        {
            EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);

            if (enumdirection != null)
            {
                switch (BlockPiston.SyntheticClass_1.a[enumdirection.ordinal()])
                {
                    case 1:
                        this.a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 2:
                        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                        break;

                    case 3:
                        this.a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 4:
                        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                        break;

                    case 5:
                        this.a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 6:
                        this.a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                }
            }
        }
        else
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void j()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        this.updateShape(p_a_1_, p_a_2_);
        return super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public boolean d()
    {
        return false;
    }

    public static EnumDirection b(int p_b_0_)
    {
        int i = p_b_0_ & 7;
        return i > 5 ? null : EnumDirection.fromType1(i);
    }

    public static EnumDirection a(World p_a_0_, BlockPosition p_a_1_, EntityLiving p_a_2_)
    {
        if (MathHelper.e((float)p_a_2_.locX - (float)p_a_1_.getX()) < 2.0F && MathHelper.e((float)p_a_2_.locZ - (float)p_a_1_.getZ()) < 2.0F)
        {
            double d0 = p_a_2_.locY + (double)p_a_2_.getHeadHeight();

            if (d0 - (double)p_a_1_.getY() > 2.0D)
            {
                return EnumDirection.UP;
            }

            if ((double)p_a_1_.getY() - d0 > 0.0D)
            {
                return EnumDirection.DOWN;
            }
        }

        return p_a_2_.getDirection().opposite();
    }

    public static boolean a(Block p_a_0_, World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_, boolean p_a_4_)
    {
        if (p_a_0_ == Blocks.OBSIDIAN)
        {
            return false;
        }
        else if (!p_a_1_.getWorldBorder().a(p_a_2_))
        {
            return false;
        }
        else if (p_a_2_.getY() >= 0 && (p_a_3_ != EnumDirection.DOWN || p_a_2_.getY() != 0))
        {
            if (p_a_2_.getY() <= p_a_1_.getHeight() - 1 && (p_a_3_ != EnumDirection.UP || p_a_2_.getY() != p_a_1_.getHeight() - 1))
            {
                if (p_a_0_ != Blocks.PISTON && p_a_0_ != Blocks.STICKY_PISTON)
                {
                    if (p_a_0_.g(p_a_1_, p_a_2_) == -1.0F)
                    {
                        return false;
                    }

                    if (p_a_0_.k() == 2)
                    {
                        return false;
                    }

                    if (p_a_0_.k() == 1)
                    {
                        if (!p_a_4_)
                        {
                            return false;
                        }

                        return true;
                    }
                }
                else if (((Boolean)p_a_1_.getType(p_a_2_).get(EXTENDED)).booleanValue())
                {
                    return false;
                }

                return !(p_a_0_ instanceof IContainer);
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_, boolean p_a_4_)
    {
        if (!p_a_4_)
        {
            p_a_1_.setAir(p_a_2_.shift(p_a_3_));
        }

        PistonExtendsChecker pistonextendschecker = new PistonExtendsChecker(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
        List list = pistonextendschecker.getMovedBlocks();
        List list1 = pistonextendschecker.getBrokenBlocks();

        if (!pistonextendschecker.a())
        {
            return false;
        }
        else
        {
            final org.bukkit.block.Block block = p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
            final List<BlockPosition> list2 = pistonextendschecker.getMovedBlocks();
            final List<BlockPosition> list3 = pistonextendschecker.getBrokenBlocks();
            List<org.bukkit.block.Block> list4 = new AbstractList<org.bukkit.block.Block>()
            {
                public int size()
                {
                    return list2.size() + list3.size();
                }
                public org.bukkit.block.Block get(int p_get_1_)
                {
                    if (p_get_1_ < this.size() && p_get_1_ >= 0)
                    {
                        BlockPosition blockposition5 = p_get_1_ < list2.size() ? (BlockPosition)list2.get(p_get_1_) : (BlockPosition)list3.get(p_get_1_ - list2.size());
                        return block.getWorld().getBlockAt(blockposition5.getX(), blockposition5.getY(), blockposition5.getZ());
                    }
                    else
                    {
                        throw new ArrayIndexOutOfBoundsException(p_get_1_);
                    }
                }
            };
            int i = list.size() + list1.size();
            Block[] ablock = new Block[i];
            EnumDirection enumdirection = p_a_4_ ? p_a_3_ : p_a_3_.opposite();
            BlockPistonEvent blockpistonevent;

            if (p_a_4_)
            {
                blockpistonevent = new BlockPistonExtendEvent(block, list4, CraftBlock.notchToBlockFace(enumdirection));
            }
            else
            {
                blockpistonevent = new BlockPistonRetractEvent(block, list4, CraftBlock.notchToBlockFace(enumdirection));
            }

            p_a_1_.getServer().getPluginManager().callEvent(blockpistonevent);

            if (blockpistonevent.isCancelled())
            {
                for (BlockPosition blockposition2 : list3)
                {
                    p_a_1_.notify(blockposition2);
                }

                for (BlockPosition blockposition3 : list2)
                {
                    p_a_1_.notify(blockposition3);
                    p_a_1_.notify(blockposition3.shift(enumdirection));
                }

                return false;
            }
            else
            {
                for (int j = list1.size() - 1; j >= 0; --j)
                {
                    BlockPosition blockposition = (BlockPosition)list1.get(j);
                    Block block1 = p_a_1_.getType(blockposition).getBlock();
                    block1.b(p_a_1_, blockposition, p_a_1_.getType(blockposition), 0);
                    p_a_1_.setAir(blockposition);
                    --i;
                    ablock[i] = block1;
                }

                for (int b = list.size() - 1; b >= 0; --b)
                {
                    BlockPosition blockposition1 = (BlockPosition)list.get(b);
                    IBlockData iblockdata1 = p_a_1_.getType(blockposition1);
                    Block block2 = iblockdata1.getBlock();
                    block2.toLegacyData(iblockdata1);
                    p_a_1_.setAir(blockposition1);
                    blockposition1 = blockposition1.shift(enumdirection);
                    p_a_1_.setTypeAndData(blockposition1, Blocks.PISTON_EXTENSION.getBlockData().set(FACING, p_a_3_), 4);
                    p_a_1_.setTileEntity(blockposition1, BlockPistonMoving.a(iblockdata1, p_a_3_, p_a_4_, false));
                    --i;
                    ablock[i] = block2;
                }

                BlockPosition blockposition4 = p_a_2_.shift(p_a_3_);

                if (p_a_4_)
                {
                    BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
                    IBlockData iblockdata2 = Blocks.PISTON_HEAD.getBlockData().set(BlockPistonExtension.FACING, p_a_3_).set(BlockPistonExtension.TYPE, blockpistonextension$enumpistontype);
                    IBlockData iblockdata = Blocks.PISTON_EXTENSION.getBlockData().set(BlockPistonMoving.FACING, p_a_3_).set(BlockPistonMoving.TYPE, this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
                    p_a_1_.setTypeAndData(blockposition4, iblockdata, 4);
                    p_a_1_.setTileEntity(blockposition4, BlockPistonMoving.a(iblockdata2, p_a_3_, true, false));
                }

                for (int l = list1.size() - 1; l >= 0; --l)
                {
                    p_a_1_.applyPhysics((BlockPosition)list1.get(l), ablock[i++]);
                }

                for (int i1 = list.size() - 1; i1 >= 0; --i1)
                {
                    p_a_1_.applyPhysics((BlockPosition)list.get(i1), ablock[i++]);
                }

                if (p_a_4_)
                {
                    p_a_1_.applyPhysics(blockposition4, Blocks.PISTON_HEAD);
                    p_a_1_.applyPhysics(p_a_2_, this);
                }

                return true;
            }
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, b(p_fromLegacyData_1_)).set(EXTENDED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (((Boolean)p_toLegacyData_1_.get(EXTENDED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, EXTENDED});
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.values().length];

        static
        {
            try
            {
                a[EnumDirection.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                a[EnumDirection.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                a[EnumDirection.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[EnumDirection.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[EnumDirection.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
