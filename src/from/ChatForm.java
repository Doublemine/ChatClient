package from;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import main.ClientDataCenter;
import thread.MsgConServerThread;
import thread.ReceiveServerMsgThread;
import thread.SendMsgToServerThread;
import util.TcpSocketUtil;

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
	private Socket socket;
	private String username;
	// 以下为在线用户列表组件
	private JScrollPane onlineuser;
	private JList userList;
	private DefaultListModel listModel;

	private void GuiInit() {

		chatArea = new JTextArea();
		inputArea = new JTextArea();

		sendMsgButton = new JButton();

		inputTitle = new JLabel();
		chatTitle = new JLabel();

		listModel = new DefaultListModel();
		userList = new JList(listModel);

		/* 以下为在线用户列表组件初始化 */
		onlineuser = new JScrollPane(userList);
		onlineuser.setBorder(new TitledBorder("在线用户"));
		onlineuser.setSize(200, 400);
		onlineuser.setLocation(0, 30);
		this.add(onlineuser);

		// chatTitle = new JLabel();
		// chatTitle.setText("聊天内容");
		// chatTitle.setLocation(300, 0);
		// chatTitle.setSize(150, 20);

		/* 以下为初始化聊天信息组件 */
		chatFont = new Font("微软雅黑", NORMAL, 14);
		chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		chatArea.setFont(chatFont);
		chatArea.setBackground(new Color(184, 207, 229));
		chatsJScroll = new JScrollPane(chatArea);
		chatsJScroll.setBorder(new TitledBorder("聊天信息"));
		chatsJScroll.setSize(340, 400);
		chatsJScroll.setLocation(250, 30);
		chatsJScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// inputTitle = new JLabel();
		// inputTitle.setText("请在下面输入聊天内容");
		// inputTitle.setLocation(350, 370);
		// inputTitle.setSize(150, 150);

		/* 以下为信息输入组件初始化 */
		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputJScroll = new JScrollPane(inputArea);
		inputJScroll.setBorder(new TitledBorder("请输入聊天内容"));
		inputJScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputJScroll.setSize(570, 60);
		inputJScroll.setLocation(10, 460);

		/* 以下为发送 信息按钮初始化 */
		sendMsgButton = new JButton();
		sendMsgButton.setText("发送消息");
		sendMsgButton.setSize(100, 35);
		sendMsgButton.setLocation(480, 520);

		/* 以下为清空消息按钮的初始化 */
		clearButton = new JButton();
		clearButton.setText("清空");
		clearButton.setSize(100, 35);
		clearButton.setLocation(300, 520);

		this.add(chatTitle);
		this.add(chatsJScroll);
		this.add(inputTitle);
		this.add(inputJScroll);
		this.add(sendMsgButton);
		this.add(clearButton);

		// 发送按钮按下
		sendMsgButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputArea.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null,
							"一开始你发送空白信息来刷屏，其实我是，是拒绝的！请自觉！", "低素质少年",
							JOptionPane.ERROR_MESSAGE);
				} else {
					new SendMsgToServerThread(socket, inputArea.getText()
							.trim()).start();
					clearinput();
				}

			}
		});

		// 清空按钮按下
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearinput();// 清空输入框中的文本内容
			}
		});

		inputArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {// 回车键发送
					if (inputArea.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null,
								"一开始你发送空白信息来刷屏，其实我是，是拒绝的！请自觉！", "低素质少年",
								JOptionPane.ERROR_MESSAGE);
					} else {
						new SendMsgToServerThread(socket, inputArea.getText()
								.trim()).start();
						clearinput();
					}
				}

			}
		});

		this.addWindowStateListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					TcpSocketUtil
							.sendStringToServer(socket, "#CLIENTCLOSE#MSG");
					socket.close();
					ClientDataCenter.conServerSocket = null;
					ClientDataCenter.regUserSocket = null;
					listModel.removeAllElements();

				} catch (IOException e1) {

					System.err.println("关闭socket出现错误" + e1.toString());
				}
				// System.exit(0);
			}
		});
		this.socket = ClientDataCenter.conServerSocket;
	}

	public ChatForm(String username) {
		this.username = username;
		new MsgConServerThread(username).start();
		this.setTitle("公告聊天室:" + username);
		this.setSize(600, 600);
		this.setLocation(200, 10);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GuiInit();
		this.validate();
		this.repaint();
		new ReceiveServerMsgThread(socket, chatArea, listModel).start();// 启动接收服务器消息线程

	}

	private void clearinput() {
		this.inputArea.setText("");
	}
}
