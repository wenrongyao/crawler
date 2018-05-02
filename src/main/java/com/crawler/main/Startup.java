package com.crawler.main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.common.constant.R;

/**
 * ui���棬��swingʵ��
 * 
 * @author Administrator
 *
 */
public class Startup {
	public static void main(String[] args) {
		String currentURL = System.getProperty("user.dir");
		R.CURRENTURL = currentURL;
		System.setProperty("logUrl", currentURL);
		new MyFrame("У��Ƶ�ɼ����").launch();
	}

}

/**
 * ������
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
class MyFrame extends JFrame {

	public MyFrame(String name) throws HeadlessException {
		super(name);
		// ��������󱣴�
		myFrame = this;
	}

	// ��������
	private JFrame myFrame;
	// ������
	private JPanel panelLeft = new JPanel();
	// ������
	private JPanel panelRight = new JPanel();
	// ��ǰִ�г������
	private JPanel panelTop = new JPanel();
	// ��ǰִ�г�������
	private JPanel panelMain = new JPanel();
	// ִ�г��������
	private JLabel procudeIng = new JLabel();
	// �ȴ�ͼ��
	LoadingPanel glasspane = new LoadingPanel();

	public void launch() {
		// ���ڹر�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// ���ó�ʼλ�úʹ�С
				setBounds(0, 0, 1200, 700);
				// ���ò��ɸı��С
				setResizable(false);
				// ���ÿɼ�
				setVisible(true);
				// ���ò��ַ�ʽΪ��
				setLayout(null);

				// ������ -start
				panelLeft.setLayout(null);
				panelLeft.setBounds(0, 0, 150, 700);
				panelLeft.setBackground(new Color(171, 149, 94));

				// ������
				Label title = new Label("������");
				title.setBounds(30, 10, 90, 30);
				title.setFont(new Font("����", Font.BOLD, 25));
				panelLeft.add(title);

				// ���ܰ�ť
				Button btn1 = new Button("΢����Ƶall");
				btn1.setBounds(20, 50, 100, 30);
				btn1.setBackground(new Color(245, 237, 205));
				// �ɼ�΢����ȫ��С��Ƶ
				btn1.addActionListener(new FunctionButtonMonitor());
				// ����ʶ��ť
				btn1.setActionCommand("΢����Ƶall");
				panelLeft.add(btn1);

				Button btn2 = new Button("΢����Ƶkey");
				btn2.setBackground(new Color(245, 237, 205));
				btn2.setBounds(20, 110, 100, 30);
				// ΢���Ĺؼ�����Ƶ�ɼ�
				btn2.addActionListener(new FunctionButtonMonitor());
				// ����ʶ��ť
				btn2.setActionCommand("΢����Ƶkey");
				panelLeft.add(btn2);

				Button btn3 = new Button("������Ƶkey");
				btn3.setBackground(new Color(245, 237, 205));
				btn3.setBounds(20, 170, 100, 30);
				// ������Ƶ�Ĺؼ��ֲɼ�
				btn3.addActionListener(new FunctionButtonMonitor());
				btn3.setActionCommand("������Ƶkey");
				panelLeft.add(btn3);

				add(panelLeft);
				// ������ -end

				// ������-start
				panelRight.setBounds(150, 0, 1050, 700);
				panelRight.setLayout(null);

				// ��ǰִ�г������-start
				panelTop.add(procudeIng);
				panelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelTop.setBackground(new Color(223, 199, 126));
				panelTop.setBounds(0, 0, 1050, 30);
				panelRight.add(panelTop);
				// ��ǰִ�г������-end

				// ��ǰִ�г������-start
				panelMain.setBounds(0, 30, 1050, 670);
				panelMain.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelMain.setBackground(new Color(245, 237, 205));

				panelMain.setBounds(0, 30, 1050, 670);
				// panelMain.add(new TextArea(R.WELCOME));
				panelRight.add(panelMain);
				add(panelRight);
				paintAll(getGraphics());

				// ��ǰִ�г������-end
				// ������-end
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				glasspane.setBounds(50, 50, (dimension.width), (dimension.height) / 2);
				myFrame.setGlassPane(glasspane);
				glasspane.setText("���ݼ����У����Ժ󡣡���");
				glasspane.start();// ��ʼ��������Ч��
			}
		});
		t.start();

	}

	/**
	 * ��Ӧ���ܰ�ť����¼�
	 * 
	 * @author Administrator
	 *
	 */
	class FunctionButtonMonitor implements ActionListener {
		// ΢���ؼ�����������
		JLabel labelKey;
		JTextField textFieldKey;
		JButton buttonSearch;
		JPanel panelKey;
		JPanel panelCheckBox;
		JPanel panelChoose;
		JPanel panelButton;
		// ΢��������Ƶҳ��
		JTextArea textAreaConsole;
		// ������
		JScrollPane scroll;

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			procudeIng.setText("��ǰִ�й���---" + actionCommand);
			paintAll(getGraphics());

			if (actionCommand.equals("΢����Ƶall")) {
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				scroll = new JScrollPane(textAreaConsole);
				// ���������򴰿ڵĻ���
				panelMain.removeAll();
				panelChoose.removeAll();
				panelMain.setLayout(null);
				panelChoose.setBounds(10, 10, 1025, 40);
				panelChoose.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelChoose.add(new JLabel("��ѡ��ɼ����ݣ�", 0));
				// panelChoose.add(new JCheckBox("������Ϣ"),1);
				panelChoose.add(new JCheckBox("������Ϣ"), 1);
				panelChoose.add(new JCheckBox("ת����Ϣ"), 2);
				panelChoose.setBackground(new Color(245, 237, 205));
				panelMain.add(panelChoose);
				JButton buttonSure = new JButton("��ʼ�ɼ�");
				buttonSure.addActionListener(new WeiboAllSureMonitor());
				panelChoose.add(buttonSure, 3);
				scroll.setBounds(10, 60, 1025, 570);
				panelMain.add(scroll);
				paintAll(getGraphics());
			} else if (actionCommand.equals("΢����Ƶkey")) {
				labelKey = new JLabel("���������ؼ��֣�");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("����");
				panelKey = new JPanel();
				panelCheckBox = new JPanel();
				panelChoose = new JPanel();
				panelButton = new JPanel();
				// ���������򴰿ڵĻ���
				panelMain.removeAll();
				panelMain.setLayout(null);
				panelKey.setBounds(10, 10, 1025, 40);
				panelKey.setBackground(new Color(245, 237, 205));
				panelCheckBox.setBounds(10, 50, 1025, 450);
				panelCheckBox.setBackground(new Color(245, 237, 205));
				panelChoose.setBounds(10, 500, 1025, 40);
				panelChoose.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelChoose.setBackground(new Color(245, 237, 205));
				panelButton.setBounds(10, 540, 60, 40);
				panelButton.setBackground(new Color(245, 237, 205));
				textFieldKey.setColumns(50);
				textFieldKey.addKeyListener(new WeibokeySearchMonitor());
				buttonSearch.addActionListener(new WeibokeySearchMonitor());
				panelKey.add(labelKey);
				panelKey.add(textFieldKey);
				panelKey.add(buttonSearch);
				panelMain.add(panelKey);
				panelMain.add(panelCheckBox);
				panelMain.add(panelChoose);
				panelMain.add(panelButton);
				paintAll(getGraphics());

			} else if (actionCommand.equals("������Ƶkey")) {
				labelKey = new JLabel("����ؼ��֣�");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("�ɼ�");
				panelKey = new JPanel();
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				scroll = new JScrollPane(textAreaConsole);
				// ���������򴰿ڵĻ���
				panelMain.removeAll();
				panelMain.setLayout(null);
				panelKey.setBounds(10, 10, 1025, 40);
				panelKey.setBackground(new Color(245, 237, 205));
				textFieldKey.setColumns(50);
				textFieldKey.addKeyListener(new XiGuaKeySearchMonitor());
				buttonSearch.addActionListener(new XiGuaKeySearchMonitor());
				scroll.setBounds(10, 60, 1025, 570);
				panelKey.add(labelKey, 0);
				panelKey.add(textFieldKey, 1);
				panelKey.add(new JLabel("�Ƿ�ɼ�������Ϣ��", 2));
				panelKey.add(new JCheckBox("������Ϣ"), 3);
				panelKey.add(buttonSearch, 4);
				panelMain.add(panelKey);
				panelMain.add(scroll);
				paintAll(getGraphics());
			}
		}

		/**
		 * ������Ƶģ�飬��Ӧ������ť
		 * 
		 * @author Administrator
		 *
		 */
		class XiGuaKeySearchMonitor extends KeyAdapter implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				search();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}

			private void search() {

			}

		}

		/**
		 * ΢����Ƶkeyģ�飬��Ӧ������ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeibokeySearchMonitor extends KeyAdapter implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}

		}

		/**
		 * ΢����Ƶkeyģ�飬ѡ����ɺ���Ӧȷ�ϰ�ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboKeySureMonitor implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

			}

		}

		/**
		 * ΢����Ƶallģ�飬��Ӧ�ɼ���ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboAllSureMonitor implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		}

	}

}
