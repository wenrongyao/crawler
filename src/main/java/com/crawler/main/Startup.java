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
		new MyFrame("校视频采集软件").launch();
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
				// panelMain.add(new TextArea(R.WELCOME));
				panelRight.add(panelMain);
				add(panelRight);
				paintAll(getGraphics());

				// 当前执行程序标题-end
				// 主窗口-end
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				glasspane.setBounds(50, 50, (dimension.width), (dimension.height) / 2);
				myFrame.setGlassPane(glasspane);
				glasspane.setText("数据加载中，请稍后。。。");
				glasspane.start();// 开始动画加载效果
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

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			procudeIng.setText("当前执行功能---" + actionCommand);
			paintAll(getGraphics());

			if (actionCommand.equals("微博视频all")) {
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				scroll = new JScrollPane(textAreaConsole);
				// 画出主程序窗口的画面
				panelMain.removeAll();
				panelChoose.removeAll();
				panelMain.setLayout(null);
				panelChoose.setBounds(10, 10, 1025, 40);
				panelChoose.setLayout(new FlowLayout(FlowLayout.LEFT));
				panelChoose.add(new JLabel("请选择采集内容：", 0));
				// panelChoose.add(new JCheckBox("基础信息"),1);
				panelChoose.add(new JCheckBox("评论信息"), 1);
				panelChoose.add(new JCheckBox("转发信息"), 2);
				panelChoose.setBackground(new Color(245, 237, 205));
				panelMain.add(panelChoose);
				JButton buttonSure = new JButton("开始采集");
				buttonSure.addActionListener(new WeiboAllSureMonitor());
				panelChoose.add(buttonSure, 3);
				scroll.setBounds(10, 60, 1025, 570);
				panelMain.add(scroll);
				paintAll(getGraphics());
			} else if (actionCommand.equals("微博视频key")) {
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
				labelKey = new JLabel("输入关键字：");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("采集");
				panelKey = new JPanel();
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
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
		 * 微博视频all模块，响应采集按钮
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
