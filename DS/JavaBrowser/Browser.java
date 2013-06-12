import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Browser extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public JPanel
        address_panel, window_panel;

    public JLabel
        address_label;

    public JTextField
        address_tf;

    public JEditorPane
        window_pane;

    public JScrollPane
        window_scroll;

    public JButton
        address_b;

    private Go go = new Go();

    public Browser() throws IOException {

        // Define address bar
        address_label = new JLabel(" address: ", SwingConstants.CENTER);
        address_tf = new JTextField("http://my.jce.ac.il/~arikgi/Web/");
        address_tf.addActionListener(go);
        address_b = new JButton("Go");
        address_b.addActionListener(go);

        window_pane = new JEditorPane("http://my.jce.ac.il/~arikgi/Web/");
        window_pane.setContentType("text/html");
        window_pane.setEditable(false);

        address_panel = new JPanel(new BorderLayout());
        window_panel = new JPanel(new BorderLayout());

        address_panel.add(address_label, BorderLayout.WEST);
        address_panel.add(address_tf, BorderLayout.CENTER);
        address_panel.add(address_b, BorderLayout.EAST);

        window_scroll = new JScrollPane(window_pane);
        window_panel.add(window_scroll);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(address_panel, BorderLayout.NORTH);
        pane.add(window_panel, BorderLayout.CENTER);

        setTitle("web browser");
        setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public class Go implements ActionListener{

        public void actionPerformed(ActionEvent ae){

            try {

                window_pane.setPage(address_tf.getText());

            } catch (MalformedURLException e) {     // new URL() failed
                window_pane.setText("MalformedURLException: " + e);
            } catch (IOException e) {               // openConnection() failed
                window_pane.setText("IOException: " + e);
            }

        }

    }

    public static void main(String args[]) throws IOException {
        @SuppressWarnings("unused")
        Browser wb = new Browser();
    }

}