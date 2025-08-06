package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormat
{
    BLACK("BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", '1', 1),
    DARK_GREEN("DARK_GREEN", '2', 2),
    DARK_AQUA("DARK_AQUA", '3', 3),
    DARK_RED("DARK_RED", '4', 4),
    DARK_PURPLE("DARK_PURPLE", '5', 5),
    GOLD("GOLD", '6', 6),
    GRAY("GRAY", '7', 7),
    DARK_GRAY("DARK_GRAY", '8', 8),
    BLUE("BLUE", '9', 9),
    GREEN("GREEN", 'a', 10),
    AQUA("AQUA", 'b', 11),
    RED("RED", 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
    YELLOW("YELLOW", 'e', 14),
    WHITE("WHITE", 'f', 15),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1);

    private static final Map<String, EnumChatFormat> w = Maps.<String, EnumChatFormat>newHashMap();
    private static final Pattern x = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private final String y;
    private final char z;
    private final boolean A;
    private final String B;
    private final int C;

    private static String c(String p_c_0_)
    {
        return p_c_0_.toLowerCase().replaceAll("[^a-z]", "");
    }

    private EnumChatFormat(String p_i503_3_, char p_i503_4_, int p_i503_5_)
    {
        this(p_i503_3_, p_i503_4_, false, p_i503_5_);
    }

    private EnumChatFormat(String p_i504_3_, char p_i504_4_, boolean p_i504_5_)
    {
        this(p_i504_3_, p_i504_4_, p_i504_5_, -1);
    }

    private EnumChatFormat(String p_i505_3_, char p_i505_4_, boolean p_i505_5_, int p_i505_6_)
    {
        this.y = p_i505_3_;
        this.z = p_i505_4_;
        this.A = p_i505_5_;
        this.C = p_i505_6_;
        this.B = "\u00a7" + p_i505_4_;
    }

    public int b()
    {
        return this.C;
    }

    public boolean isFormat()
    {
        return this.A;
    }

    public boolean d()
    {
        return !this.A && this != RESET;
    }

    public String e()
    {
        return this.name().toLowerCase();
    }

    public String toString()
    {
        return this.B;
    }

    public static String a(String p_a_0_)
    {
        return p_a_0_ == null ? null : x.matcher(p_a_0_).replaceAll("");
    }

    public static EnumChatFormat b(String p_b_0_)
    {
        return p_b_0_ == null ? null : (EnumChatFormat)w.get(c(p_b_0_));
    }

    public static EnumChatFormat a(int p_a_0_)
    {
        if (p_a_0_ < 0)
        {
            return RESET;
        }
        else
        {
            for (EnumChatFormat enumchatformat : values())
            {
                if (enumchatformat.b() == p_a_0_)
                {
                    return enumchatformat;
                }
            }

            return null;
        }
    }

    public static Collection<String> a(boolean p_a_0_, boolean p_a_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (EnumChatFormat enumchatformat : values())
        {
            if ((!enumchatformat.d() || p_a_0_) && (!enumchatformat.isFormat() || p_a_1_))
            {
                arraylist.add(enumchatformat.e());
            }
        }

        return arraylist;
    }

    static {
        for (EnumChatFormat enumchatformat : values())
        {
            w.put(c(enumchatformat.y), enumchatformat);
        }
    }
}
