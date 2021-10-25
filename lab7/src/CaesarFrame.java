import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CaesarFrame extends JFrame{

    private final JComboBox jcb;
    private final JPanel p1, p2;
    private final JTextField f1, f2;
    private final JButton btn;

    private boolean decode=false;

    public CaesarFrame() throws HeadlessException {
        super("SwingLab");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 110);
        GridLayout lm=new GridLayout();
        lm.setRows(2);
        lm.setColumns(1);
        this.setLayout(lm);

        //felső panel
        p1=new JPanel(new FlowLayout());
        add(p1);
        Object chars[]=new Object[26];
        for(int i=(int)'A';i<=(int)'Z';i++)
            chars[i-(int)'A']=(char)i;
        jcb=new JComboBox(chars); p1.add(jcb);
        f1=new JTextField(20);
        f1.addFocusListener(new FocuseLoseListener(false));
        f1.setEnabled(true);
        f1.getDocument().addDocumentListener(new InputKeyFieldListener());
        p1.add(f1);
        btn=new JButton(); btn.setText("Code!");
        btn.addActionListener(new OkButtonActionListener());
        p1.add(btn);
        //------------------------------------------------

        //alsó panel
        p2=new JPanel(new BorderLayout());
        add(p2);
        p2.add(new JLabel("Output:"),BorderLayout.WEST);
        f2=new JTextField(20);
        f2.setEditable(true);
        f2.addFocusListener(new FocuseLoseListener(true));
        p2.add(f2);
        //-----------------------------------------------

        setResizable(true);
        setVisible(true);
    }

    private class OkButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(decode==true)
                f1.setText(CaesarCode.CaesarDecode(f2.getText(), (char)jcb.getSelectedItem()));
            else
                f2.setText(CaesarCode.CaesarCode(f1.getText(), (char)jcb.getSelectedItem()));
        }
    }

    private class FocuseLoseListener implements FocusListener
    {
        private boolean dir;
        public FocuseLoseListener(boolean b)
        {
            dir=b;
        }

        @Override
        public void focusGained(FocusEvent fe) {
//			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void focusLost(FocusEvent fe) {
            decode=dir;
        }

    }

    private class InputKeyFieldListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent de) {
            f2.setText(CaesarCode.CaesarCode(f1.getText(), (char)jcb.getSelectedItem()));
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            f2.setText(CaesarCode.CaesarCode(f1.getText(), (char)jcb.getSelectedItem()));
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            f2.setText(CaesarCode.CaesarCode(f1.getText(), (char)jcb.getSelectedItem()));
        }
    }
}

