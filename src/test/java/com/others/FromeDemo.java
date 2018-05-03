package com.others;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FromeDemo extends JFrame {
	JButton jbutton;

	public FromeDemo() {
		jbutton = new JButton("弹出2个文本框");
		jbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog jDialog = new JDialog();
				jDialog.setBounds(320, 180, 260, 100);
				jDialog.setTitle("弹出文本框");
				jDialog.getContentPane().setLayout(new GridLayout(2, 2));
				jDialog.add(new JLabel("文本框一"));
				jDialog.add(new JTextField(80));
				jDialog.add(new JLabel("文本框二"));
				jDialog.add(new JTextField(80));
				// 确保弹出的窗口在其他窗口前面
				jDialog.setModal(true);
				jDialog.setVisible(true);
			}
		});

		add(jbutton, BorderLayout.SOUTH);
		setBounds(300, 100, 320, 320);
		setTitle("测试");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FromeDemo();
	}
}