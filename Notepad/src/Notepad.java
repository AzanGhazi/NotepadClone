import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {
    JTextArea area;
    String cacheString;
    Notepad () {
        //Setup application Title, Icon, and Jframe state
        setTitle("Notepad Clone");

        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("icons/Windows_Notepad_icon.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);

        //Setup menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);

        // Create file settings
        JMenu file = new JMenu("File");
        file.setFont( new Font("AERIAL", Font.PLAIN, 14));

        // Allow for new Notes
        JMenuItem newNote = new JMenuItem("New Note");
        newNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newNote.addActionListener(this);
        // Allow for opening existing Notes
        JMenuItem openNote = new JMenuItem("Open Note");
        openNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openNote.addActionListener(this);
        // Allow for saving Notes
        JMenuItem saveNote = new JMenuItem("Save Note");
        saveNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveNote.addActionListener(this);
        // Allow for printing Notes
        JMenuItem printNote = new JMenuItem("Print Note");
        printNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        printNote.addActionListener(this);
        // Allow for exiting the program
        JMenuItem exitNote = new JMenuItem("Exit");
        exitNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exitNote.addActionListener(this);

        file.add(newNote);
        file.add(openNote);
        file.add(saveNote);
        file.add(printNote);
        file.add(exitNote);
        menuBar.add(file);

        // Create edit settings
        JMenu edit = new JMenu("Edit");
        edit.setFont( new Font("AERIAL", Font.PLAIN, 14));

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cut.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        selectAll.addActionListener(this);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectAll);
        menuBar.add(edit);


        // Create help settings
        JMenu help = new JMenu("Help");
        help.setFont( new Font("AERIAL", Font.PLAIN, 14));

        JMenuItem link  = new JMenuItem("Visit Link");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        link.addActionListener(this);

        help.add(link);
        menuBar.add(help);


        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 18 ));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane pane = new JScrollPane(area);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane);
        setJMenuBar(menuBar);
        setExtendedState(JFrame.MAXIMIZED_BOTH);



        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*switch (e.getActionCommand()) {
            case "New Note" -> area.setText("");
        }*/
        if (e.getActionCommand().equals("New Note")) {
            area.setText("");
        } else if (e.getActionCommand().equals("Open Note")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter(".txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int action = chooser.showOpenDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = chooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                area.read(reader, null);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        } else if (e.getActionCommand().equals("Save Note")) {
            JFileChooser saver = new JFileChooser();
            saver.setApproveButtonText("Save");

            int action = saver.showOpenDialog(this);

            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File filename = new File(saver.getSelectedFile() + ".txt");

            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(filename));
                area.write(outFile);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Print Note")) {
            try {
                area.print();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (e.getActionCommand().equals("Exit")) {
        System.exit(0);
        } else if (e.getActionCommand().equals("Copy")) {
            cacheString = area.getSelectedText();
        } else if (e.getActionCommand().equals("Paste")) {
            area.insert(cacheString, area.getCaretPosition());
        } else if (e.getActionCommand().equals("Cut")) {
            cacheString = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        } else if (e.getActionCommand().equals("Select All")) {
            area.selectAll();
        } else if (e.getActionCommand().equals("Visit Link")){
            Runtime rt = Runtime.getRuntime();
            String url = "https://github.com/AzanGhazi";
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println(e.getActionCommand());
        }

    }
}
