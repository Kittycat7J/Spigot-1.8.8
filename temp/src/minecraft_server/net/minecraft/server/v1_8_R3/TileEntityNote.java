package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.NotePlayEvent;

public class TileEntityNote extends TileEntity {
   public byte note;
   public boolean f;

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setByte("note", this.note);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.note = p_a_1_.getByte("note");
      this.note = (byte)MathHelper.clamp(this.note, 0, 24);
   }

   public void b() {
      this.note = (byte)((this.note + 1) % 25);
      this.update();
   }

   public void play(World p_play_1_, BlockPosition p_play_2_) {
      if(p_play_1_.getType(p_play_2_.up()).getBlock().getMaterial() == Material.AIR) {
         Material material = p_play_1_.getType(p_play_2_.down()).getBlock().getMaterial();
         byte b0 = 0;
         if(material == Material.STONE) {
            b0 = 1;
         }

         if(material == Material.SAND) {
            b0 = 2;
         }

         if(material == Material.SHATTERABLE) {
            b0 = 3;
         }

         if(material == Material.WOOD) {
            b0 = 4;
         }

         NotePlayEvent noteplayevent = CraftEventFactory.callNotePlayEvent(this.world, p_play_2_.getX(), p_play_2_.getY(), p_play_2_.getZ(), b0, this.note);
         if(!noteplayevent.isCancelled()) {
            p_play_1_.playBlockAction(p_play_2_, Blocks.NOTEBLOCK, noteplayevent.getInstrument().getType(), noteplayevent.getNote().getId());
         }
      }

   }
}
