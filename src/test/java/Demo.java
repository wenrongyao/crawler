import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Demo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Demo() {
		super("Title");
		NewPanel p = new NewPanel();
		this.getContentPane().add(p); // �������ӵ�JFrame��
		this.setSize(596, 298); // ��ʼ���ڵĴ�С
		this.setLocationRelativeTo(null); // ���ô��ھ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Demo();
	}

	class NewPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NewPanel() {

		}

		public void paintComponent(Graphics g) {
			int x = 0, y = 0;
			ImageIcon icon = new ImageIcon("background.jpg");// 003.jpg�ǲ���ͼƬ����Ŀ�ĸ�Ŀ¼��
			g.drawImage(icon.getImage(), x, y, getSize().width, getSize().height, this);// ͼƬ���Զ�����
			// g.drawImage(icon.getImage(), x, y,this);//ͼƬ�����Զ�����
		}
	}

}