package net.minecraft.server.v1_8_R3;

public enum EnumColor implements INamable
{
    WHITE(0, 15, "white", "white", MaterialMapColor.j, EnumChatFormat.WHITE),
    ORANGE(1, 14, "orange", "orange", MaterialMapColor.q, EnumChatFormat.GOLD),
    MAGENTA(2, 13, "magenta", "magenta", MaterialMapColor.r, EnumChatFormat.AQUA),
    LIGHT_BLUE(3, 12, "light_blue", "lightBlue", MaterialMapColor.s, EnumChatFormat.BLUE),
    YELLOW(4, 11, "yellow", "yellow", MaterialMapColor.t, EnumChatFormat.YELLOW),
    LIME(5, 10, "lime", "lime", MaterialMapColor.u, EnumChatFormat.GREEN),
    PINK(6, 9, "pink", "pink", MaterialMapColor.v, EnumChatFormat.LIGHT_PURPLE),
    GRAY(7, 8, "gray", "gray", MaterialMapColor.w, EnumChatFormat.DARK_GRAY),
    SILVER(8, 7, "silver", "silver", MaterialMapColor.x, EnumChatFormat.GRAY),
    CYAN(9, 6, "cyan", "cyan", MaterialMapColor.y, EnumChatFormat.DARK_AQUA),
    PURPLE(10, 5, "purple", "purple", MaterialMapColor.z, EnumChatFormat.DARK_PURPLE),
    BLUE(11, 4, "blue", "blue", MaterialMapColor.A, EnumChatFormat.DARK_BLUE),
    BROWN(12, 3, "brown", "brown", MaterialMapColor.B, EnumChatFormat.GOLD),
    GREEN(13, 2, "green", "green", MaterialMapColor.C, EnumChatFormat.DARK_GREEN),
    RED(14, 1, "red", "red", MaterialMapColor.D, EnumChatFormat.DARK_RED),
    BLACK(15, 0, "black", "black", MaterialMapColor.E, EnumChatFormat.BLACK);

    private static final EnumColor[] q = new EnumColor[values().length];
    private static final EnumColor[] r = new EnumColor[values().length];
    private final int s;
    private final int t;
    private final String u;
    private final String v;
    private final MaterialMapColor w;
    private final EnumChatFormat x;

    private EnumColor(int p_i1274_3_, int p_i1274_4_, String p_i1274_5_, String p_i1274_6_, MaterialMapColor p_i1274_7_, EnumChatFormat p_i1274_8_)
    {
        this.s = p_i1274_3_;
        this.t = p_i1274_4_;
        this.u = p_i1274_5_;
        this.v = p_i1274_6_;
        this.w = p_i1274_7_;
        this.x = p_i1274_8_;
    }

    public int getColorIndex()
    {
        return this.s;
    }

    public int getInvColorIndex()
    {
        return this.t;
    }

    public String d()
    {
        return this.v;
    }

    public MaterialMapColor e()
    {
        return this.w;
    }

    public static EnumColor fromInvColorIndex(int p_fromInvColorIndex_0_)
    {
        if (p_fromInvColorIndex_0_ < 0 || p_fromInvColorIndex_0_ >= r.length)
        {
            p_fromInvColorIndex_0_ = 0;
        }

        return r[p_fromInvColorIndex_0_];
    }

    public static EnumColor fromColorIndex(int p_fromColorIndex_0_)
    {
        if (p_fromColorIndex_0_ < 0 || p_fromColorIndex_0_ >= q.length)
        {
            p_fromColorIndex_0_ = 0;
        }

        return q[p_fromColorIndex_0_];
    }

    public String toString()
    {
        return this.v;
    }

    public String getName()
    {
        return this.u;
    }

    static {
        for (EnumColor enumcolor : values())
        {
            q[enumcolor.getColorIndex()] = enumcolor;
            r[enumcolor.getInvColorIndex()] = enumcolor;
        }
    }
}
