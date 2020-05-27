package graphics;

public class LoginHandler {
	
	private LoginForm leLogin;
	private RegisterForm leRegister;
	
	public LoginForm getLeLogin() {
		return leLogin;
	}

	public void setLeLogin(LoginForm leLogin) {
		this.leLogin = leLogin;
	}

	public RegisterForm getLeRegister() {
		return leRegister;
	}

	public void setLeRegister(RegisterForm leRegister) {
		this.leRegister = leRegister;
	}

	public LoginHandler() {
		super();
		leLogin = new LoginForm(this);
		leRegister = new RegisterForm(this);
		leLogin.setVisible(true);
	}
	
	public void goBackToLogin() {
		leLogin.setVisible(true);
		leRegister.setVisible(false);
	}
	
	public void goRegister() {
		leLogin.setVisible(false);
		leRegister.setVisible(true);
	}

}
