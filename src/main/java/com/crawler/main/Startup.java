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
 * ui界面，用swing实现
 * 
 * @author Administrator
 *
 */
public class Startup {
	public static void main(String[] args) {
		String currentURL = System.getProperty("user.dir");
		R.CURRENTURL = currentURL;
		System.setProperty("logUrl", currentURL);
		new MyFrame("小视频采集软件").launch();
	}

}

/**
 * 主窗口
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
class MyFrame extends JFrame {

	public MyFrame(String name) throws HeadlessException {
		super(name);
		// 将自身对象保存
		myFrame = this;
	}

	// 保存自身
	private JFrame myFrame;
	// 功能栏
	private JPanel panelLeft = new JPanel();
	// 主窗口
	private JPanel panelRight = new JPanel();
	// 当前执行程序标题
	private JPanel panelTop = new JPanel();
	// 当前执行程序内容
	private JPanel panelMain = new JPanel();
	// 执行程序的名字
	private JLabel procudeIng = new JLabel();
	// 等待图标
	LoadingPanel glasspane = new LoadingPanel();

	public void launch() {
		// 窗口关闭
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// 设置初始位置和大小
				setBounds(0, 0, 1200, 700);
				// 设置不可改变大小
				setResizable(false);
				// 设置可见
				setVisible(true);
				// 设置布局方式为空
				setLayout(null);

				// 功能栏 -start
				panelLeft.setLayout(null);
				panelLeft.setBounds(0, 0, 150, 700);
				panelLeft.setBackground(new Color(171, 149, 94));

				// 功能栏
				Label title = new Label("功能栏");
				title.setBounds(30, 10, 90, 30);
				title.setFont(new Font("黑体", Font.BOLD, 25));
				panelLeft.add(title);

				// 功能按钮
				Button btn1 = new Button("微博视频all");
				btn1.setBounds(20, 50, 100, 30);
				btn1.setBackground(new Color(245, 237, 205));
				// 采集微博的全部小视频
				btn1.addActionListener(new FunctionButtonMonitor());
				// 用来识别按钮
				btn1.setActionCommand("微博视频all");
				panelLeft.add(btn1);

				Button btn2 = new Button("微博视频key");
				btn2.setBackground(new Color(245, 237, 205));
				btn2.setBounds(20, 110, 100, 30);
				// 微博的关键字视频采集
				btn2.addActionListener(new FunctionButtonMonitor());
				// 用来识别按钮
				btn2.setActionCommand("微博视频key");
				panelLeft.add(btn2);

				Button btn3 = new Button("西瓜视频key");
				btn3.setBackground(new Color(245, 237, 205));
				btn3.setBounds(20, 170, 100, 30);
				// 西瓜视频的关键字采集
				btn3.addActionListener(new FunctionButtonMonitor());
				btn3.setActionCommand("西瓜视频key");
				panelLeft.add(btn3);

				add(panelLeft);
				// 功能栏 -end

				// 主窗口-start
				panelRight.setBounds(150, 0, 1050, 700);
				panelRight.setLayout(null);

				// 当前执行程序标题-start
				panelTop.add(procudeIng);
				panelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelTop.setBackground(new Color(223, 199, 126));
				panelTop.setBounds(0, 0, 1050, 30);
				panelRight.add(panelTop);
				// 当前执行程序标题-end

				// 当前执行程序标题-start
				panelMain.setBounds(0, 30, 1050, 670);
				panelMain.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelMain.setBackground(new Color(245, 237, 205));
				panelMain.setBounds(0, 30, 1050, 670);
				panelRight.add(panelMain);
				add(panelRight);
				paintAll(getGraphics());
				// 当前执行程序标题-end
				// 主窗口-end

				// 开始动画加载效果，预先加载，不可见，点击时可见。
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				glasspane.setBounds(0, 0, (dimension.width), (dimension.height) / 2);
				myFrame.setGlassPane(glasspane);
				glasspane.setText("数据加载中，请稍后。。。");
				glasspane.start();

				// 默认加载微博视频all
				FunctionButtonMonitor fbm = new FunctionButtonMonitor();
				fbm.actionPerformed(null);
			}
		});
		t.start();

	}

	/**
	 * 响应功能按钮点击事件
	 * 
	 * @author Administrator
	 *
	 */
	class FunctionButtonMonitor implements ActionListener {
		// 微博关键字搜索画面
		JLabel labelKey;
		JTextField textFieldKey;
		JButton buttonSearch;
		JPanel panelKey;
		JPanel panelCheckBox;
		JPanel panelChoose;
		JPanel panelButton;
		// 微博所有视频页面
		JTextArea textAreaConsole;
		// 滚动栏
		JScrollPane scroll;
		// 开始采集和停止采集公用的线程
		Thread collectionThread;
		// 线程停止标志
		boolean collectionQuitFlag = false;
		// 点击继续采集时，不需要重新获取，视频链接
		boolean collectionContinue = false;

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = "微博视频all";
			if (null != e) {
				actionCommand = e.getActionCommand();
			}
			procudeIng.setText("当前执行功能---" + actionCommand);
			paintAll(getGraphics());

			if (actionCommand.equals("微博视频all")) {
				// 将动画效果设置为不可见
				glasspane.isVisible(false);
				paintAll(getGraphics());
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				textAreaConsole.setEditable(false);
				scroll = new JScrollPane(textAreaConsole);
				// 画出主程序窗口的画面
				panelMain.removeAll();
				panelChoose.removeAll();
				panelMain.setLayout(null);
				panelChoose.setBounds(10, 10, 1025, 40);
				panelChoose.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelChoose.add(new JLabel("请选择采集内容：", 0));
				panelChoose.add(new JCheckBox("评论信息"), 1);
				panelChoose.add(new JCheckBox("转发信息"), 2);
				panelChoose.setBackground(new Color(245, 237, 205));
				panelMain.add(panelChoose);
				JButton buttonConfig = new JButton("设置采集参数");
				buttonConfig.addActionListener(new WeiboAllConfigMonitor());
				panelChoose.add(buttonConfig, 3);
				JButton buttonSure = new JButton("开始采集");
				buttonSure.addActionListener(new WeiboAllSureMonitor());
				panelChoose.add(buttonSure, 4);
				JButton buttonStop = new JButton("停止采集");
				buttonStop.addActionListener(new WeiboAllStopMonitor());
				panelChoose.add(buttonStop, 5);
				scroll.setBounds(10, 60, 1025, 570);
				panelMain.add(scroll);
				paintAll(getGraphics());
			} else if (actionCommand.equals("微博视频key")) {
				// 将动画效果设置为不可见
				glasspane.isVisible(false);
				paintAll(getGraphics());
				labelKey = new JLabel("输入搜索关键字：");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("搜索");
				panelKey = new JPanel();
				panelCheckBox = new JPanel();
				panelChoose = new JPanel();
				panelButton = new JPanel();
				// 画出主程序窗口的画面
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

			} else if (actionCommand.equals("西瓜视频key")) {
				// 将动画效果设置为不可见
				glasspane.isVisible(false);
				paintAll(getGraphics());
				labelKey = new JLabel("输入关键字：");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("采集");
				panelKey = new JPanel();
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				textAreaConsole.setEditable(false);
				scroll = new JScrollPane(textAreaConsole);
				// 画出主程序窗口的画面
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
				panelKey.add(new JLabel("是否采集评论信息：", 2));
				panelKey.add(new JCheckBox("评论信息"), 3);
				panelKey.add(buttonSearch, 4);
				panelMain.add(panelKey);
				panelMain.add(scroll);
				paintAll(getGraphics());
			}
		}

		/**
		 * 西瓜视频模块，响应搜索按钮
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
		 * 微博视频key模块，响应搜索按钮
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
		 * 微博视频key模块，选择完成后，响应确认按钮
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
		 * 微博视频all模块，点击配置参数，弹框出现一个window，输出配置参数
		 * 
		 * @author WRY
		 *
		 */
		class WeiboAllConfigMonitor implements ActionListener {
			// 对话框
			JDialog jDialog;
			// 采集总数
			JTextField jtfCount;
			// 定时任务，设置间隔
			JTextField jtfTime;

			@Override
			public void actionPerformed(ActionEvent e) {
				jDialog = new JDialog();
				jDialog.setBounds(500, 130, 250, 150);
				jDialog.setTitle("采集参数配置");
				jDialog.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 13));
				jDialog.add(new JLabel("采集条数"));
				jtfCount = new JTextField(15);
				jtfCount.setDocument(new NumberTextField());
				jDialog.add(jtfCount);
				jDialog.add(new JLabel("定时采集"));
				jtfTime = new JTextField(15);
				jtfTime.setDocument(new NumberTextField());
				jDialog.add(jtfTime);
				JButton buttonSure = new JButton("确认");
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
				JButton buttonCancel = new JButton("取消");
				buttonCancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jDialog.setVisible(false);
					}
				});
				jDialog.add(buttonSure);
				jDialog.add(buttonCancel);
				jDialog.setResizable(false);
				// 确保弹出的窗口在其他窗口前面
				jDialog.setModal(true);
				jDialog.setVisible(true);
				jDialog.paintAll(getGraphics());
			}

		}

		/**
		 * 数字输入框
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
				// 过滤非数字
				for (int i = 0; i < s.length; i++) {
					if ((s[i] >= '0') && (s[i] <= '9')) {
						s[length++] = s[i];
					}
					// 插入内容
					super.insertString(offset, new String(s, 0, length), attr);
				}
			}
		}

		/**
		 * 微博视频all模块，响应停止按钮
		 * 
		 * @author WRY
		 *
		 */
		class WeiboAllStopMonitor implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (collectionThread != null) {
					// 将线程停止标志设置为真
					collectionQuitFlag = true;
				}
				// 继续采集标志设置为真
				collectionContinue = true;
			}

		}

		/**
		 * 微博视频all模块，响应采集按钮
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboAllSureMonitor implements ActionListener {
			private int collectionCount;

			@Override
			public void actionPerformed(ActionEvent e) {
				// 恢复执行
				collectionQuitFlag = false;
				collectionThread = new Thread(new Runnable() {

					@Override
					public void run() {
						TimerTask task = new TimerTask() {
							@Override
							public void run() {
								WeiboAllMain weiboAllMain = new WeiboAllMain();
								String url = "http://www.weibo.com/tv";
								// 获取所有视频链接
								if(!collectionContinue) {
									weiboAllMain.getVideoUrls(url);
								}
								BerkeleyDBService urls = new BerkeleyDBService(R.URLS);
								BerkeleyDBService visitedUrls = new BerkeleyDBService(R.VISITEDURLS);
								while (!collectionQuitFlag) {
									// 获取视频信息
									Map<String, String> urlsMap = urls.getAll();
									for (@SuppressWarnings("rawtypes")
									Entry entry : urlsMap.entrySet()) {
										// 取值和去值是原子操作
										Video video = weiboAllMain.getVideo((String) entry.getValue());
										urls.remove((String) entry.getKey());
										visitedUrls.put((String) entry.getKey(), (String) entry.getValue(), false);

										// 评论信息被选择
										JCheckBox discussChoose = (JCheckBox) panelChoose.getComponent(1);
										if (discussChoose.isSelected()) {
											List<Discuss> discussList = weiboAllMain.getDiscuss(video);
											for(Discuss discuss : discussList) {
												textAreaConsole.append(discuss.toString() + "\n");
											}
										}
										// 转发信息被选择
										JCheckBox transpondChoose = (JCheckBox) panelChoose.getComponent(2);
										if (transpondChoose.isSelected()) {
											List<Transpond> transpondList = weiboAllMain.getTranspond(video);
											for(Transpond transpond : transpondList) {
												textAreaConsole.append(transpond.toString() + "\n");
											}
										}

										// 数据库存储操作
										textAreaConsole.append(video.toString() + "\n");

										// 这边是耗时操作，每循环一次，都需要校验一下,退出标志是否改变。
										if (collectionQuitFlag) {
											break;
										}
										collectionCount += 1;
										// 如果采集数量设置了，且任务数等于采集数量，线程退出。
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
						// 没有设置定时或者，设置定时为负数，定时为没24小时执行一次
						timer.scheduleAtFixedRate(task, 0, R.TIMETASK <= 0 ? 86400000 : R.TIMETASK);
					}
				});
				collectionThread.start();
			}
		}
	}

}
