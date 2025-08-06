package net.minecraft.server.v1_8_R3;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Painting;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;

public abstract class EntityHanging extends Entity
{
    private int c;
    public BlockPosition blockPosition;
    public EnumDirection direction;

    public EntityHanging(World p_i174_1_)
    {
        super(p_i174_1_);
        this.setSize(0.5F, 0.5F);
    }

    public EntityHanging(World p_i175_1_, BlockPosition p_i175_2_)
    {
        this(p_i175_1_);
        this.blockPosition = p_i175_2_;
    }

    protected void h()
    {
    }

    public void setDirection(EnumDirection p_setDirection_1_)
    {
        Validate.notNull(p_setDirection_1_);
        Validate.isTrue(p_setDirection_1_.k().c());
        this.direction = p_setDirection_1_;
        this.lastYaw = this.yaw = (float)(this.direction.b() * 90);
        this.updateBoundingBox();
    }

    public static AxisAlignedBB calculateBoundingBox(BlockPosition p_calculateBoundingBox_0_, EnumDirection p_calculateBoundingBox_1_, int p_calculateBoundingBox_2_, int p_calculateBoundingBox_3_)
    {
        double d0 = (double)p_calculateBoundingBox_0_.getX() + 0.5D;
        double d1 = (double)p_calculateBoundingBox_0_.getY() + 0.5D;
        double d2 = (double)p_calculateBoundingBox_0_.getZ() + 0.5D;
        double d3 = p_calculateBoundingBox_2_ % 32 == 0 ? 0.5D : 0.0D;
        double d4 = p_calculateBoundingBox_3_ % 32 == 0 ? 0.5D : 0.0D;
        d0 = d0 - (double)p_calculateBoundingBox_1_.getAdjacentX() * 0.46875D;
        d2 = d2 - (double)p_calculateBoundingBox_1_.getAdjacentZ() * 0.46875D;
        d1 = d1 + d4;
        EnumDirection enumdirection = p_calculateBoundingBox_1_.f();
        d0 = d0 + d3 * (double)enumdirection.getAdjacentX();
        d2 = d2 + d3 * (double)enumdirection.getAdjacentZ();
        double d5 = (double)p_calculateBoundingBox_2_;
        double d6 = (double)p_calculateBoundingBox_3_;
        double d7 = (double)p_calculateBoundingBox_2_;

        if (p_calculateBoundingBox_1_.k() == EnumDirection.EnumAxis.Z)
        {
            d7 = 1.0D;
        }
        else
        {
            d5 = 1.0D;
        }

        d5 = d5 / 32.0D;
        d6 = d6 / 32.0D;
        d7 = d7 / 32.0D;
        return new AxisAlignedBB(d0 - d5, d1 - d6, d2 - d7, d0 + d5, d1 + d6, d2 + d7);
    }

    private void updateBoundingBox()
    {
        if (this.direction != null)
        {
            AxisAlignedBB axisalignedbb = calculateBoundingBox(this.blockPosition, this.direction, this.l(), this.m());
            this.locX = (axisalignedbb.a + axisalignedbb.d) / 2.0D;
            this.locY = (axisalignedbb.b + axisalignedbb.e) / 2.0D;
            this.locZ = (axisalignedbb.c + axisalignedbb.f) / 2.0D;
            this.a(axisalignedbb);
        }
    }

    private double a(int p_a_1_)
    {
        return p_a_1_ % 32 == 0 ? 0.5D : 0.0D;
    }

    public void t_()
    {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;

        if (this.c++ == this.world.spigotConfig.hangingTickFrequency && !this.world.isClientSide)
        {
            this.c = 0;

            if (!this.dead && !this.survives())
            {
                Material material = this.world.getType(new BlockPosition(this)).getBlock().getMaterial();
                RemoveCause removecause;

                if (!material.equals(Material.AIR))
                {
                    removecause = RemoveCause.OBSTRUCTION;
                }
                else
                {
                    removecause = RemoveCause.PHYSICS;
                }

                HangingBreakEvent hangingbreakevent = new HangingBreakEvent((Hanging)this.getBukkitEntity(), removecause);
                this.world.getServer().getPluginManager().callEvent(hangingbreakevent);
                PaintingBreakEvent paintingbreakevent = null;

                if (this instanceof EntityPainting)
                {
                    paintingbreakevent = new PaintingBreakEvent((Painting)this.getBukkitEntity(), org.bukkit.event.painting.PaintingBreakEvent.RemoveCause.valueOf(removecause.name()));
                    paintingbreakevent.setCancelled(hangingbreakevent.isCancelled());
                    this.world.getServer().getPluginManager().callEvent(paintingbreakevent);
                }

                if (this.dead || hangingbreakevent.isCancelled() || paintingbreakevent != null && paintingbreakevent.isCancelled())
                {
                    return;
                }

                this.die();
                this.b((Entity)null);
            }
        }
    }

    public boolean survives()
    {
        if (!this.world.getCubes(this, this.getBoundingBox()).isEmpty())
        {
            return false;
        }
        else
        {
            int i = Math.max(1, this.l() / 16);
            int j = Math.max(1, this.m() / 16);
            BlockPosition blockposition = this.blockPosition.shift(this.direction.opposite());
            EnumDirection enumdirection = this.direction.f();

            for (int k = 0; k < i; ++k)
            {
                for (int l = 0; l < j; ++l)
                {
                    BlockPosition blockposition1 = blockposition.shift(enumdirection, k).up(l);
                    Block block = this.world.getType(blockposition1).getBlock();

                    if (!block.getMaterial().isBuildable() && !BlockDiodeAbstract.d(block))
                    {
                        return false;
                    }
                }
            }

            for (Entity entity : this.world.getEntities(this, this.getBoundingBox()))
            {
                if (entity instanceof EntityHanging)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean ad()
    {
        return true;
    }

    public boolean l(Entity p_l_1_)
    {
        return p_l_1_ instanceof EntityHuman ? this.damageEntity(DamageSource.playerAttack((EntityHuman)p_l_1_), 0.0F) : false;
    }

    public EnumDirection getDirection()
    {
        return this.direction;
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else
        {
            if (!this.dead && !this.world.isClientSide)
            {
                HangingBreakEvent hangingbreakevent = new HangingBreakEvent((Hanging)this.getBukkitEntity(), RemoveCause.DEFAULT);
                PaintingBreakEvent paintingbreakevent = null;

                if (p_damageEntity_1_.getEntity() != null)
                {
                    hangingbreakevent = new HangingBreakByEntityEvent((Hanging)this.getBukkitEntity(), p_damageEntity_1_.getEntity() == null ? null : p_damageEntity_1_.getEntity().getBukkitEntity());

                    if (this instanceof EntityPainting)
                    {
                        paintingbreakevent = new PaintingBreakByEntityEvent((Painting)this.getBukkitEntity(), p_damageEntity_1_.getEntity() == null ? null : p_damageEntity_1_.getEntity().getBukkitEntity());
                    }
                }
                else if (p_damageEntity_1_.isExplosion())
                {
                    hangingbreakevent = new HangingBreakEvent((Hanging)this.getBukkitEntity(), RemoveCause.EXPLOSION);
                }

                this.world.getServer().getPluginManager().callEvent(hangingbreakevent);

                if (paintingbreakevent != null)
                {
                    paintingbreakevent.setCancelled(hangingbreakevent.isCancelled());
                    this.world.getServer().getPluginManager().callEvent(paintingbreakevent);
                }

                if (this.dead || hangingbreakevent.isCancelled() || paintingbreakevent != null && paintingbreakevent.isCancelled())
                {
                    return true;
                }

                this.die();
                this.ac();
                this.b(p_damageEntity_1_.getEntity());
            }

            return true;
        }
    }

    public void move(double p_move_1_, double p_move_3_, double p_move_5_)
    {
        if (!this.world.isClientSide && !this.dead && p_move_1_ * p_move_1_ + p_move_3_ * p_move_3_ + p_move_5_ * p_move_5_ > 0.0D)
        {
            if (this.dead)
            {
                return;
            }

            HangingBreakEvent hangingbreakevent = new HangingBreakEvent((Hanging)this.getBukkitEntity(), RemoveCause.PHYSICS);
            this.world.getServer().getPluginManager().callEvent(hangingbreakevent);

            if (this.dead || hangingbreakevent.isCancelled())
            {
                return;
            }

            this.die();
            this.b((Entity)null);
        }
    }

    public void g(double p_g_1_, double p_g_3_, double p_g_5_)
    {
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setByte("Facing", (byte)this.direction.b());
        p_b_1_.setInt("TileX", this.getBlockPosition().getX());
        p_b_1_.setInt("TileY", this.getBlockPosition().getY());
        p_b_1_.setInt("TileZ", this.getBlockPosition().getZ());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.blockPosition = new BlockPosition(p_a_1_.getInt("TileX"), p_a_1_.getInt("TileY"), p_a_1_.getInt("TileZ"));
        EnumDirection enumdirection;

        if (p_a_1_.hasKeyOfType("Direction", 99))
        {
            enumdirection = EnumDirection.fromType2(p_a_1_.getByte("Direction"));
            this.blockPosition = this.blockPosition.shift(enumdirection);
        }
        else if (p_a_1_.hasKeyOfType("Facing", 99))
        {
            enumdirection = EnumDirection.fromType2(p_a_1_.getByte("Facing"));
        }
        else
        {
            enumdirection = EnumDirection.fromType2(p_a_1_.getByte("Dir"));
        }

        this.setDirection(enumdirection);
    }

    public abstract int l();

    public abstract int m();

    public abstract void b(Entity var1);

    protected boolean af()
    {
        return false;
    }

    public void setPosition(double p_setPosition_1_, double p_setPosition_3_, double p_setPosition_5_)
    {
        this.locX = p_setPosition_1_;
        this.locY = p_setPosition_3_;
        this.locZ = p_setPosition_5_;
        BlockPosition blockposition = this.blockPosition;
        this.blockPosition = new BlockPosition(p_setPosition_1_, p_setPosition_3_, p_setPosition_5_);

        if (!this.blockPosition.equals(blockposition))
        {
            this.updateBoundingBox();
            this.ai = true;
        }
    }

    public BlockPosition getBlockPosition()
    {
        return this.blockPosition;
    }
}
