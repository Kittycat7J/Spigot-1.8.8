package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttributeMapServer extends AttributeMapBase
{
    private final Set<AttributeInstance> e = Sets.<AttributeInstance>newHashSet();
    protected final Map<String, AttributeInstance> d = new InsensitiveStringMap();

    public AttributeModifiable e(IAttribute p_e_1_)
    {
        return (AttributeModifiable)super.a(p_e_1_);
    }

    public AttributeModifiable b(String p_b_1_)
    {
        AttributeInstance attributeinstance = super.a(p_b_1_);

        if (attributeinstance == null)
        {
            attributeinstance = (AttributeInstance)this.d.get(p_b_1_);
        }

        return (AttributeModifiable)attributeinstance;
    }

    public AttributeInstance b(IAttribute p_b_1_)
    {
        AttributeInstance attributeinstance = super.b(p_b_1_);

        if (p_b_1_ instanceof AttributeRanged && ((AttributeRanged)p_b_1_).g() != null)
        {
            this.d.put(((AttributeRanged)p_b_1_).g(), attributeinstance);
        }

        return attributeinstance;
    }

    protected AttributeInstance c(IAttribute p_c_1_)
    {
        return new AttributeModifiable(this, p_c_1_);
    }

    public void a(AttributeInstance p_a_1_)
    {
        if (p_a_1_.getAttribute().c())
        {
            this.e.add(p_a_1_);
        }

        for (IAttribute iattribute : this.c.get(p_a_1_.getAttribute()))
        {
            AttributeModifiable attributemodifiable = this.e(iattribute);

            if (attributemodifiable != null)
            {
                attributemodifiable.f();
            }
        }
    }

    public Set<AttributeInstance> getAttributes()
    {
        return this.e;
    }

    public Collection<AttributeInstance> c()
    {
        HashSet hashset = Sets.newHashSet();

        for (AttributeInstance attributeinstance : this.a())
        {
            if (attributeinstance.getAttribute().c())
            {
                hashset.add(attributeinstance);
            }
        }

        return hashset;
    }
}
