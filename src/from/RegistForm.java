package from;

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
import thread.ReceiveRegStatThread;
import thread.SendRegInfoToServerThread;

public class RegistForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8046486481704395478L;
	private JTextField userName;
	private JPasswordField userPasswd1;
	private JPasswordField userPasswd2;
	private JLabel usernameLabel;
	private JLabel passwdLabel1;
	private JLabel passwdLabel2;
	private JButton resetButton;
	private JButton registButton;
	private Socket socket;
	private LoginForm loginForm;

	/**
	 * 初始化界面元素
	 */
	private void InitGUI() {

		userName = new JTextField();
		userPasswd1 = new JPasswordField();
		usernameLabel = new JLabel();
		passwdLabel1 = new JLabel();
		resetButton = new JButton();
		registButton = new JButton();
		passwdLabel2 = new JLabel();
		userPasswd2 = new JPasswordField();

		usernameLabel.setText("用户名：");
		passwdLabel1.setText("密码：");
		passwdLabel2.setText("确认密码");

		resetButton.setText("重置");
		registButton.setText("注册");

		usernameLabel.setSize(200, 75);
		usernameLabel.setLocation(100, 50);

		passwdLabel1.setSize(200, 75);
		passwdLabel1.setLocation(100, 100);

		passwdLabel2.setSize(200, 75);
		passwdLabel2.setLocation(100, 150);

		userName.setSize(200, 35);
		userName.setLocation(200, 70);

		userPasswd1.setSize(200, 35);
		userPasswd1.setLocation(200, 120);

		userPasswd2.setSize(200, 35);
		userPasswd2.setLocation(200, 170);

		resetButton.setSize(100, 35);
		resetButton.setLocation(250, 220);

		registButton.setSize(100, 35);
		registButton.setLocation(130, 220);

		this.add(usernameLabel);
		this.add(userName);
		this.add(passwdLabel1);
		this.add(passwdLabel2);
		this.add(userPasswd1);
		this.add(registButton);
		this.add(resetButton);
		this.add(userPasswd2);

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
				if (userName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "同学，你的名字呢？", "无知的少年",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (userPasswd1.getPassword().length > 5) {
					if (validationPasswd()) {
						/**
						 * 本地密码验证通过 发送加密注册信息到服务器
						 */
						new SendRegInfoToServerThread(socket, userName
								.getText(), new String(userPasswd2
								.getPassword()), "#REGINFO#").start();
						ExecutorService pool = Executors.newCachedThreadPool();
						Future<Boolean> future = pool
								.submit(new ReceiveRegStatThread(socket));
						pool.shutdown();
						try {
							if (future.get()) {// 注册成功
								Object[] options = { "登录到聊天室", "哦" };
								int choose = JOptionPane.showOptionDialog(null,
										"注册成功啦，你现在可以使用这个账号密码登录了。", "幸运的少年",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										options, options[0]);

								if (choose == 0) {
									turnToLoginForm();
								} else {
									ClearContext();
								}

							} else {// 注册失败
								JOptionPane.showMessageDialog(null,
										"注册失败了，请重试！", "悲催的少年",
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (InterruptedException e1) {

							System.err
									.println("RegistForm--InterruptedException-注册线程池错误："
											+ e.toString());
						} catch (ExecutionException e1) {

							System.err
									.println("RegistForm--ExecutionException-注册线程池错误："
											+ e.toString());

						}

					} else {// 密码不相等
						JOptionPane.showMessageDialog(null,
								"同学，密码两次输入的不一样你都不知道么？", "健忘的少年",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {// 长度太短
					JOptionPane.showMessageDialog(null, "同学，密码长度最少6位。",
							"无知的少年", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		resetButton.addMouseListener(new MouseListener() {

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
				ClearContext();

			}
		});
	}

	public RegistForm() {
		this.setTitle("账号注册");
		this.setSize(500, 350);
		this.setLocation(300, 200);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.socket = ClientDataCenter.regUserSocket;
		InitGUI();
		this.repaint();

	}

	/**
	 * 清除输入框中内容
	 */
	private void ClearContext() {

		userName.setText("");
		userPasswd1.setText("");
		userPasswd2.setText("");
	}

	/**
	 * 验证密码是否统一
	 * 
	 * @return 返回布尔值
	 */
	private boolean validationPasswd() {
		char[] ctemp1 = userPasswd1.getPassword();
		char[] ctemp2 = userPasswd2.getPassword();
		String passwd1 = new String(ctemp1);
		String passwd2 = new String(ctemp2);
		if (passwd1.equals(passwd2)) {
			return true;
		} else {
			return false;
		}
	}

	private void turnToLoginForm() {
		loginForm = new LoginForm();
		loginForm.setVisible(true);
		this.dispose();
	}
}
