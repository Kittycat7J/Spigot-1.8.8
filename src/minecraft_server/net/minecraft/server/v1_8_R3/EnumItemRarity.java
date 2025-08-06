package net.minecraft.server.v1_8_R3;

public enum EnumItemRarity
{
    COMMON(EnumChatFormat.WHITE, "Common"),
    UNCOMMON(EnumChatFormat.YELLOW, "Uncommon"),
    RARE(EnumChatFormat.AQUA, "Rare"),
    EPIC(EnumChatFormat.LIGHT_PURPLE, "Epic");

    public final EnumChatFormat e;
    public final String f;

    private EnumItemRarity(EnumChatFormat p_i512_3_, String p_i512_4_)
    {
        this.e = p_i512_3_;
        this.f = p_i512_4_;
    }
}
