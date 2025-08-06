package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockTNT extends Block
{
    public static final BlockStateBoolean EXPLODE = BlockStateBoolean.of("explode");

    public BlockTNT()
    {
        super(Material.TNT);
        this.j(this.blockStateList.getBlockData().set(EXPLODE, Boolean.valueOf(false)));
        this.a(CreativeModeTab.d);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        super.onPlace(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);

        if (p_onPlace_1_.isBlockIndirectlyPowered(p_onPlace_2_))
        {
            this.postBreak(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_.set(EXPLODE, Boolean.valueOf(true)));
            p_onPlace_1_.setAir(p_onPlace_2_);
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_))
        {
            this.postBreak(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_.set(EXPLODE, Boolean.valueOf(true)));
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    public void wasExploded(World p_wasExploded_1_, BlockPosition p_wasExploded_2_, Explosion p_wasExploded_3_)
    {
        if (!p_wasExploded_1_.isClientSide)
        {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(p_wasExploded_1_, (double)((float)p_wasExploded_2_.getX() + 0.5F), (double)p_wasExploded_2_.getY(), (double)((float)p_wasExploded_2_.getZ() + 0.5F), p_wasExploded_3_.getSource());
            entitytntprimed.fuseTicks = p_wasExploded_1_.random.nextInt(entitytntprimed.fuseTicks / 4) + entitytntprimed.fuseTicks / 8;
            p_wasExploded_1_.addEntity(entitytntprimed);
        }
    }

    public void postBreak(World p_postBreak_1_, BlockPosition p_postBreak_2_, IBlockData p_postBreak_3_)
    {
        this.a(p_postBreak_1_, p_postBreak_2_, p_postBreak_3_, (EntityLiving)null);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityLiving p_a_4_)
    {
        if (!p_a_1_.isClientSide && ((Boolean)p_a_3_.get(EXPLODE)).booleanValue())
        {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(p_a_1_, (double)((float)p_a_2_.getX() + 0.5F), (double)p_a_2_.getY(), (double)((float)p_a_2_.getZ() + 0.5F), p_a_4_);
            p_a_1_.addEntity(entitytntprimed);
            p_a_1_.makeSound(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
        }
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_4_.bZ() != null)
        {
            Item item = p_interact_4_.bZ().getItem();

            if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE)
            {
                this.a(p_interact_1_, p_interact_2_, p_interact_3_.set(EXPLODE, Boolean.valueOf(true)), (EntityLiving)p_interact_4_);
                p_interact_1_.setAir(p_interact_2_);

                if (item == Items.FLINT_AND_STEEL)
                {
                    p_interact_4_.bZ().damage(1, p_interact_4_);
                }
                else if (!p_interact_4_.abilities.canInstantlyBuild)
                {
                    --p_interact_4_.bZ().count;
                }

                return true;
            }
        }

        return super.interact(p_interact_1_, p_interact_2_, p_interact_3_, p_interact_4_, p_interact_5_, p_interact_6_, p_interact_7_, p_interact_8_);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        if (!p_a_1_.isClientSide && p_a_4_ instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)p_a_4_;

            if (entityarrow.isBurning())
            {
                if (CraftEventFactory.callEntityChangeBlockEvent(entityarrow, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ(), Blocks.AIR, 0).isCancelled())
                {
                    return;
                }

                this.a(p_a_1_, p_a_2_, p_a_1_.getType(p_a_2_).set(EXPLODE, Boolean.valueOf(true)), entityarrow.shooter instanceof EntityLiving ? (EntityLiving)entityarrow.shooter : null);
                p_a_1_.setAir(p_a_2_);
            }
        }
    }

    public boolean a(Explosion p_a_1_)
    {
        return false;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(EXPLODE, Boolean.valueOf((p_fromLegacyData_1_ & 1) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Boolean)p_toLegacyData_1_.get(EXPLODE)).booleanValue() ? 1 : 0;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {EXPLODE});
    }
}
