package net.minecraft.server.v1_8_R3;

import com.mojang.util.QueueLogAppender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import net.minecraft.server.v1_8_R3.DedicatedServer;
import net.minecraft.server.v1_8_R3.GuiStatsComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerListBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerGUI extends JComponent {
   private static final Font a = new Font("Monospaced", 0, 12);
   private static final Logger b = LogManager.getLogger();
   private DedicatedServer c;

   public static void a(final DedicatedServer p_a_0_) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var3) {
         ;
      }

      ServerGUI servergui = new ServerGUI(p_a_0_);
      JFrame jframe = new JFrame("Minecraft server");
      jframe.add(servergui);
      jframe.pack();
      jframe.setLocationRelativeTo((Component)null);
      jframe.setVisible(true);
      jframe.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent p_windowClosing_1_) {
            p_a_0_.safeShutdown();

            while(!p_a_0_.isStopped()) {
               try {
                  Thread.sleep(100L);
               } catch (InterruptedException interruptedexception) {
                  interruptedexception.printStackTrace();
               }
            }

            System.exit(0);
         }
      });
   }

   public ServerGUI(DedicatedServer p_i1069_1_) {
      this.c = p_i1069_1_;
      this.setPreferredSize(new Dimension(854, 480));
      this.setLayout(new BorderLayout());

      try {
         this.add(this.c(), "Center");
         this.add(this.a(), "West");
      } catch (Exception exception) {
         b.error((String)"Couldn\'t build server GUI", (Throwable)exception);
      }

   }

   private JComponent a() throws Exception {
      JPanel jpanel = new JPanel(new BorderLayout());
      jpanel.add(new GuiStatsComponent(this.c), "North");
      jpanel.add(this.b(), "Center");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
      return jpanel;
   }

   private JComponent b() throws Exception {
      PlayerListBox playerlistbox = new PlayerListBox(this.c);
      JScrollPane jscrollpane = new JScrollPane(playerlistbox, 22, 30);
      jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
      return jscrollpane;
   }

   private JComponent c() throws Exception {
      JPanel jpanel = new JPanel(new BorderLayout());
      final JTextArea jtextarea = new JTextArea();
      final JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);
      jtextarea.setEditable(false);
      jtextarea.setFont(a);
      final JTextField jtextfield = new JTextField();
      jtextfield.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent p_actionPerformed_1_) {
            String s = jtextfield.getText().trim();
            if(s.length() > 0) {
               ServerGUI.this.c.issueCommand(s, MinecraftServer.getServer());
            }

            jtextfield.setText("");
         }
      });
      jtextarea.addFocusListener(new FocusAdapter() {
         public void focusGained(FocusEvent p_focusGained_1_) {
         }
      });
      jpanel.add(jscrollpane, "Center");
      jpanel.add(jtextfield, "South");
      jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
      Thread thread = new Thread(new Runnable() {
         public void run() {
            String s;
            while((s = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
               ServerGUI.this.a(jtextarea, jscrollpane, s);
            }

         }
      });
      thread.setDaemon(true);
      thread.start();
      return jpanel;
   }

   public void a(final JTextArea p_a_1_, final JScrollPane p_a_2_, final String p_a_3_) {
      if(!SwingUtilities.isEventDispatchThread()) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               ServerGUI.this.a(p_a_1_, p_a_2_, p_a_3_);
            }
         });
      } else {
         Document document = p_a_1_.getDocument();
         JScrollBar jscrollbar = p_a_2_.getVerticalScrollBar();
         boolean flag = false;
         if(p_a_2_.getViewport().getView() == p_a_1_) {
            flag = (double)jscrollbar.getValue() + jscrollbar.getSize().getHeight() + (double)(a.getSize() * 4) > (double)jscrollbar.getMaximum();
         }

         try {
            document.insertString(document.getLength(), p_a_3_, (AttributeSet)null);
         } catch (BadLocationException var8) {
            ;
         }

         if(flag) {
            jscrollbar.setValue(Integer.MAX_VALUE);
         }

      }
   }
}
