package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class TileEntityEnchantTable extends TileEntity implements IUpdatePlayerListBox, ITileEntityContainer
{
    public int a;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public float l;
    public float m;
    public float n;
    private static Random o = new Random();
    private String p;

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);

        if (this.hasCustomName())
        {
            p_b_1_.setString("CustomName", this.p);
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("CustomName", 8))
        {
            this.p = p_a_1_.getString("CustomName");
        }
    }

    public void c()
    {
        this.k = this.j;
        this.m = this.l;
        EntityHuman entityhuman = this.world.findNearbyPlayer((double)((float)this.position.getX() + 0.5F), (double)((float)this.position.getY() + 0.5F), (double)((float)this.position.getZ() + 0.5F), 3.0D);

        if (entityhuman != null)
        {
            double d0 = entityhuman.locX - (double)((float)this.position.getX() + 0.5F);
            double d1 = entityhuman.locZ - (double)((float)this.position.getZ() + 0.5F);
            this.n = (float)MathHelper.b(d1, d0);
            this.j += 0.1F;

            if (this.j < 0.5F || o.nextInt(40) == 0)
            {
                float fx = this.h;

                while (true)
                {
                    this.h += (float)(o.nextInt(4) - o.nextInt(4));

                    if (fx != this.h)
                    {
                        break;
                    }
                }
            }
        }
        else
        {
            this.n += 0.02F;
            this.j -= 0.1F;
        }

        while (this.l >= (float)Math.PI)
        {
            this.l -= ((float)Math.PI * 2F);
        }

        while (this.l < -(float)Math.PI)
        {
            this.l += ((float)Math.PI * 2F);
        }

        while (this.n >= (float)Math.PI)
        {
            this.n -= ((float)Math.PI * 2F);
        }

        while (this.n < -(float)Math.PI)
        {
            this.n += ((float)Math.PI * 2F);
        }

        float f1;

        for (f1 = this.n - this.l; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F))
        {
            ;
        }

        while (f1 < -(float)Math.PI)
        {
            f1 += ((float)Math.PI * 2F);
        }

        this.l += f1 * 0.4F;
        this.j = MathHelper.a(this.j, 0.0F, 1.0F);
        ++this.a;
        this.g = this.f;
        float f2 = (this.h - this.f) * 0.4F;
        float f3 = 0.2F;
        f2 = MathHelper.a(f2, -f3, f3);
        this.i += (f2 - this.i) * 0.9F;
        this.f += this.i;
    }

    public String getName()
    {
        return this.hasCustomName() ? this.p : "container.enchant";
    }

    public boolean hasCustomName()
    {
        return this.p != null && this.p.length() > 0;
    }

    public void a(String p_a_1_)
    {
        this.p = p_a_1_;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return (IChatBaseComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }

    public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_)
    {
        return new ContainerEnchantTable(p_createContainer_1_, this.world, this.position);
    }

    public String getContainerName()
    {
        return "minecraft:enchanting_table";
    }
}
