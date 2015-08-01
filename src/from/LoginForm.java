package from;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5418325765774911412L;
	private RegistForm registForm;
	private JTextField userName;
	private JPasswordField userPasswd;
	private JLabel usernameLabel;
	private JLabel passwdLabel;
	private JButton loginButton;
	private JButton registButton;

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

	public void toRegistForm() {
		this.setVisible(false);
		registForm = new RegistForm();
		registForm.setVisible(true);

	}

}
