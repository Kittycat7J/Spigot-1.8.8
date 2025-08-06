package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityBeacon extends TileEntityContainer implements IUpdatePlayerListBox, IInventory
{
    public static final MobEffectList[][] a = new MobEffectList[][] {{MobEffectList.FASTER_MOVEMENT, MobEffectList.FASTER_DIG}, {MobEffectList.RESISTANCE, MobEffectList.JUMP}, {MobEffectList.INCREASE_DAMAGE}, {MobEffectList.REGENERATION}};
    private final List<TileEntityBeacon.BeaconColorTracker> f = Lists.<TileEntityBeacon.BeaconColorTracker>newArrayList();
    private boolean i;
    private int j = -1;
    private int k;
    private int l;
    private ItemStack inventorySlot;
    private String n;
    public List<HumanEntity> transaction = new ArrayList();
    private int maxStack = 64;

    public ItemStack[] getContents()
    {
        return new ItemStack[] {this.inventorySlot};
    }

    public void onOpen(CraftHumanEntity p_onOpen_1_)
    {
        this.transaction.add(p_onOpen_1_);
    }

    public void onClose(CraftHumanEntity p_onClose_1_)
    {
        this.transaction.remove(p_onClose_1_);
    }

    public List<HumanEntity> getViewers()
    {
        return this.transaction;
    }

    public void setMaxStackSize(int p_setMaxStackSize_1_)
    {
        this.maxStack = p_setMaxStackSize_1_;
    }

    public void c()
    {
        if (this.world.getTime() % 80L == 0L)
        {
            this.m();
        }
    }

    public void m()
    {
        this.B();
        this.A();
    }

    private void A()
    {
        if (this.i && this.j > 0 && !this.world.isClientSide && this.k > 0)
        {
            double d0 = (double)(this.j * 10 + 10);
            byte b0 = 0;

            if (this.j >= 4 && this.k == this.l)
            {
                b0 = 1;
            }

            int i = this.position.getX();
            int j = this.position.getY();
            int k = this.position.getZ();
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1))).grow(d0, d0, d0).a(0.0D, (double)this.world.getHeight(), 0.0D);
            List list = this.world.a(EntityHuman.class, axisalignedbb);

            for (EntityHuman entityhuman : list)
            {
                entityhuman.addEffect(new MobEffect(this.k, 180, b0, true, true));
            }

            if (this.j >= 4 && this.k != this.l && this.l > 0)
            {
                for (EntityHuman entityhuman1 : list)
                {
                    entityhuman1.addEffect(new MobEffect(this.l, 180, 0, true, true));
                }
            }
        }
    }

    private void B()
    {
        int i = this.j;
        int j = this.position.getX();
        int k = this.position.getY();
        int l = this.position.getZ();
        this.j = 0;
        this.f.clear();
        this.i = true;
        TileEntityBeacon.BeaconColorTracker tileentitybeacon$beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(EntitySheep.a(EnumColor.WHITE));
        this.f.add(tileentitybeacon$beaconcolortracker);
        boolean flag = true;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for (int i1 = k + 1; i1 < 256; ++i1)
        {
            IBlockData iblockdata = this.world.getType(blockposition$mutableblockposition.c(j, i1, l));
            float[] afloat;

            if (iblockdata.getBlock() == Blocks.STAINED_GLASS)
            {
                afloat = EntitySheep.a((EnumColor)iblockdata.get(BlockStainedGlass.COLOR));
            }
            else
            {
                if (iblockdata.getBlock() != Blocks.STAINED_GLASS_PANE)
                {
                    if (iblockdata.getBlock().p() >= 15 && iblockdata.getBlock() != Blocks.BEDROCK)
                    {
                        this.i = false;
                        this.f.clear();
                        break;
                    }

                    tileentitybeacon$beaconcolortracker.a();
                    continue;
                }

                afloat = EntitySheep.a((EnumColor)iblockdata.get(BlockStainedGlassPane.COLOR));
            }

            if (!flag)
            {
                afloat = new float[] {(tileentitybeacon$beaconcolortracker.b()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beaconcolortracker.b()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beaconcolortracker.b()[2] + afloat[2]) / 2.0F};
            }

            if (Arrays.equals(afloat, tileentitybeacon$beaconcolortracker.b()))
            {
                tileentitybeacon$beaconcolortracker.a();
            }
            else
            {
                tileentitybeacon$beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(afloat);
                this.f.add(tileentitybeacon$beaconcolortracker);
            }

            flag = false;
        }

        if (this.i)
        {
            for (int l11 = 1; l11 <= 4; this.j = l11++)
            {
                int i2 = k - l11;

                if (i2 < 0)
                {
                    break;
                }

                boolean flag1 = true;

                for (int j1 = j - l11; j1 <= j + l11 && flag1; ++j1)
                {
                    for (int k1 = l - l11; k1 <= l + l11; ++k1)
                    {
                        Block block = this.world.getType(new BlockPosition(j1, i2, k1)).getBlock();

                        if (block != Blocks.EMERALD_BLOCK && block != Blocks.GOLD_BLOCK && block != Blocks.DIAMOND_BLOCK && block != Blocks.IRON_BLOCK)
                        {
                            flag1 = false;
                            break;
                        }
                    }
                }

                if (!flag1)
                {
                    break;
                }
            }

            if (this.j == 0)
            {
                this.i = false;
            }
        }

        if (!this.world.isClientSide && this.j == 4 && i < this.j)
        {
            for (EntityHuman entityhuman : this.world.a(EntityHuman.class, (new AxisAlignedBB((double)j, (double)k, (double)l, (double)j, (double)(k - 4), (double)l)).grow(10.0D, 5.0D, 10.0D)))
            {
                entityhuman.b((Statistic)AchievementList.K);
            }
        }
    }

    public Packet getUpdatePacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.b(nbttagcompound);
        return new PacketPlayOutTileEntityData(this.position, 3, nbttagcompound);
    }

    private int h(int p_h_1_)
    {
        if (p_h_1_ >= 0 && p_h_1_ < MobEffectList.byId.length && MobEffectList.byId[p_h_1_] != null)
        {
            MobEffectList mobeffectlist = MobEffectList.byId[p_h_1_];
            return mobeffectlist != MobEffectList.FASTER_MOVEMENT && mobeffectlist != MobEffectList.FASTER_DIG && mobeffectlist != MobEffectList.RESISTANCE && mobeffectlist != MobEffectList.JUMP && mobeffectlist != MobEffectList.INCREASE_DAMAGE && mobeffectlist != MobEffectList.REGENERATION ? 0 : p_h_1_;
        }
        else
        {
            return 0;
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.k = this.h(p_a_1_.getInt("Primary"));
        this.l = this.h(p_a_1_.getInt("Secondary"));
        this.j = p_a_1_.getInt("Levels");
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("Primary", this.k);
        p_b_1_.setInt("Secondary", this.l);
        p_b_1_.setInt("Levels", this.j);
    }

    public int getSize()
    {
        return 1;
    }

    public ItemStack getItem(int p_getItem_1_)
    {
        return p_getItem_1_ == 0 ? this.inventorySlot : null;
    }

    public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_)
    {
        if (p_splitStack_1_ == 0 && this.inventorySlot != null)
        {
            if (p_splitStack_2_ >= this.inventorySlot.count)
            {
                ItemStack itemstack = this.inventorySlot;
                this.inventorySlot = null;
                return itemstack;
            }
            else
            {
                this.inventorySlot.count -= p_splitStack_2_;
                return new ItemStack(this.inventorySlot.getItem(), p_splitStack_2_, this.inventorySlot.getData());
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_)
    {
        if (p_splitWithoutUpdate_1_ == 0 && this.inventorySlot != null)
        {
            ItemStack itemstack = this.inventorySlot;
            this.inventorySlot = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setItem(int p_setItem_1_, ItemStack p_setItem_2_)
    {
        if (p_setItem_1_ == 0)
        {
            this.inventorySlot = p_setItem_2_;
        }
    }

    public String getName()
    {
        return this.hasCustomName() ? this.n : "container.beacon";
    }

    public boolean hasCustomName()
    {
        return this.n != null && this.n.length() > 0;
    }

    public void a(String p_a_1_)
    {
        this.n = p_a_1_;
    }

    public int getMaxStackSize()
    {
        return this.maxStack;
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return this.world.getTileEntity(this.position) != this ? false : p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
    }

    public void startOpen(EntityHuman p_startOpen_1_)
    {
    }

    public void closeContainer(EntityHuman p_closeContainer_1_)
    {
    }

    public boolean b(int p_b_1_, ItemStack p_b_2_)
    {
        return p_b_2_.getItem() == Items.EMERALD || p_b_2_.getItem() == Items.DIAMOND || p_b_2_.getItem() == Items.GOLD_INGOT || p_b_2_.getItem() == Items.IRON_INGOT;
    }

    public String getContainerName()
    {
        return "minecraft:beacon";
    }

    public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_)
    {
        return new ContainerBeacon(p_createContainer_1_, this);
    }

    public int getProperty(int p_getProperty_1_)
    {
        switch (p_getProperty_1_)
        {
            case 0:
                return this.j;

            case 1:
                return this.k;

            case 2:
                return this.l;

            default:
                return 0;
        }
    }

    public void b(int p_b_1_, int p_b_2_)
    {
        switch (p_b_1_)
        {
            case 0:
                this.j = p_b_2_;
                break;

            case 1:
                this.k = this.h(p_b_2_);
                break;

            case 2:
                this.l = this.h(p_b_2_);
        }
    }

    public int g()
    {
        return 3;
    }

    public void l()
    {
        this.inventorySlot = null;
    }

    public boolean c(int p_c_1_, int p_c_2_)
    {
        if (p_c_1_ == 1)
        {
            this.m();
            return true;
        }
        else
        {
            return super.c(p_c_1_, p_c_2_);
        }
    }

    public static class BeaconColorTracker
    {
        private final float[] a;
        private int b;

        public BeaconColorTracker(float[] p_i270_1_)
        {
            this.a = p_i270_1_;
            this.b = 1;
        }

        protected void a()
        {
            ++this.b;
        }

        public float[] b()
        {
            return this.a;
        }
    }
}
