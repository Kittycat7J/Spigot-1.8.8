package net.minecraft.server.v1_8_R3;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class PlayerListBox extends JList implements IUpdatePlayerListBox {
   private MinecraftServer a;
   private int b;

   public PlayerListBox(MinecraftServer p_i1070_1_) {
      this.a = p_i1070_1_;
      p_i1070_1_.a((IUpdatePlayerListBox)this);
   }

   public void c() {
      if(this.b++ % 20 == 0) {
         Vector vector = new Vector();

         for(int i = 0; i < this.a.getPlayerList().v().size(); ++i) {
            vector.add(((EntityPlayer)this.a.getPlayerList().v().get(i)).getName());
         }

         this.setListData(vector);
      }

   }
}
