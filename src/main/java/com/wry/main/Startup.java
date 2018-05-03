package com.wry.main;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import com.wry.constant.R;
import com.wry.log.LogUtil;
import com.wry.model.Discuss;
import com.wry.model.Transpond;
import com.wry.model.Video;
import com.wry.model.VideoXiGua;
import com.wry.parser.WeiboParser;
import com.wry.parser.WeiboSearchParse;
import com.wry.parser.XiGuaParser;
import com.wry.service.BerkeleyDBService;
import com.wry.service.DiscussService;
import com.wry.service.TranspondService;
import com.wry.service.VideoService;
import com.wry.service.VideoXiGuaService;
import com.wry.util.BrowserHttpclient;
import com.wry.util.BrowserHttpclientXIGua;
import com.wry.util.BrowserPhantomjs;

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
		new MyFrame1("С��Ƶ�ɼ����").launch();
	}

}

/**
 * ������
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
class MyFrame1 extends JFrame {

	public MyFrame1(String name) throws HeadlessException {
		super(name);
	}

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

	public void launch() {
		Thread t0 = new Thread(new Runnable() {

			@Override
			public void run() {
				// ���ڹرռ�����
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						setVisible(false);
						System.exit(0);
					}
				});
			}
		});
		t0.start();

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
				
				// ����Ĭ�ϴ���
				FunctionButtonMonitor fbm = new FunctionButtonMonitor();
				fbm.actionPerformed(null);

			}
		});
		t.start();
	}

	/**
	 * ��������ѡ�ť
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
		// JScrollPane ScrollPaneCheckBox = new JScrollPane(panelCheckBox);
		JPanel panelChoose;
		JPanel panelButton;
		// ΢��������Ƶҳ��
		JTextArea textAreaConsole;
		// ������
		JScrollPane scroll;
		// ������
		JPanel contentPane;

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = "΢����Ƶall";
			if(e != null) {
				actionCommand = e.getActionCommand();
			}
			procudeIng.setText("��ǰִ��---" + actionCommand);
			paintAll(getGraphics());

			if (actionCommand.equals("΢����Ƶall")) {
				panelChoose = new JPanel();
				textAreaConsole = new JTextArea();
				scroll = new JScrollPane(textAreaConsole);
				R.TABLENAME = "_weiboall";
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
				panelChoose.add(buttonConfig);
				JButton buttonSure = new JButton("��ʼ�ɼ�");
				buttonSure.addActionListener(new WeiboAllSureMonitor());
				panelChoose.add(buttonSure, 4);
				JButton buttonStop = new JButton("ֹͣ�ɼ�");
				panelChoose.add(buttonStop,5);
				scroll.setBounds(10, 60, 1025, 570);
				panelMain.add(scroll);
				paintAll(getGraphics());
			} else if (actionCommand.equals("΢����Ƶkey")) {
				labelKey = new JLabel("���������ؼ��֣�");
				textFieldKey = new JTextField();
				buttonSearch = new JButton("����");
				panelKey = new JPanel();
				panelCheckBox = new JPanel();
				// JScrollPane ScrollPaneCheckBox = new JScrollPane(panelCheckBox);
				panelChoose = new JPanel();
				panelButton = new JPanel();
				R.TABLENAME = "_weibokey";
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
				textFieldKey.addKeyListener(new WeiboakeySearchMonitor());
				buttonSearch.addActionListener(new WeiboakeySearchMonitor());
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
				R.TABLENAME = "_xiguakey";
				// ���������򴰿ڵĻ���
				panelMain.removeAll();
				panelMain.setLayout(null);
				panelKey.setBounds(10, 10, 1025, 40);
				panelKey.setBackground(new Color(245, 237, 205));
				textFieldKey.setColumns(50);
				textFieldKey.addKeyListener(new XiGuakeySearchMonitor());
				buttonSearch.addActionListener(new XiGuakeySearchMonitor());
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
		 * ������Ƶģ�飬��Ӧ����������ť
		 * 
		 * @author Administrator
		 *
		 */
		class XiGuakeySearchMonitor extends KeyAdapter implements ActionListener {

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
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							String keyWorld = URLEncoder.encode(textFieldKey.getText().trim(), "utf-8");
							if ("".equals(keyWorld)) {
								textFieldKey.setText("������ؼ���");
								return;
							}
							textAreaConsole.append("�ɼ���ʼ��ǰ�����������Ҫһ��ʱ�䣬�����ĵȴ���\n");
							String url = "";
							List<VideoXiGua> videoList = new ArrayList<>();
							if (!"".equals(textFieldKey.getText().trim())) {
								int offset = 0;
								BrowserHttpclientXIGua browser = null;
								String soundCode = "";
								XiGuaParser parser = new XiGuaParser();
								while (true) {
									try {
										url = "https://security.snssdk.com/video/app/search/search_content/?from=video&keyword="
												+ keyWorld
												+ "&iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%252C236847%252C246276%252C273539%252C271584%252C271962%252C261931%252C217285%252C249822%252C268198%252C227960%252C249631%252C273774%252C252883%252C255490%252C252979%252C257292%252C249824%252C249819%252C249827%252C249830%252C273215%252C264627%252C272859%252C265856%252C274099%252C150151&device_type=vivo%2BY51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517749692385&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&keyword_type=video&search_sug=1&forum=1&no_outsite_res=0&as=A165FA5727A05BE&cp=5A7780E53B4E7E1&count=10&cur_tab=2&format=json&offset="
												+ offset + "&search_id=&m_tab=&en_qc=1";
										browser = new BrowserHttpclientXIGua(url);
										soundCode = browser.httpGet();
										if (!parser.parserVideoList(soundCode, videoList, textAreaConsole)) {
											break;
										}
										if (videoList.size() <= 0) {
											textAreaConsole.append("��Ƶ�����ڣ�������ؼ��� \n");
										}
										offset += 10;
									} catch (Exception e) {
										LogUtil.LogInfo("������Ƶ������Ƶidʱ����" + e.getMessage());
									}
								}
								VideoXiGuaService videoXiGuaService = new VideoXiGuaService();
								DiscussService discussService = new DiscussService();
								for (VideoXiGua video : videoList) {
									try {
										// url = "https://is.snssdk.com/video/app/article/full/15/1/" +
										// video.getId_str()
										// + "/" + video.getId_str()
										// +
										// "/1/0/?iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%2C236847%2C246276%2C273539%2C271584%2C271962%2C261931%2C217285%2C249822%2C268198%2C227960%2C249631%2C273774%2C252883%2C255490%2C252979%2C257292%2C249824%2C249819%2C249827%2C249830%2C273215%2C264627%2C272859%2C274099%2C150151&ssmix=a&device_type=vivo+Y51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517751215103&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&ts=1517751212&as=a275f017ec6aea0b576406&mas=008974dff05e53b841ddbbf5f83f8fef5aa30e8d165cb964e9a9c9";
										// browser = new BrowserHttpclientXIGua(url);
										// soundCode = browser.httpGet();
										// parser.parserVideoList(soundCode, video);
										JCheckBox checkBox = (JCheckBox) panelKey.getComponent(3);
										boolean discussChoose = checkBox.isSelected();
										// textAreaConsole.append(video + "\n");
										if (!discussChoose) {
											videoXiGuaService.save(video);
										}
										if (discussChoose) {
											List<Discuss> discussList = new ArrayList<>();
											offset = 0;
											while (true) {
												try {
													url = "https://is.snssdk.com/article/v2/tab_comments/?group_id="
															+ video.getId_str() + "&item_id=" + video.getId_str()
															+ "&aggr_type=1&count=20&offset=" + offset
															+ "&tab_index=0&iid=25377382466&device_id=47753959844&ac=wifi&channel=vivo&aid=32&app_name=video_article&version_code=639&version_name=6.3.9&device_platform=android&ab_version=271217%2C236847%2C246276%2C273539%2C271584%2C217285%2C249822%2C268198%2C227960%2C249631%2C273774%2C252883%2C255490%2C252979%2C257292%2C249824%2C249819%2C249827%2C249830%2C273215%2C264627%2C272859%2C275010%2C274099%2C150151&ssmix=a&device_type=vivo+Y51e&device_brand=vivo&language=zh&os_api=22&os_version=5.1.1&uuid=862063039571455&openudid=3375f0196b3f6b72&manifest_version_code=239&resolution=540*960&dpi=240&update_version_code=6392&_rticket=1517810590116&fp=zlTqLSPMcWs5FlP5LSU1FYwIFlK1&ts=1517810586&as=a2a5ff578a19daf3a78704&mas=000903536cf86832899fe09fe571a8b3c0da1f2e7211d50b0d7757";
													browser = new BrowserHttpclientXIGua(url);
													soundCode = browser.httpGet();
													if (!parser.parserDisscussList(soundCode, discussList)) {
														break;
													}
													offset += 20;
												} catch (Exception e) {
													LogUtil.LogInfo("������Ƶ������Ƶ����ʱ����" + e.getMessage());
												}
											}
											if (discussList.size() == 0) {
												videoXiGuaService.save(video);
											}
											for (Discuss discuss : discussList) {
												// System.out.println(discuss);
												discuss.setVideoXigua(video);
												discussService.save(discuss);
												textAreaConsole.append(discuss + "\n");
											}
											textAreaConsole.append("�ɼ���" + discussList.size() + "����������\n");
										}
									} catch (Exception e) {
										LogUtil.LogInfo("������Ƶ������Ƶ��Ϣʱ����" + e.getMessage());
									}
								}
								textAreaConsole.append("һ���ɼ���" + videoList.size() + "����Ƶ����\n");
							} else {
								textFieldKey.setText("������ؼ���");
								return;
							}

						} catch (UnsupportedEncodingException e) {
						} catch (Exception e) {
							LogUtil.LogInfo("���ϳ��������쳣" + e.getMessage());
						}
					}
				});
				t.start();

			}

		}

		/**
		 * ΢����Ƶkeyģ�飬��Ӧ΢��������ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboakeySearchMonitor extends KeyAdapter implements ActionListener {
			List<Video> videoList;
			String soundCode;
			WeiboSearchParse parse;
			String url;

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}

			private void search() {
				try {
					String keyWorld = URLEncoder.encode(textFieldKey.getText().trim(), "utf-8");
					url = "";
					if (!"".equals(textFieldKey.getText().trim())) {
						url = "http://s.weibo.com/weibo/" + keyWorld
								+ "&xsort=hot&hasvideo=1&searchvideo=1&Refer=STopic_box";
					} else {
						textFieldKey.setText("������ؼ���");
						return;
					}
					contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
					panelKey.add(contentPane);
					contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					final JProgressBar progressBar = new JProgressBar();
					progressBar.setStringPainted(true);
					new Thread() {
						public void run() {
							for (int i = 0; i <= 100; i++) {
								try {
									Thread.sleep(300);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								progressBar.setValue(i);
							}
							progressBar.setString("������ɣ�");
						}
					}.start();
					contentPane.add(progressBar);
					contentPane.setVisible(true);
					paintAll(getGraphics());
				} catch (UnsupportedEncodingException e) {
				}

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							BrowserPhantomjs bs = new BrowserPhantomjs();
							String soundCode = bs.httpGet(url);

							// ���û���һ��ѡ��ѡ�ʣ�µ�videoList���û���Ҫ�ɼ�����Ƶ��Ϣ��
							parse = new WeiboSearchParse();
							videoList = parse.parseVideos(soundCode);
							if (videoList.size() == 0) {
								return;
							}
							panelCheckBox.removeAll();
							panelCheckBox.setLayout(new GridLayout(videoList.size(), 1));
							for (int i = 0; i < videoList.size(); i++) {
								JCheckBox checkbox = new JCheckBox(videoList.get(i).getTitle());
								panelCheckBox.add(checkbox, i);
							}
							panelChoose.removeAll();
							panelButton.removeAll();
							panelChoose.add(new JLabel("��ѡ��ɼ����ݣ�", 0));
							panelChoose.add(new JCheckBox("������Ϣ"), 1);
							panelChoose.add(new JCheckBox("ת����Ϣ"), 2);
							JButton buttonSure = new JButton("ȷ��");
							panelButton.add(buttonSure);
							buttonSure.addActionListener(new SureMonitor());
							paintAll(getGraphics());

						} catch (Exception e1) {
						}
					}
				});
				t.start();
			}

			/**
			 * ΢����Ƶkeyģ�飬����ѡ����ȷ�ϰ�ť
			 * 
			 * @author Administrator
			 *
			 */
			class SureMonitor implements ActionListener {
				// ѡ����Ϣ
				boolean disscussChoose = false;
				boolean transpondChoose = false;

				@SuppressWarnings("static-access")
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						LogUtil.LogInfo("����С��Ƶ��ʼ-key");
						// ������
						int counter = 0;
						JCheckBox checkBox1 = (JCheckBox) panelChoose.getComponent(1);
						disscussChoose = checkBox1.isSelected();
						checkBox1 = (JCheckBox) panelChoose.getComponent(2);
						transpondChoose = checkBox1.isSelected();
						// װ�û�ѡ��õ���Ƶ
						List<Video> videoList3 = new ArrayList<>();
						for (int i = 0; i < panelCheckBox.getComponentCount(); i++) {
							JCheckBox checkBox = (JCheckBox) panelCheckBox.getComponent(i);
							if (checkBox.isSelected()) {
								videoList3.add(videoList.get(i));
							}
						}

						BrowserHttpclient browserHttpclient = null;
						WeiboAllSureMonitor weiboAllSureMonitor = new WeiboAllSureMonitor();
						VideoService videoService = new VideoService();
						DiscussService discussService = new DiscussService();
						TranspondService transpondService = new TranspondService();
						for (Video video : videoList3) {
							counter++;
							String soundUrl = video.getSoundUrl();
							try {
								browserHttpclient = new BrowserHttpclient(soundUrl);
							} catch (Exception e1) {
							}
							soundCode = browserHttpclient.httpGet();
							parse.parseVideo(soundCode, video);
							if (disscussChoose) {
								try {
									List<Discuss> discussList = weiboAllSureMonitor.getDiscuss(video);
									for (Discuss discuss : discussList) {
										discuss.setVideo(video);
										discussService.save(discuss);
									}
								} catch (Exception e1) {
								}
							}
							if (transpondChoose) {
								try {
									List<Transpond> transpondList = weiboAllSureMonitor.getTranspond(video);
									for (Transpond transpond : transpondList) {
										transpondService.save(transpond);
									}
								} catch (Exception e1) {
								}
							}
							if (!disscussChoose && !transpondChoose) {
								videoService.save(video);
							}
						}
						new JOptionPane().showMessageDialog(null, "�ɹ�����" + counter + "����Ƶ�������Ѵ������ݿ⡣", "��ʾ��",
								JOptionPane.WARNING_MESSAGE);
						LogUtil.LogInfo("����С��Ƶ����-key��һ��������" + counter + "����Ƶ");
					} catch (Exception e1) {
						LogUtil.LogInfo("����С��Ƶ����-key����" + e1.getMessage());
					}
				}

			}
		}

		/**
		 * ΢����Ƶallģ�飬��Ӧ΢����Ƶall�Ŀ�ʼ�ɼ���ť
		 * 
		 * @author Administrator
		 *
		 */
		class WeiboAllSureMonitor implements ActionListener {
			// ѡ����Ϣ
			boolean disscussChoose = false;
			boolean transpondChoose = false;
			// ����3���߳�
			private ExecutorService fixedThreadPool = null;
			// ������¼�������ӻ���Ƶ�ĸ���
			private int counter = 0;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				JCheckBox checkBox = (JCheckBox) panelChoose.getComponent(1);
				disscussChoose = checkBox.isSelected();
				checkBox = (JCheckBox) panelChoose.getComponent(2);
				transpondChoose = checkBox.isSelected();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						// ΢��С��Ƶ�Ķ�������
						String url = "https://www.weibo.com/tv";
						// ��ȡ��������
						getTitleLinks(url);
						// ��ȡ��Ƶ����
						getVideoLinks();
						// ��ȡ��Ƶ����
						getVideoMessage();
					}
				});
				t.start();

			}

			/**
			 * ��ȡ��������
			 */
			private void getTitleLinks(String url) {
				// ����������
				counter = 0;
				LogUtil.LogInfo("�����������ӿ�ʼ");
				textAreaConsole.append("�����������ӿ�ʼ \n");

				try {
					BrowserHttpclient bs = new BrowserHttpclient(url);
					String soundCode = bs.httpGet();
					WeiboParser weiboParser = new WeiboParser();
					Map<String, String> titleMap = weiboParser.parserTitleLinks(soundCode);
					BerkeleyDBService ts = new BerkeleyDBService(R.TITLE);
					for (Map.Entry<String, String> entity : titleMap.entrySet()) {
						ts.put(entity.getKey(), entity.getValue(), false);
						counter += 1;
					}
				} catch (Exception e) {
					LogUtil.LogInfo("url�쳣" + e.getMessage());
					LogUtil.LogInfo("��������������������");
				}
				LogUtil.LogInfo("��������������������,һ��������" + counter + "����������");
				textAreaConsole.append("��������������������,һ��������" + counter + "���������� \n");
			}

			/**
			 * ��ȡÿ����Ƶ������
			 */
			private void getVideoLinks() {
				// ����������
				counter = 0;
				LogUtil.LogInfo("����С��Ƶ���ӿ�ʼ");
				textAreaConsole.append("����С��Ƶ���ӿ�ʼ \n");
				textAreaConsole.append("���ڽ������ӣ����Եȣ� \n");
				// ����3���߳�
				fixedThreadPool = Executors.newFixedThreadPool(3);

				BerkeleyDBService ts = new BerkeleyDBService(R.TITLE);
				// map��key������ value������
				Map<String, String> map = ts.getAll_map();
				if (map.size() > 0) {
					for (Entry<String, String> entry : map.entrySet()) {
						fixedThreadPool.execute(new Runnable() {

							@Override
							public void run() {
								String dbName = entry.getKey().toLowerCase() + "_db";
								BerkeleyDBService cs = new BerkeleyDBService(dbName);
								String url = entry.getValue();
								try {
									BrowserHttpclient bs = new BrowserHttpclient(url);
									String soundCode = bs.httpGet();
									WeiboParser weiboParser = new WeiboParser();
									Map<String, String> videoLinksMap = weiboParser.parserVideoLinks(soundCode,
											entry.getKey().toLowerCase());
									for (Map.Entry<String, String> entity : videoLinksMap.entrySet()) {
										cs.put(entity.getKey(), entity.getValue(), false);
										counter += 1;
									}
								} catch (Exception e) {
									LogUtil.LogInfo("С��Ƶ���������쳣" + e.getMessage());
								}
							}
						});

					}
				} else {
					LogUtil.LogInfo("����С��Ƶ�����쳣������ͷ���Ӳ�����");
				}
				// ����һ��˳��رգ�ִ����ǰ�ύ�����񣬵�������������
				fixedThreadPool.shutdown();
				try {
					fixedThreadPool.awaitTermination(100, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					LogUtil.LogInfo("�̹߳ر�ʧ��" + e.getMessage());
				}
				LogUtil.LogInfo("��������������������,һ��������" + counter + "��С��Ƶ����");
				textAreaConsole.append("��������������������,һ��������" + counter + "��С��Ƶ���� \n");
			}

			// ����С��Ƶ���� ---��ʼ
			/**
			 * ��ȡС��Ƶ��Ϣ
			 */
			private void getVideoMessage() {
				// ����������
				counter = 0;
				LogUtil.LogInfo("����С��Ƶ���ݿ�ʼ");
				textAreaConsole.append("����С��Ƶ���ݿ�ʼ \n");
				// ����3���߳�
				fixedThreadPool = Executors.newFixedThreadPool(3);
				BerkeleyDBService ts = new BerkeleyDBService(R.TITLE);
				List<String> titleList = ts.getAll_list();
				if (titleList.size() > 0) {
					for (String dbName : titleList) {
						fixedThreadPool.execute(new Runnable() {
							BerkeleyDBService cs = new BerkeleyDBService(dbName.toLowerCase() + "_db");
							BerkeleyDBService vs = new BerkeleyDBService(R.VISITED);
							DiscussService discussService = new DiscussService();
							TranspondService transpondService = new TranspondService();

							@SuppressWarnings({ "null", "static-access" })
							@Override
							public void run() {
								// �õ�ĳһ��������Ƶ���ݿ����������
								Map<String, String> linkMap = cs.getAll_map();
								if (linkMap.size() > 0) {
									for (Entry<String, String> entry : linkMap.entrySet()) {
										boolean status = true;
										// ��������ÿ��С��Ƶҳ��
										try {
											BrowserHttpclient bs = new BrowserHttpclient(entry.getValue());
											String html = bs.httpGet();
											if (html == null && html.equals("")) {
												LogUtil.LogInfo("��Ƶ����ҳ��δ�ɹ�����");
												return;
											}
											// һ�ν���ÿ��С��Ƶҳ��
											WeiboParser weiboParser = new WeiboParser();
											Video video = weiboParser.parserVideo(html);
											// ��Ƶ����
											video.setUrl(entry.getValue());
											// ��Ƶ����
											video.setType(dbName.toLowerCase());
											textAreaConsole.append(video.toString() + "\n");
											// ��ȡÿ����Ƶ������
											if (disscussChoose) {
												List<Discuss> discussList = getDiscuss(video);
												for (Discuss discuss : discussList) {
													discuss.setVideo(video);
													// ���ü�����������video
													discussService.save(discuss);
													textAreaConsole.append(discuss.toString() + "\n");
												}
											}
											// ����Ƶ��Ϣ�������ݿ�
											// VideoManage videoManage = new VideoManage();
											// videoManage.save(video);
											// ����ÿ����Ƶ�Ĵ���·��
											if (transpondChoose) {
												List<Transpond> transpondList = getTranspond(video);
												for (Transpond transpond : transpondList) {
													transpondService.save(transpond);
													textAreaConsole.append(transpond.toString() + "\n");
												}
											}
											if (!disscussChoose && !transpondChoose) {
												VideoService videoService = new VideoService();
												videoService.save(video);
											}
										} catch (Exception e) {
											try {
												Thread.sleep(180000);
												status = false;
											} catch (InterruptedException e1) {
												LogUtil.LogInfo("�̴߳�ϴ���");
											}
											LogUtil.LogInfo("���ʹ���" + e.getMessage());
										}
										if (status) {
											// �����������Ӽ����ѽ��������ݿ��
											vs.put(entry.getKey(), entry.getValue(), false);
											// �����ӴӴ������б��Ƴ�
											cs.remove(entry.getKey());
										}
									}

								} else {
									LogUtil.LogInfo("�����������������ڣ�");
								}
							}
						});
					}

				}
				// ����һ��˳��رգ�ִ����ǰ�ύ�����񣬵�������������
				fixedThreadPool.shutdown();
				try {
					fixedThreadPool.awaitTermination(3600, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					LogUtil.LogInfo("�̹߳ر�ʧ��" + e.getMessage());
				}
				LogUtil.LogInfo("����С��Ƶ��������,һ��������" + counter + "��С��Ƶ����");
				textAreaConsole.append("����С��Ƶ��������,һ��������" + counter + "��С��Ƶ����  \n");
			}

			/**
			 * ��ȡ������Ϣ
			 * 
			 * @param mid
			 * @return
			 */
			@SuppressWarnings("static-access")
			private List<Discuss> getDiscuss(Video video) throws Exception {
				List<Discuss> discussList = new ArrayList<Discuss>();
				String discussLink = "https://weibo.com/aj/v6/comment/big?ajwvr=6&id=" + video.getMid()
						+ "&from=singleWeiBo&__rnd=1512440330247";
				BrowserHttpclient bs = new BrowserHttpclient(discussLink);
				String jsonStr = bs.httpGet();
				JSONObject jsonObj = new JSONObject(jsonStr);
				jsonObj = (JSONObject) jsonObj.get("data");
				String html = jsonObj.getString("html");
				WeiboParser weiboParser = new WeiboParser();
				discussList = weiboParser.parserDiscuss(html, video.getMid());
				return discussList;
			}

			/**
			 * ��ȡ��Ƶ��ת������
			 * 
			 * @param mid
			 * @param author
			 * @return
			 */
			@SuppressWarnings("static-access")
			private List<Transpond> getTranspond(Video video) throws Exception {
				List<Transpond> transpondList = new ArrayList<Transpond>();
				// ��һ��ת��������
				String url = "https://www.weibo.com/aj/v6/mblog/info/big?ajwvr=6&id=" + video.getMid()
						+ "&__rnd=1512698545178";
				BrowserHttpclient bs = new BrowserHttpclient(url);
				String jsonStr = bs.httpGet();
				JSONObject jsonObj = new JSONObject(jsonStr);
				jsonObj = (JSONObject) jsonObj.get("data");
				String html = jsonObj.getString("html");
				WeiboParser weiboParser = new WeiboParser();
				transpondList = weiboParser.parserTranspond(html, video);
				return transpondList;
			}
			// ����С��Ƶ���� ---����
		}
	}

}
