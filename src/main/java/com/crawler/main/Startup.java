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
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import com.common.constant.R;
import com.weiboall.main.WeiboAllMain;
import com.weiboall.model.Discuss;
import com.weiboall.model.Transpond;
import com.weiboall.model.Video;
import com.weiboall.service.BerkeleyDBService;

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
		new MyFrame("С��Ƶ�ɼ����").launch();
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
				panelRight.add(panelMain);
				add(panelRight);
				paintAll(getGraphics());
				// ��ǰִ�г������-end
				// ������-end

				// ��ʼ��������Ч����Ԥ�ȼ��أ����ɼ������ʱ�ɼ���
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				glasspane.setBounds(0, 0, (dimension.width), (dimension.height) / 2);
				myFrame.setGlassPane(glasspane);
				glasspane.setText("���ݼ����У����Ժ󡣡���");
				glasspane.start();

				// Ĭ�ϼ���΢����Ƶall
				FunctionButtonMonitor fbm = new FunctionButtonMonitor();
				fbm.actionPerformed(null);
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
		// ��ʼ�ɼ���ֹͣ�ɼ����õ��߳�
		Thread collectionThread;
		// �߳�ֹͣ��־
		boolean collectionQuitFlag = false;
		// ��������ɼ�ʱ������Ҫ���»�ȡ����Ƶ����
		boolean collectionContinue = false;

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = "΢����Ƶall";
			if (null != e) {
				actionCommand = e.getActionCommand();
			}
			procudeIng.setText("��ǰִ�й���---" + actionCommand);
			paintAll(getGraphics());

			if (actionCommand.equals("΢����Ƶall")) {
				// ������Ч������Ϊ���ɼ�
				glasspane.isVisible(false);
				paintAll(getGraphics());
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				textAreaConsole.setEditable(false);
				scroll = new JScrollPane(textAreaConsole);
				// ���������򴰿ڵĻ���
				panelMain.removeAll();
				panelChoose.removeAll();
				panelMain.setLayout(null);
				panelChoose.setBounds(10, 10, 1025, 40);
				panelChoose.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelChoose.add(new JLabel("��ѡ��ɼ����ݣ�", 0));
				panelChoose.add(new JCheckBox("������Ϣ"), 1);
				panelChoose.add(new JCheckBox("ת����Ϣ"), 2);
				panelChoose.setBackground(new Color(245, 237, 205));
				panelMain.add(panelChoose);
				JButton buttonConfig = new JButton("���òɼ�����");
				buttonConfig.addActionListener(new WeiboAllConfigMonitor());
				panelChoose.add(buttonConfig, 3);
				JButton buttonSure = new JButton("��ʼ�ɼ�");
				buttonSure.addActionListener(new WeiboAllSureMonitor());
				panelChoose.add(buttonSure, 4);
				JButton buttonStop = new JButton("ֹͣ�ɼ�");
				buttonStop.addActionListener(new WeiboAllStopMonitor());
				panelChoose.add(buttonStop, 5);
				scroll.setBounds(10, 60, 1025, 570);
				panelMain.add(scroll);
				paintAll(getGraphics());
			} else if (actionCommand.equals("΢����Ƶkey")) {
				// ������Ч������Ϊ���ɼ�
				glasspane.isVisible(false);
				paintAll(getGraphics());
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
				// ������Ч������Ϊ���ɼ�
				glasspane.isVisible(false);
				paintAll(getGraphics());
				labelKey = new JLabel("����ؼ��֣�");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("�ɼ�");
				panelKey = new JPanel();
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				textAreaConsole.setEditable(false);
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
				search();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}

			private void search() {
				glasspane.isVisible(true);
				paintAll(getGraphics());
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
		 * ΢����Ƶallģ�飬������ò������������һ��window��������ò���
		 * 
		 * @author WRY
		 *
		 */
		class WeiboAllConfigMonitor implements ActionListener {
			// �Ի���
			JDialog jDialog;
			// �ɼ�����
			JTextField jtfCount;
			// ��ʱ�������ü��
			JTextField jtfTime;

			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog = new JDialog();
				jDialog.setBounds(500, 130, 250, 150);
				jDialog.setTitle("�ɼ���������");
				jDialog.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 13));
				jDialog.add(new JLabel("�ɼ�����"));
				jtfCount = new JTextField(15);
				jtfCount.setDocument(new NumberTextField());
				jDialog.add(jtfCount);
				jDialog.add(new JLabel("��ʱ�ɼ�"));
				jtfTime = new JTextField(15);
				jtfTime.setDocument(new NumberTextField());
				jDialog.add(jtfTime);
				JButton buttonSure = new JButton("ȷ��");
				buttonSure.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							R.COLLECTIONCOUNT = Integer.parseInt(jtfCount.getText());
						} catch (NumberFormatException e1) {
						}
						try {
							R.TIMETASK = Integer.parseInt(jtfTime.getText());
						} catch (NumberFormatException e1) {
						}
						jDialog.setVisible(false);
					}
				});
				JButton buttonCancel = new JButton("ȡ��");
				buttonCancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jDialog.setVisible(false);
					}
				});
				jDialog.add(buttonSure);
				jDialog.add(buttonCancel);
				jDialog.setResizable(false);
				// ȷ�������Ĵ�������������ǰ��
				jDialog.setModal(true);
				jDialog.setVisible(true);
				jDialog.paintAll(getGraphics());
			}

		}

		/**
		 * ���������
		 * 
		 * @author WRY
		 *
		 */
		class NumberTextField extends PlainDocument {
			public NumberTextField() {
				super();
			}

			public void insertString(int offset, String str, AttributeSet attr)
					throws javax.swing.text.BadLocationException {
				if (str == null) {
					return;
				}

				char[] s = str.toCharArray();
				int length = 0;
				// ���˷�����
				for (int i = 0; i < s.length; i++) {
					if ((s[i] >= '0') && (s[i] <= '9')) {
						s[length++] = s[i];
					}
					// ��������
					super.insertString(offset, new String(s, 0, length), attr);
				}
			}
		}

		/**
		 * ΢����Ƶallģ�飬��Ӧֹͣ��ť
		 * 
		 * @author WRY
		 *
		 */
		class WeiboAllStopMonitor implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (collectionThread != null) {
					// ���߳�ֹͣ��־����Ϊ��
					collectionQuitFlag = true;
				}
				// �����ɼ���־����Ϊ��
				collectionContinue = true;
			}

		}

		/**
		 * ΢����Ƶallģ�飬��Ӧ�ɼ���ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboAllSureMonitor implements ActionListener {
			private int collectionCount;

			@Override
			public void actionPerformed(ActionEvent e) {
				// �ָ�ִ��
				collectionQuitFlag = false;
				collectionThread = new Thread(new Runnable() {

					@Override
					public void run() {
						TimerTask task = new TimerTask() {
							@Override
							public void run() {
								WeiboAllMain weiboAllMain = new WeiboAllMain();
								String url = "http://www.weibo.com/tv";
								// ��ȡ������Ƶ����
								if(!collectionContinue) {
									weiboAllMain.getVideoUrls(url);
								}
								BerkeleyDBService urls = new BerkeleyDBService(R.URLS);
								BerkeleyDBService visitedUrls = new BerkeleyDBService(R.VISITEDURLS);
								while (!collectionQuitFlag) {
									// ��ȡ��Ƶ��Ϣ
									Map<String, String> urlsMap = urls.getAll();
									for (@SuppressWarnings("rawtypes")
									Entry entry : urlsMap.entrySet()) {
										// ȡֵ��ȥֵ��ԭ�Ӳ���
										Video video = weiboAllMain.getVideo((String) entry.getValue());
										urls.remove((String) entry.getKey());
										visitedUrls.put((String) entry.getKey(), (String) entry.getValue(), false);

										// ������Ϣ��ѡ��
										JCheckBox discussChoose = (JCheckBox) panelChoose.getComponent(1);
										if (discussChoose.isSelected()) {
											List<Discuss> discussList = weiboAllMain.getDiscuss(video);
											for(Discuss discuss : discussList) {
												textAreaConsole.append(discuss.toString() + "\n");
											}
										}
										// ת����Ϣ��ѡ��
										JCheckBox transpondChoose = (JCheckBox) panelChoose.getComponent(2);
										if (transpondChoose.isSelected()) {
											List<Transpond> transpondList = weiboAllMain.getTranspond(video);
											for(Transpond transpond : transpondList) {
												textAreaConsole.append(transpond.toString() + "\n");
											}
										}

										// ���ݿ�洢����
										textAreaConsole.append(video.toString() + "\n");

										// ����Ǻ�ʱ������ÿѭ��һ�Σ�����ҪУ��һ��,�˳���־�Ƿ�ı䡣
										if (collectionQuitFlag) {
											break;
										}
										collectionCount += 1;
										// ����ɼ����������ˣ������������ڲɼ��������߳��˳���
										if (R.COLLECTIONCOUNT > 0) {
											if (R.COLLECTIONCOUNT == collectionCount) {
												collectionQuitFlag = true;
												break;
											}
										}
									}
								}
							}
						};
						Timer timer = new Timer();
						// û�����ö�ʱ���ߣ����ö�ʱΪ��������ʱΪû24Сʱִ��һ��
						timer.scheduleAtFixedRate(task, 0, R.TIMETASK <= 0 ? 86400000 : R.TIMETASK);
					}
				});
				collectionThread.start();
			}
		}
	}

}
