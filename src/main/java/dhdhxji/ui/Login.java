package dhdhxji.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dhdhxji.ui.common.LoginInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Login extends JFrame {
    public Login() {
        super("Login");

        setLayout(new GridBagLayout());

        _name.setPreferredSize(new Dimension(200, 30));
        _address.setPreferredSize(new Dimension(200, 30));
        _connect.setPreferredSize(new Dimension(_connect.getPreferredSize().width, 45));

        _connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyLogin();
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.2;
        add(new JLabel("Name"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(_name, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.2;
        c.fill = GridBagConstraints.NONE;
        add(new JLabel("Address"), c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.8;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(_address, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 0.7;
        c.fill = GridBagConstraints.BOTH;
        add(_connect,c);


        _address.setMaximumSize(new Dimension(8192, 100));
        _address.setMinimumSize(new Dimension(50, 100));

        _connect.setMinimumSize(new Dimension(50, 50));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        pack();
        //setResizable(false);
        setVisible(true);
    }

    public void registerLoginCallback(LoginInterface cb) {
        _loginListeners.add(cb);
    }

    private void notifyLogin() {
        String name = _name.getText();
        String address = _address.getText();
        
        for (LoginInterface cb : _loginListeners) {
            cb.login(name, address);
        }
    }

    private static final long serialVersionUID = -6526679708877401521L;

    private Vector<LoginInterface> _loginListeners = new Vector<LoginInterface>();
    private JTextField _name = new JTextField();
    private JTextField _address = new JTextField("localhost");
    private JButton _connect = new JButton("Connect");
}
