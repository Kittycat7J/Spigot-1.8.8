package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public enum EnumDirection implements INamable
{
    DOWN(0, 1, -1, "down", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Y, new BaseBlockPosition(0, -1, 0)),
    UP(1, 0, -1, "up", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Y, new BaseBlockPosition(0, 1, 0)),
    NORTH(2, 3, 2, "north", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.Z, new BaseBlockPosition(0, 0, -1)),
    SOUTH(3, 2, 0, "south", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.Z, new BaseBlockPosition(0, 0, 1)),
    WEST(4, 5, 1, "west", EnumDirection.EnumAxisDirection.NEGATIVE, EnumDirection.EnumAxis.X, new BaseBlockPosition(-1, 0, 0)),
    EAST(5, 4, 3, "east", EnumDirection.EnumAxisDirection.POSITIVE, EnumDirection.EnumAxis.X, new BaseBlockPosition(1, 0, 0));

    private final int g;
    private final int h;
    private final int i;
    private final String j;
    private final EnumDirection.EnumAxis k;
    private final EnumDirection.EnumAxisDirection l;
    private final BaseBlockPosition m;
    private static final EnumDirection[] n = new EnumDirection[6];
    private static final EnumDirection[] o = new EnumDirection[4];
    private static final Map<String, EnumDirection> p = Maps.<String, EnumDirection>newHashMap();

    private EnumDirection(int p_i898_3_, int p_i898_4_, int p_i898_5_, String p_i898_6_, EnumDirection.EnumAxisDirection p_i898_7_, EnumDirection.EnumAxis p_i898_8_, BaseBlockPosition p_i898_9_)
    {
        this.g = p_i898_3_;
        this.i = p_i898_5_;
        this.h = p_i898_4_;
        this.j = p_i898_6_;
        this.k = p_i898_8_;
        this.l = p_i898_7_;
        this.m = p_i898_9_;
    }

    public int a()
    {
        return this.g;
    }

    public int b()
    {
        return this.i;
    }

    public EnumDirection.EnumAxisDirection c()
    {
        return this.l;
    }

    public EnumDirection opposite()
    {
        return fromType1(this.h);
    }

    public EnumDirection e()
    {
        switch (this)
        {
            case NORTH:
                return EAST;

            case EAST:
                return SOUTH;

            case SOUTH:
                return WEST;

            case WEST:
                return NORTH;

            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public EnumDirection f()
    {
        switch (this)
        {
            case NORTH:
                return WEST;

            case EAST:
                return NORTH;

            case SOUTH:
                return EAST;

            case WEST:
                return SOUTH;

            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int getAdjacentX()
    {
        return this.k == EnumDirection.EnumAxis.X ? this.l.a() : 0;
    }

    public int getAdjacentY()
    {
        return this.k == EnumDirection.EnumAxis.Y ? this.l.a() : 0;
    }

    public int getAdjacentZ()
    {
        return this.k == EnumDirection.EnumAxis.Z ? this.l.a() : 0;
    }

    public String j()
    {
        return this.j;
    }

    public EnumDirection.EnumAxis k()
    {
        return this.k;
    }

    public static EnumDirection fromType1(int p_fromType1_0_)
    {
        return n[MathHelper.a(p_fromType1_0_ % n.length)];
    }

    public static EnumDirection fromType2(int p_fromType2_0_)
    {
        return o[MathHelper.a(p_fromType2_0_ % o.length)];
    }

    public static EnumDirection fromAngle(double p_fromAngle_0_)
    {
        return fromType2(MathHelper.floor(p_fromAngle_0_ / 90.0D + 0.5D) & 3);
    }

    public static EnumDirection a(Random p_a_0_)
    {
        return values()[p_a_0_.nextInt(values().length)];
    }

    public String toString()
    {
        return this.j;
    }

    public String getName()
    {
        return this.j;
    }

    public static EnumDirection a(EnumDirection.EnumAxisDirection p_a_0_, EnumDirection.EnumAxis p_a_1_)
    {
        for (EnumDirection enumdirection : values())
        {
            if (enumdirection.c() == p_a_0_ && enumdirection.k() == p_a_1_)
            {
                return enumdirection;
            }
        }

        throw new IllegalArgumentException("No such direction: " + p_a_0_ + " " + p_a_1_);
    }

    static {
        for (EnumDirection enumdirection : values())
        {
            n[enumdirection.g] = enumdirection;

            if (enumdirection.k().c())
            {
                o[enumdirection.i] = enumdirection;
            }

            p.put(enumdirection.j().toLowerCase(), enumdirection);
        }
    }

    public static enum EnumAxis implements Predicate<EnumDirection>, INamable {
        X("x", EnumDirection.EnumDirectionLimit.HORIZONTAL),
        Y("y", EnumDirection.EnumDirectionLimit.VERTICAL),
        Z("z", EnumDirection.EnumDirectionLimit.HORIZONTAL);

        private static final Map<String, EnumDirection.EnumAxis> d = Maps.<String, EnumDirection.EnumAxis>newHashMap();
        private final String e;
        private final EnumDirection.EnumDirectionLimit f;

        private EnumAxis(String p_i895_3_, EnumDirection.EnumDirectionLimit p_i895_4_)
        {
            this.e = p_i895_3_;
            this.f = p_i895_4_;
        }

        public String a()
        {
            return this.e;
        }

        public boolean b()
        {
            return this.f == EnumDirection.EnumDirectionLimit.VERTICAL;
        }

        public boolean c()
        {
            return this.f == EnumDirection.EnumDirectionLimit.HORIZONTAL;
        }

        public String toString()
        {
            return this.e;
        }

        public boolean a(EnumDirection p_a_1_)
        {
            return p_a_1_ != null && p_a_1_.k() == this;
        }

        public EnumDirection.EnumDirectionLimit d()
        {
            return this.f;
        }

        public String getName()
        {
            return this.e;
        }

        static {
            for (EnumDirection.EnumAxis enumdirection$enumaxis : values())
            {
                d.put(enumdirection$enumaxis.a().toLowerCase(), enumdirection$enumaxis);
            }
        }
    }

    public static enum EnumAxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int c;
        private final String d;

        private EnumAxisDirection(int p_i896_3_, String p_i896_4_)
        {
            this.c = p_i896_3_;
            this.d = p_i896_4_;
        }

        public int a()
        {
            return this.c;
        }

        public String toString()
        {
            return this.d;
        }
    }

    public static enum EnumDirectionLimit implements Predicate<EnumDirection>, Iterable<EnumDirection> {
        HORIZONTAL,
        VERTICAL;

        public EnumDirection[] a()
        {
            switch (this)
            {
                case HORIZONTAL:
                    return new EnumDirection[] {EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH, EnumDirection.WEST};
                case VERTICAL:
                    return new EnumDirection[] {EnumDirection.UP, EnumDirection.DOWN};
                default:
                    throw new Error("Someone\'s been tampering with the universe!");
            }
        }

        public EnumDirection a(Random p_a_1_)
        {
            EnumDirection[] aenumdirection = this.a();
            return aenumdirection[p_a_1_.nextInt(aenumdirection.length)];
        }

        public boolean a(EnumDirection p_a_1_)
        {
            return p_a_1_ != null && p_a_1_.k().d() == this;
        }

        public Iterator<EnumDirection> iterator()
        {
            return Iterators.<EnumDirection>forArray(this.a());
        }
    }
}
