package net.minecraft.server.v1_8_R3;

import com.google.gson.JsonParseException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatClickable;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatComponentUtils;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateSign;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

public class TileEntitySign extends TileEntity {
   public final IChatBaseComponent[] lines = new IChatBaseComponent[]{new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};
   public int f = -1;
   public boolean isEditable = true;
   private EntityHuman h;
   private final CommandObjectiveExecutor i = new CommandObjectiveExecutor();

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);

      for(int i = 0; i < 4; ++i) {
         String s = IChatBaseComponent.ChatSerializer.a(this.lines[i]);
         p_b_1_.setString("Text" + (i + 1), s);
      }

      if(Boolean.getBoolean("convertLegacySigns")) {
         p_b_1_.setBoolean("Bukkit.isConverted", true);
      }

      this.i.b(p_b_1_);
   }

   public void a(NBTTagCompound p_a_1_) {
      this.isEditable = false;
      super.a(p_a_1_);
      ICommandListener icommandlistener = new ICommandListener() {
         public String getName() {
            return "Sign";
         }

         public IChatBaseComponent getScoreboardDisplayName() {
            return new ChatComponentText(this.getName());
         }

         public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
         }

         public boolean a(int p_a_1_, String p_a_2_) {
            return true;
         }

         public BlockPosition getChunkCoordinates() {
            return TileEntitySign.this.position;
         }

         public Vec3D d() {
            return new Vec3D((double)TileEntitySign.this.position.getX() + 0.5D, (double)TileEntitySign.this.position.getY() + 0.5D, (double)TileEntitySign.this.position.getZ() + 0.5D);
         }

         public World getWorld() {
            return TileEntitySign.this.world;
         }

         public Entity f() {
            return null;
         }

         public boolean getSendCommandFeedback() {
            return false;
         }

         public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
         }
      };
      boolean flag = Boolean.getBoolean("convertLegacySigns") && !p_a_1_.getBoolean("Bukkit.isConverted");

      for(int i = 0; i < 4; ++i) {
         String s = p_a_1_.getString("Text" + (i + 1));
         if(s != null && s.length() > 2048) {
            s = "\"\"";
         }

         try {
            IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
            if(flag) {
               this.lines[i] = CraftChatMessage.fromString(s)[0];
            } else {
               try {
                  this.lines[i] = ChatComponentUtils.filterForDisplay(icommandlistener, ichatbasecomponent, (Entity)null);
               } catch (CommandException var7) {
                  this.lines[i] = ichatbasecomponent;
               }
            }
         } catch (JsonParseException var8) {
            this.lines[i] = new ChatComponentText(s);
         }
      }

      this.i.a(p_a_1_);
   }

   public Packet getUpdatePacket() {
      IChatBaseComponent[] aichatbasecomponent = new IChatBaseComponent[4];
      System.arraycopy(this.lines, 0, aichatbasecomponent, 0, 4);
      return new PacketPlayOutUpdateSign(this.world, this.position, aichatbasecomponent);
   }

   public boolean F() {
      return true;
   }

   public boolean b() {
      return this.isEditable;
   }

   public void a(EntityHuman p_a_1_) {
      this.h = p_a_1_;
   }

   public EntityHuman c() {
      return this.h;
   }

   public boolean b(final EntityHuman p_b_1_) {
      ICommandListener icommandlistener = new ICommandListener() {
         public String getName() {
            return p_b_1_.getName();
         }

         public IChatBaseComponent getScoreboardDisplayName() {
            return p_b_1_.getScoreboardDisplayName();
         }

         public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
         }

         public boolean a(int p_a_1_, String p_a_2_) {
            return p_a_1_ <= 2;
         }

         public BlockPosition getChunkCoordinates() {
            return TileEntitySign.this.position;
         }

         public Vec3D d() {
            return new Vec3D((double)TileEntitySign.this.position.getX() + 0.5D, (double)TileEntitySign.this.position.getY() + 0.5D, (double)TileEntitySign.this.position.getZ() + 0.5D);
         }

         public World getWorld() {
            return p_b_1_.getWorld();
         }

         public Entity f() {
            return p_b_1_;
         }

         public boolean getSendCommandFeedback() {
            return false;
         }

         public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_, int p_a_2_) {
            TileEntitySign.this.i.a(this, p_a_1_, p_a_2_);
         }
      };

      for(int i = 0; i < this.lines.length; ++i) {
         ChatModifier chatmodifier = this.lines[i] == null?null:this.lines[i].getChatModifier();
         if(chatmodifier != null && chatmodifier.h() != null) {
            ChatClickable chatclickable = chatmodifier.h();
            if(chatclickable.a() == ChatClickable.EnumClickAction.RUN_COMMAND) {
               CommandBlockListenerAbstract.executeCommand(p_b_1_, (Player)p_b_1_.getBukkitEntity(), chatclickable.b());
            }
         }
      }

      return true;
   }

   public CommandObjectiveExecutor d() {
      return this.i;
   }
}
