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
		jbutton = new JButton("����2���ı���");
		jbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog jDialog = new JDialog();
				jDialog.setBounds(320, 180, 260, 100);
				jDialog.setTitle("�����ı���");
				jDialog.getContentPane().setLayout(new GridLayout(2, 2));
				jDialog.add(new JLabel("�ı���һ"));
				jDialog.add(new JTextField(80));
				jDialog.add(new JLabel("�ı����"));
				jDialog.add(new JTextField(80));
				// ȷ�������Ĵ�������������ǰ��
				jDialog.setModal(true);
				jDialog.setVisible(true);
			}
		});

		add(jbutton, BorderLayout.SOUTH);
		setBounds(300, 100, 320, 320);
		setTitle("����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FromeDemo();
	}
}