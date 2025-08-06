package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockSkull extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
    public static final BlockStateBoolean NODROP = BlockStateBoolean.of("nodrop");
    private static final Predicate<ShapeDetectorBlock> N = new Predicate()
    {
        public boolean a(ShapeDetectorBlock p_a_1_)
        {
            return p_a_1_.a() != null && p_a_1_.a().getBlock() == Blocks.SKULL && p_a_1_.b() instanceof TileEntitySkull && ((TileEntitySkull)p_a_1_.b()).getSkullType() == 1;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((ShapeDetectorBlock)p_apply_1_);
        }
    };
    private ShapeDetector O;
    private ShapeDetector P;

    protected BlockSkull()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(NODROP, Boolean.valueOf(false)));
        this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    public String getName()
    {
        return LocaleI18n.get("tile.skull.skeleton.name");
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        switch (BlockSkull.SyntheticClass_1.a[((EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING)).ordinal()])
        {
            case 1:
            default:
                this.a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                break;

            case 2:
                this.a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                break;

            case 3:
                this.a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                break;

            case 4:
                this.a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                break;

            case 5:
                this.a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        this.updateShape(p_a_1_, p_a_2_);
        return super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection()).set(NODROP, Boolean.valueOf(false));
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntitySkull();
    }

    public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_)
    {
        TileEntity tileentity = p_getDropData_1_.getTileEntity(p_getDropData_2_);
        return tileentity instanceof TileEntitySkull ? ((TileEntitySkull)tileentity).getSkullType() : super.getDropData(p_getDropData_1_, p_getDropData_2_);
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        if (p_dropNaturally_1_.random.nextFloat() < p_dropNaturally_4_)
        {
            ItemStack itemstack = new ItemStack(Items.SKULL, 1, this.getDropData(p_dropNaturally_1_, p_dropNaturally_2_));
            TileEntitySkull tileentityskull = (TileEntitySkull)p_dropNaturally_1_.getTileEntity(p_dropNaturally_2_);

            if (tileentityskull.getSkullType() == 3 && tileentityskull.getGameProfile() != null)
            {
                itemstack.setTag(new NBTTagCompound());
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
                itemstack.getTag().set("SkullOwner", nbttagcompound);
            }

            a(p_dropNaturally_1_, p_dropNaturally_2_, (ItemStack)itemstack);
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_)
    {
        if (p_a_4_.abilities.canInstantlyBuild)
        {
            p_a_3_ = p_a_3_.set(NODROP, Boolean.valueOf(true));
            p_a_1_.setTypeAndData(p_a_2_, p_a_3_, 4);
        }

        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        if (!p_remove_1_.isClientSide)
        {
            super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.SKULL;
    }

    public boolean b(World p_b_1_, BlockPosition p_b_2_, ItemStack p_b_3_)
    {
        return p_b_3_.getData() == 1 && p_b_2_.getY() >= 2 && p_b_1_.getDifficulty() != EnumDifficulty.PEACEFUL && !p_b_1_.isClientSide ? this.l().a(p_b_1_, p_b_2_) != null : false;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, TileEntitySkull p_a_3_)
    {
        if (!p_a_1_.captureBlockStates)
        {
            if (p_a_3_.getSkullType() == 1 && p_a_2_.getY() >= 2 && p_a_1_.getDifficulty() != EnumDifficulty.PEACEFUL && !p_a_1_.isClientSide)
            {
                ShapeDetector shapedetector = this.n();
                ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = shapedetector.a(p_a_1_, p_a_2_);

                if (shapedetector$shapedetectorcollection != null)
                {
                    BlockStateListPopulator blockstatelistpopulator = new BlockStateListPopulator(p_a_1_.getWorld());

                    for (int i = 0; i < 3; ++i)
                    {
                        ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(i, 0, 0);
                        BlockPosition blockposition = shapedetectorblock.getPosition();
                        IBlockData iblockdata = shapedetectorblock.a().set(NODROP, Boolean.valueOf(true));
                        blockstatelistpopulator.setTypeAndData(blockposition.getX(), blockposition.getY(), blockposition.getZ(), iblockdata.getBlock(), iblockdata.getBlock().toLegacyData(iblockdata), 2);
                    }

                    for (int k = 0; k < shapedetector.c(); ++k)
                    {
                        for (int l = 0; l < shapedetector.b(); ++l)
                        {
                            ShapeDetectorBlock shapedetectorblock2 = shapedetector$shapedetectorcollection.a(k, l, 0);
                            BlockPosition blockposition2 = shapedetectorblock2.getPosition();
                            blockstatelistpopulator.setTypeAndData(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ(), Blocks.AIR, 0, 2);
                        }
                    }

                    BlockPosition blockposition1 = shapedetector$shapedetectorcollection.a(1, 0, 0).getPosition();
                    EntityWither entitywither = new EntityWither(p_a_1_);
                    BlockPosition blockposition3 = shapedetector$shapedetectorcollection.a(1, 2, 0).getPosition();
                    entitywither.setPositionRotation((double)blockposition3.getX() + 0.5D, (double)blockposition3.getY() + 0.55D, (double)blockposition3.getZ() + 0.5D, shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F, 0.0F);
                    entitywither.aI = shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F;
                    entitywither.n();
                    Iterator iterator = p_a_1_.a(EntityHuman.class, entitywither.getBoundingBox().grow(50.0D, 50.0D, 50.0D)).iterator();

                    if (p_a_1_.addEntity(entitywither, SpawnReason.BUILD_WITHER))
                    {
                        blockstatelistpopulator.updateList();

                        while (iterator.hasNext())
                        {
                            EntityHuman entityhuman = (EntityHuman)iterator.next();
                            entityhuman.b((Statistic)AchievementList.I);
                        }

                        for (int i1 = 0; i1 < 120; ++i1)
                        {
                            p_a_1_.addParticle(EnumParticle.SNOWBALL, (double)blockposition1.getX() + p_a_1_.random.nextDouble(), (double)(blockposition1.getY() - 2) + p_a_1_.random.nextDouble() * 3.9D, (double)blockposition1.getZ() + p_a_1_.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
                        }

                        for (int j1 = 0; j1 < shapedetector.c(); ++j1)
                        {
                            for (int j = 0; j < shapedetector.b(); ++j)
                            {
                                ShapeDetectorBlock shapedetectorblock1 = shapedetector$shapedetectorcollection.a(j1, j, 0);
                                p_a_1_.update(shapedetectorblock1.getPosition(), Blocks.AIR);
                            }
                        }
                    }
                }
            }
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, EnumDirection.fromType1(p_fromLegacyData_1_ & 7)).set(NODROP, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (((Boolean)p_toLegacyData_1_.get(NODROP)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, NODROP});
    }

    protected ShapeDetector l()
    {
        if (this.O == null)
        {
            this.O = ShapeDetectorBuilder.a().a(new String[] {"   ", "###", "~#~"}).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
        }

        return this.O;
    }

    protected ShapeDetector n()
    {
        if (this.P == null)
        {
            this.P = ShapeDetectorBuilder.a().a(new String[] {"^^^", "###", "~#~"}).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('^', N).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
        }

        return this.P;
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[EnumDirection.values().length];

        static
        {
            try
            {
                a[EnumDirection.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                a[EnumDirection.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                a[EnumDirection.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                a[EnumDirection.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[EnumDirection.EAST.ordinal()] = 5;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
