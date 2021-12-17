package dungeon.dungeonview;

import dungeon.driver.Driver;
import dungeon.dungeoncontroller.DungeonGuiController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * This is an implementation of the DungeonView interface that uses Java Swing to draw the results
 * of the dungeon game. It contains a menu bar for restarting a new game using a same or different
 * dungeon and quitting the game. It displays the panel and other text related outputs of the game
 * such as player description,location description and game settings.
 */
public class DungeonViewImpl extends JFrame implements DungeonView {

  ReadonlyDungeonModel readOnlyDungeonModel;
  DungeonPanel jPanel;
  JTextArea jLabel;
  JTextPane playerDescription;

  /**
   * Initializes the read only dungeon model to be used by the view.
   *
   * @param readOnlyDungeonModel read only dungeon model
   */
  public DungeonViewImpl(ReadonlyDungeonModel readOnlyDungeonModel) {
    super("Dungeon Adventure Game @ copyrights 2021");
    setSize(680, 680);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

    setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JTextArea textArea = new JTextArea(
        "Size: " + readOnlyDungeonModel.getRows() + "x" + readOnlyDungeonModel.getColumns()
            + "\nInterconnectivity: " + readOnlyDungeonModel.getInterconnectivity()
            + "\nIsNonWrapping: " + readOnlyDungeonModel.isNonWrapping() + "\nDifficulty: "
            + readOnlyDungeonModel.getOtyughCount() + "\nItem Percentage: "
            + readOnlyDungeonModel.getPercentageOfItems() + "%");
    textArea.setFont(new Font("Times New Roman", Font.ITALIC, 15));
    textArea.setForeground(Color.BLACK);

    panel.add(textArea, BorderLayout.WEST);

    this.jLabel = new JTextArea(readOnlyDungeonModel.getPlayer().getCurrentLocation().toString());
    //this.jLabel.setEnabled(false);
    this.jLabel.setFont(new Font("Times New Roman", Font.ITALIC, 15));
    this.jLabel.setForeground(Color.BLACK);
    //add(jLabel, BorderLayout.SOUTH);
    panel.add(jLabel, BorderLayout.EAST);

    this.playerDescription = new JTextPane();
    StyledDocument doc = this.playerDescription.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);

    panel.add(this.playerDescription, BorderLayout.CENTER);// added new

    add(panel, BorderLayout.SOUTH);

    this.readOnlyDungeonModel = readOnlyDungeonModel;
    this.jPanel = new DungeonPanel(readOnlyDungeonModel);

    ScrollPane jScrollPane = new ScrollPane();
    jScrollPane.setPreferredSize(new Dimension(500, 500));
    jScrollPane.add(jPanel);
    add(jScrollPane, BorderLayout.CENTER);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Settings");

    menu.addSeparator();
    JMenu submenu = new JMenu("Restart Game");
    JMenuItem menuItem1 = new JMenuItem("Reuse Dungeon");
    menuItem1.addActionListener(e -> {
      String rows = String.valueOf(readOnlyDungeonModel.getRows());
      String cols = String.valueOf(readOnlyDungeonModel.getColumns());
      String interconnectivity = String.valueOf(readOnlyDungeonModel.getInterconnectivity());
      String isWrapping = String.valueOf(readOnlyDungeonModel.isNonWrapping());
      String otyughs = String.valueOf(readOnlyDungeonModel.getOtyughCount());
      String nItems = String.valueOf(readOnlyDungeonModel.getPercentageOfItems());

      Driver.main(new String[]{"gui", isWrapping, rows, cols, interconnectivity, otyughs, nItems});
      this.dispose();
    });

    JMenuItem menuItem2 = new JMenuItem("New Dungeon");
    menuItem2.addActionListener(e -> {
      JTextField rows = new JTextField("5");
      JTextField cols = new JTextField("5");
      JTextField interconnectivity = new JTextField("0");
      JTextField isWrapping = new JTextField("true");
      JTextField otyughs = new JTextField("2");
      JTextField nItems = new JTextField("50");

      Object[] inputFields = {"Enter number of rows (>=5)", rows, "Enter number of columns(>=5)",
          cols,
          "Enter interconnectivity", interconnectivity,
          "Enter if dungeon is non-wrapping or not (true/false)",
          isWrapping, "Enter number of otyughs (1-" + (Integer.parseInt(rows.getText())
          * Integer.parseInt(cols.getText())) + ")", otyughs, "Enter percentage of items %",
          nItems};

      int option = JOptionPane.showConfirmDialog(null, inputFields,
          "Dungeon Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
      if (option == 0) {
        Driver.main(new String[]{"gui", isWrapping.getText(), rows.getText(), cols.getText(),
            interconnectivity.getText(), otyughs.getText(), nItems.getText()});
        this.dispose();
      }
    });

    submenu.add(menuItem1);
    submenu.add(menuItem2);
    menu.add(submenu);

    JMenuItem menuItem3 = new JMenuItem("Quit Game");
    menuItem3.addActionListener(e -> System.exit(0));
    menu.add(menuItem3);

    JMenuItem menuItem4 = new JMenuItem("Game Info");
    menuItem4.addActionListener(e -> {
      Desktop desktop = Desktop.getDesktop();
      File myFile = new File("./res/Readme.md");
      try {
        desktop.open(myFile);
      } catch (IOException ex) {
        System.out.println("Can't read file");
      }

    });
    menu.add(menuItem4);

    menuBar.add(menu);
    add(menuBar, BorderLayout.NORTH);

  }

  @Override
  public void addClickListener(DungeonGuiController mouseListener) {
    this.jPanel.addMouseListener(new DungeonMouseAdaptor(mouseListener));
  }

  @Override
  public void addKeyListener(DungeonGuiController keyListener) {
    this.jPanel.addKeyListener(new DungeonKeyListener(keyListener, readOnlyDungeonModel));
    this.jPanel.setFocusable(true);
    this.jPanel.requestFocusInWindow();
  }

  @Override
  public void refresh() {
    if (readOnlyDungeonModel.getPlayer().getCurrentLocation().getOtyugh() == null) {
      if (readOnlyDungeonModel.getPlayer().getCurrentLocation()
          == readOnlyDungeonModel.getEndState()) {
        this.jLabel.setText("Player wins");
        this.jPanel.repaint();
        return;
      }
    } else {
      this.jLabel.setText("Game Over !! Player is Dead");
      this.jPanel.repaint();
      return;
    }

    this.playerDescription.setText(readOnlyDungeonModel.getPlayerDescription());
    this.jLabel.setText(readOnlyDungeonModel.getPlayerLocationDescription());
    this.jPanel.repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayKeyPress(String result) {
    Timer timer = new Timer(1500, e -> {
      this.refresh();
    });
    timer.setRepeats(false);
    timer.start();
    this.jLabel.setText(result);
  }

}
