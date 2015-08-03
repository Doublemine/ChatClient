package from;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.ClientDataCenter;
import thread.MsgConServerThread;
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
	private Socket loginsocket;
	// private Socket msgsocket;
	private ChatForm chaForm;

	private void InitGUI() {

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

		registButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				toRegistForm();
			}
		});

		/**
		 * 登录事件
		 */
		loginButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				new SendRegInfoToServerThread(loginsocket, userName.getText(),
						new String(userPasswd.getPassword()), "#LOGIN#")
						.start();
				/* 发送登录信息完毕，等待响应 */
				ExecutorService pool = Executors.newCachedThreadPool();
				Future<Boolean> future = pool
						.submit(new ReceiveLoginStatThread(loginsocket));
				pool.shutdown();

				try {
					if (future.get()) {// 登录成功
						new MsgConServerThread().start();
						toChatForm();
					} else {// 登录失败

						JOptionPane.showMessageDialog(null, "密码或者用户名无效！",
								"悲催的少年", JOptionPane.ERROR_MESSAGE);

					}
				} catch (HeadlessException e1) {

					e1.printStackTrace();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				} catch (ExecutionException e1) {

					e1.printStackTrace();
				}
			}
		});

	}

	public LoginForm() {
		new MsgConServerThread().start();
		new RegConServerThread().start();
		this.setTitle("聊天系统登录");
		this.setSize(500, 350);
		this.setLocation(300, 200);
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		InitGUI();
		this.repaint();
		this.loginsocket = ClientDataCenter.regUserSocket;
		// this.msgsocket = ClientDataCenter.conServerSocket;

	}

	public void toRegistForm() {
		registForm = new RegistForm();
		registForm.setVisible(true);
		this.dispose();

	}

	private void toChatForm() {
		chatForm = new ChatForm();
		chatForm.setVisible(true);
		this.dispose();

	}

}
