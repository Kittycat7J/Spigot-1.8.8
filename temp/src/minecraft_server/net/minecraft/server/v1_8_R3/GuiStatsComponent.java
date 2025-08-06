package net.minecraft.server.v1_8_R3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class GuiStatsComponent extends JComponent {
   private static final DecimalFormat a = new DecimalFormat("########0.000");
   private int[] b = new int[256];
   private int c;
   private String[] d = new String[11];
   private final MinecraftServer e;

   public GuiStatsComponent(MinecraftServer p_i1072_1_) {
      this.e = p_i1072_1_;
      this.setPreferredSize(new Dimension(456, 246));
      this.setMinimumSize(new Dimension(456, 246));
      this.setMaximumSize(new Dimension(456, 246));
      (new Timer(500, new ActionListener() {
         public void actionPerformed(ActionEvent p_actionPerformed_1_) {
            GuiStatsComponent.this.a();
         }
      })).start();
      this.setBackground(Color.BLACK);
   }

   private void a() {
      long i = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      System.gc();
      this.d[0] = "Memory use: " + i / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
      this.d[1] = "Avg tick: " + a.format(this.a(this.e.h) * 1.0E-6D) + " ms";
      this.repaint();
   }

   private double a(long[] p_a_1_) {
      long i = 0L;

      for(int j = 0; j < p_a_1_.length; ++j) {
         i += p_a_1_[j];
      }

      return (double)i / (double)p_a_1_.length;
   }

   public void paint(Graphics p_paint_1_) {
      p_paint_1_.setColor(new Color(16777215));
      p_paint_1_.fillRect(0, 0, 456, 246);

      for(int i = 0; i < 256; ++i) {
         int j = this.b[i + this.c & 255];
         p_paint_1_.setColor(new Color(j + 28 << 16));
         p_paint_1_.fillRect(i, 100 - j, 1, j);
      }

      p_paint_1_.setColor(Color.BLACK);

      for(int k = 0; k < this.d.length; ++k) {
         String s = this.d[k];
         if(s != null) {
            p_paint_1_.drawString(s, 32, 116 + k * 16);
         }
      }

   }
}
