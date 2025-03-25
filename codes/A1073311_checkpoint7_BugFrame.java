import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class A1073311_checkpoint7_BugFrame extends JFrame {
    public A1073311_checkpoint7_BugFrame(){
        this.setBounds(100, 100, 150, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        JLabel lblWithout = new JLabel("Collided!");
        lblWithout.setHorizontalAlignment(SwingConstants.CENTER);
        lblWithout.setFont(new Font("Calibri", Font.BOLD, 12));
        lblWithout.setBounds(11, 0, 98, 41);
        this.getContentPane().add(lblWithout);

        JButton btnConfirm = new JButton("Exit");
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        btnConfirm.setBounds(21, 39, 87, 23);
        this.getContentPane().add(btnConfirm);
    }
}
