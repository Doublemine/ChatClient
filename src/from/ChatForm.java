package from;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5098855896885694461L;
	private JTextArea chatArea;
	private JButton sendMsgButton;
	private JButton clearButton;
	private JTextArea inputArea;
	private JLabel inputTitle;
	private JLabel chatTitle;
	private Font chatFont;
	private JScrollPane chatsJScroll;
	private JScrollPane inputJScroll;

	private void GuiInit() {

		chatArea = new JTextArea();
		inputArea = new JTextArea();

		sendMsgButton = new JButton();

		inputTitle = new JLabel();
		chatTitle = new JLabel();

		chatFont = new Font("微软雅黑", NORMAL, 14);

		chatTitle = new JLabel();
		chatTitle.setText("聊天内容");
		chatTitle.setLocation(400, 5);
		chatTitle.setSize(150, 60);

		chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setFont(chatFont);
		chatArea.setBackground(new Color(184, 207, 229));
		chatsJScroll = new JScrollPane(chatArea);
		chatsJScroll.setSize(700, 400);
		chatsJScroll.setLocation(50, 50);
		chatsJScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		inputTitle = new JLabel();
		inputTitle.setText("请在下面输入聊天内容");
		inputTitle.setLocation(350, 400);
		inputTitle.setSize(150, 150);

		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputJScroll = new JScrollPane(inputArea);
		inputJScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputJScroll.setSize(700, 50);
		inputJScroll.setLocation(50, 500);

		sendMsgButton = new JButton();
		sendMsgButton.setText("发送消息");
		sendMsgButton.setSize(100, 35);
		sendMsgButton.setLocation(650, 570);

		clearButton = new JButton();
		clearButton.setText("清空");
		clearButton.setSize(100, 35);
		clearButton.setLocation(450, 570);

		this.add(chatTitle);
		this.add(chatsJScroll);
		this.add(inputTitle);
		this.add(inputJScroll);
		this.add(sendMsgButton);
		this.add(clearButton);
	}

	public ChatForm() {
		this.setTitle("公告聊天室");
		this.setSize(800, 700);
		this.setLocation(200, 10);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GuiInit();
		this.validate();
		this.repaint();

	}

}
