package from;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.ClientDataCenter;
import thread.ReceiveLoginStatThread;
import thread.RegConServerThread;
import thread.SendRegInfoToServerThread;

public class LoginForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5418325765774911412L;
	private RegistForm registForm;
	private ChatForm chatForm;
	private JTextField userName;
	private JPasswordField userPasswd;
	private JLabel usernameLabel;
	private JLabel passwdLabel;
	private JButton loginButton;
	private JButton registButton;
	// private Socket msgsocket;
	private ChatForm chaForm;
	private JOptionPane jop;
	private LoginForm loginForm;
	private Socket socket;

	private void InitGUI() {
		new RegConServerThread().start();
		loginForm = this;
		userName = new JTextField();
		userPasswd = new JPasswordField();
		usernameLabel = new JLabel();
		passwdLabel = new JLabel();
		loginButton = new JButton();
		registButton = new JButton();

		usernameLabel.setText("用户名：");
		passwdLabel.setText("密码：");
		loginButton.setText("登录账号");
		registButton.setText("注册账号");

		usernameLabel.setSize(200, 75);
		usernameLabel.setLocation(100, 50);

		passwdLabel.setSize(200, 75);
		passwdLabel.setLocation(100, 100);

		userName.setSize(200, 35);
		userName.setLocation(200, 70);

		userPasswd.setSize(200, 35);
		userPasswd.setLocation(200, 120);

		loginButton.setSize(100, 35);
		loginButton.setLocation(250, 170);

		registButton.setSize(100, 35);
		registButton.setLocation(130, 170);

		this.add(usernameLabel);
		this.add(userName);
		this.add(passwdLabel);
		this.add(userPasswd);
		this.add(registButton);
		this.add(loginButton);
		this.socket = ClientDataCenter.regUserSocket;

		// 注册按钮按下
		registButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				toRegistForm();// 转到注册界面
			}
		});
		// 登录按钮按下
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SendRegInfoToServerThread(socket,
						userName.getText().trim(), new String(userPasswd
								.getPassword()), "#LOGIN#").start();
				;
				new ReceiveLoginStatThread(socket, chaForm, jop, loginForm,
						userName.getText().trim()).start();

			}
		});
		/**
		 * 窗口关闭事件
		 */
		this.addWindowStateListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
				} catch (IOException e1) {
					System.err.println("关闭socket产生错误");
				}
			}
		});
	}

	public LoginForm() {

		this.setTitle("聊天系统登录");
		this.setSize(500, 350);
		this.setLocation(300, 200);
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		InitGUI();
		this.repaint();

	}

	/**
	 * 关闭本界面，创建并转到注册界面
	 */
	public void toRegistForm() {
		registForm = new RegistForm();
		registForm.setVisible(true);
		this.dispose();

	}

	/**
	 * 关闭本界面，创建并转到聊天界面
	 */
	// private void toChatForm() {
	// chatForm = new ChatForm();
	// chatForm.setVisible(true);
	// this.dispose();
	//
	// }

}
