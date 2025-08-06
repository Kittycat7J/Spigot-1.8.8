package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Explosion
{
    private final boolean a;
    private final boolean b;
    private final Random c = new Random();
    private final World world;
    private final double posX;
    private final double posY;
    private final double posZ;
    public final Entity source;
    private final float size;
    private final List<BlockPosition> blocks = Lists.<BlockPosition>newArrayList();
    private final Map<EntityHuman, Vec3D> k = Maps.<EntityHuman, Vec3D>newHashMap();
    public boolean wasCanceled = false;

    public Explosion(World p_i460_1_, Entity p_i460_2_, double p_i460_3_, double p_i460_5_, double p_i460_7_, float p_i460_9_, boolean p_i460_10_, boolean p_i460_11_)
    {
        this.world = p_i460_1_;
        this.source = p_i460_2_;
        this.size = (float)Math.max((double)p_i460_9_, 0.0D);
        this.posX = p_i460_3_;
        this.posY = p_i460_5_;
        this.posZ = p_i460_7_;
        this.a = p_i460_10_;
        this.b = p_i460_11_;
    }

    public void a()
    {
        if (this.size >= 0.1F)
        {
            HashSet hashset = Sets.newHashSet();

            for (int i = 0; i < 16; ++i)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15)
                        {
                            double d0 = (double)((float)i / 15.0F * 2.0F - 1.0F);
                            double d1 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                            double d2 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 = d0 / d3;
                            d1 = d1 / d3;
                            d2 = d2 / d3;
                            float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
                            double d4 = this.posX;
                            double d5 = this.posY;

                            for (double d6 = this.posZ; f > 0.0F; f -= 0.22500001F)
                            {
                                BlockPosition blockposition = new BlockPosition(d4, d5, d6);
                                IBlockData iblockdata = this.world.getType(blockposition);

                                if (iblockdata.getBlock().getMaterial() != Material.AIR)
                                {
                                    float f1 = this.source != null ? this.source.a(this, this.world, blockposition, iblockdata) : iblockdata.getBlock().a((Entity)null);
                                    f -= (f1 + 0.3F) * 0.3F;
                                }

                                if (f > 0.0F && (this.source == null || this.source.a(this, this.world, blockposition, iblockdata, f)) && blockposition.getY() < 256 && blockposition.getY() >= 0)
                                {
                                    hashset.add(blockposition);
                                }

                                d4 += d0 * 0.30000001192092896D;
                                d5 += d1 * 0.30000001192092896D;
                                d6 += d2 * 0.30000001192092896D;
                            }
                        }
                    }
                }
            }

            this.blocks.addAll(hashset);
            float f2 = this.size * 2.0F;
            int i2 = MathHelper.floor(this.posX - (double)f2 - 1.0D);
            int j2 = MathHelper.floor(this.posX + (double)f2 + 1.0D);
            int l = MathHelper.floor(this.posY - (double)f2 - 1.0D);
            int i1 = MathHelper.floor(this.posY + (double)f2 + 1.0D);
            int j1 = MathHelper.floor(this.posZ - (double)f2 - 1.0D);
            int k1 = MathHelper.floor(this.posZ + (double)f2 + 1.0D);
            List list = this.world.getEntities(this.source, new AxisAlignedBB((double)i2, (double)l, (double)j1, (double)j2, (double)i1, (double)k1));
            Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);

            for (int l1 = 0; l1 < list.size(); ++l1)
            {
                Entity entity = (Entity)list.get(l1);

                if (!entity.aW())
                {
                    double d7 = entity.f(this.posX, this.posY, this.posZ) / (double)f2;

                    if (d7 <= 1.0D)
                    {
                        double d8 = entity.locX - this.posX;
                        double d9 = entity.locY + (double)entity.getHeadHeight() - this.posY;
                        double d10 = entity.locZ - this.posZ;
                        double d11 = (double)MathHelper.sqrt(d8 * d8 + d9 * d9 + d10 * d10);

                        if (d11 != 0.0D)
                        {
                            d8 = d8 / d11;
                            d9 = d9 / d11;
                            d10 = d10 / d11;
                            double d12 = (double)this.world.a(vec3d, entity.getBoundingBox());
                            double d13 = (1.0D - d7) * d12;
                            CraftEventFactory.entityDamage = this.source;
                            entity.forceExplosionKnockback = false;
                            boolean flag = entity.damageEntity(DamageSource.explosion(this), (float)((int)((d13 * d13 + d13) / 2.0D * 8.0D * (double)f2 + 1.0D)));
                            CraftEventFactory.entityDamage = null;

                            if (flag || entity instanceof EntityTNTPrimed || entity instanceof EntityFallingBlock || entity.forceExplosionKnockback)
                            {
                                double d14 = EnchantmentProtection.a(entity, d13);
                                entity.motX += d8 * d14;
                                entity.motY += d9 * d14;
                                entity.motZ += d10 * d14;

                                if (entity instanceof EntityHuman && !((EntityHuman)entity).abilities.isInvulnerable)
                                {
                                    this.k.put((EntityHuman)entity, new Vec3D(d8 * d13, d9 * d13, d10 * d13));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(boolean p_a_1_)
    {
        this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);

        if (this.size >= 2.0F && this.b)
        {
            this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
        }
        else
        {
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
        }

        if (this.b)
        {
            org.bukkit.World world = this.world.getWorld();
            org.bukkit.entity.Entity entity = this.source == null ? null : this.source.getBukkitEntity();
            Location location = new Location(world, this.posX, this.posY, this.posZ);
            List<org.bukkit.block.Block> list = Lists.<org.bukkit.block.Block>newArrayList();

            for (int i = this.blocks.size() - 1; i >= 0; --i)
            {
                BlockPosition blockposition = (BlockPosition)this.blocks.get(i);
                org.bukkit.block.Block block = world.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());

                if (block.getType() != org.bukkit.Material.AIR)
                {
                    list.add(block);
                }
            }

            boolean flag;
            List<org.bukkit.block.Block> list1;
            float f;

            if (entity != null)
            {
                EntityExplodeEvent entityexplodeevent = new EntityExplodeEvent(entity, location, list, 0.3F);
                this.world.getServer().getPluginManager().callEvent(entityexplodeevent);
                flag = entityexplodeevent.isCancelled();
                list1 = entityexplodeevent.blockList();
                f = entityexplodeevent.getYield();
            }
            else
            {
                BlockExplodeEvent blockexplodeevent = new BlockExplodeEvent(location.getBlock(), list, 0.3F);
                this.world.getServer().getPluginManager().callEvent(blockexplodeevent);
                flag = blockexplodeevent.isCancelled();
                list1 = blockexplodeevent.blockList();
                f = blockexplodeevent.getYield();
            }

            this.blocks.clear();

            for (org.bukkit.block.Block block1 : list1)
            {
                BlockPosition blockposition1 = new BlockPosition(block1.getX(), block1.getY(), block1.getZ());
                this.blocks.add(blockposition1);
            }

            if (flag)
            {
                this.wasCanceled = true;
                return;
            }

            for (BlockPosition blockposition2 : this.blocks)
            {
                Block block2 = this.world.getType(blockposition2).getBlock();
                this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition2);

                if (p_a_1_)
                {
                    double d0 = (double)((float)blockposition2.getX() + this.world.random.nextFloat());
                    double d1 = (double)((float)blockposition2.getY() + this.world.random.nextFloat());
                    double d2 = (double)((float)blockposition2.getZ() + this.world.random.nextFloat());
                    double d3 = d0 - this.posX;
                    double d4 = d1 - this.posY;
                    double d5 = d2 - this.posZ;
                    double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 = d3 / d6;
                    d4 = d4 / d6;
                    d5 = d5 / d6;
                    double d7 = 0.5D / (d6 / (double)this.size + 0.1D);
                    d7 = d7 * (double)(this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F);
                    d3 = d3 * d7;
                    d4 = d4 * d7;
                    d5 = d5 * d7;
                    this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, (d0 + this.posX * 1.0D) / 2.0D, (d1 + this.posY * 1.0D) / 2.0D, (d2 + this.posZ * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
                    this.world.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                }

                if (block2.getMaterial() != Material.AIR)
                {
                    if (block2.a(this))
                    {
                        block2.dropNaturally(this.world, blockposition2, this.world.getType(blockposition2), f, 0);
                    }

                    this.world.setTypeAndData(blockposition2, Blocks.AIR.getBlockData(), 3);
                    block2.wasExploded(this.world, blockposition2, this);
                }
            }
        }

        if (this.a)
        {
            for (BlockPosition blockposition3 : this.blocks)
            {
                if (this.world.getType(blockposition3).getBlock().getMaterial() == Material.AIR && this.world.getType(blockposition3.down()).getBlock().o() && this.c.nextInt(3) == 0 && !CraftEventFactory.callBlockIgniteEvent(this.world, blockposition3.getX(), blockposition3.getY(), blockposition3.getZ(), this).isCancelled())
                {
                    this.world.setTypeUpdate(blockposition3, Blocks.FIRE.getBlockData());
                }
            }
        }
    }

    public Map<EntityHuman, Vec3D> b()
    {
        return this.k;
    }

    public EntityLiving getSource()
    {
        return this.source == null ? null : (this.source instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.source).getSource() : (this.source instanceof EntityLiving ? (EntityLiving)this.source : (this.source instanceof EntityFireball ? ((EntityFireball)this.source).shooter : null)));
    }

    public void clearBlocks()
    {
        this.blocks.clear();
    }

    public List<BlockPosition> getBlocks()
    {
        return this.blocks;
    }
}
