package org.irods.jargon.idrop.desktop.systraygui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.auth.AuthResponse;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSFileSystem;
import org.irods.jargon.idrop.desktop.systraygui.services.IdropConfigurationService;
import org.irods.jargon.idrop.desktop.systraygui.utils.IdropPropertiesHelper;
import org.irods.jargon.idrop.exceptions.IdropException;
import org.irods.jargon.idrop.exceptions.IdropRuntimeException;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mikeconway
 */
public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private IDROPCore idropCore = null;
	public static org.slf4j.Logger log = LoggerFactory
			.getLogger(LoginDialog.class);

	public LoginDialog(final JDialog parentDialog, final IDROPCore idropCore) {
		super(parentDialog, true);
		if (idropCore == null) {
			throw new IllegalArgumentException("null idropCore");
		}
		this.idropCore = idropCore;
		initComponents();

		if (idropCore.getIdropConfig().isLoginPreset()) {
			loginUsingPreset();
		} else {
			loginNormally();
		}

		registerKeystrokeListener();
		setLocationRelativeTo(parentDialog);

	}

	private void loginNormally() {
		// predispose based on preferences
		String host = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_HOST);
		if (host != null) {
			txtHost.setText(host);
		}
		String port = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_PORT);
		if (port == null || port.isEmpty()) {
			port = "1247";
		}

		String mode = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_LOGIN_MODE);
		if (mode == null || mode.isEmpty()) {
			mode = IRODSAccount.AuthScheme.STANDARD.name();
		} else {
			comboLoginMode.setSelectedItem(mode);
		}

		txtPort.setText(port);
		String zone = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_ZONE);
		txtZone.setText(zone);
		String resource = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_RESOURCE);
		txtResource.setText(resource);
		String username = idropCore.getIdropConfig().getPropertyForKey(
				IdropConfigurationService.ACCOUNT_CACHE_USER_NAME);
		txtUserName.setText(username);
		hideAdvancedViewFields();
	}

	private void loginUsingPreset() {
		log.debug("login will use presets");
		lblHost.setVisible(false);
		txtHost.setVisible(false);
		lblPort.setVisible(false);
		txtPort.setVisible(false);
		lblZone.setVisible(false);
		txtZone.setVisible(false);
		lblResource.setVisible(false);
		txtResource.setVisible(false);
		chkAdvancedLogin.setVisible(false);
		lblLoginMode.setVisible(false);
		comboLoginMode.setVisible(false);
	}

	/**
	 * Action to take when login is initiated
	 * 
	 * @return
	 * @throws NumberFormatException
	 */
	private boolean processLogin() throws NumberFormatException {
		// validate various inputs based on whether a full login, or a uid only
		// login is indicated
		if (!idropCore.getIdropConfig().isLoginPreset()) {
			txtHost.setBackground(Color.white);
			txtPort.setBackground(Color.white);
			txtZone.setBackground(Color.white);
			txtResource.setBackground(Color.white);
			if (txtHost.getText().length() == 0) {
				txtHost.setBackground(Color.red);
			}
			if (txtPort.getText().length() == 0) {
				txtPort.setBackground(Color.red);
			} else {
				try {
					Integer.parseInt(txtPort.getText());
				} catch (Exception e) {
					txtPort.setBackground(Color.red);
				}
			}
			if (txtZone.getText().length() == 0) {
				txtZone.setBackground(Color.red);
			}
			if (txtResource.getText().length() == 0) {
				txtResource.setBackground(Color.red);
			}
		}

		txtUserName.setBackground(Color.white);
		password.setBackground(Color.white);
		if (txtUserName.getText().length() == 0) {
			txtUserName.setBackground(Color.red);
		}
		if (password.getPassword().length == 0) {
			password.setBackground(Color.red);
		}
		StringBuilder sb = new StringBuilder();
		final IRODSAccount irodsAccount;

		try {

			// validated, now try to log in
			if (idropCore.getIdropConfig().isLoginPreset()) {
				log.debug("creating account with presets");
				String presetHost = idropCore.getIdropConfig()
						.getPropertyForKey(
								IdropPropertiesHelper.LOGIN_PRESET_HOST);
				log.info("presetHost:{}", presetHost);
				int presetPort = Integer.parseInt(idropCore.getIdropConfig()
						.getPropertyForKey(
								IdropPropertiesHelper.LOGIN_PRESET_PORT));
				log.info("presetPort:{}", presetPort);
				String presetZone = idropCore.getIdropConfig()
						.getPropertyForKey(
								IdropPropertiesHelper.LOGIN_PRESET_ZONE);
				log.info("presetZone:{}", presetZone);
				String presetResource = idropCore.getIdropConfig()
						.getPropertyForKey(
								IdropPropertiesHelper.LOGIN_PRESET_RESOURCE);
				log.info("presetResource:{}", presetResource);
				sb.append('/');
				sb.append(presetZone);
				sb.append("/home/");
				sb.append(txtUserName.getText());

				if (chkGuestLogin.isSelected()) {
					irodsAccount = IRODSAccount.instanceForAnonymous(
							presetHost, presetPort, "", presetZone,
							presetResource);
				} else {
					irodsAccount = IRODSAccount.instance(presetHost,
							presetPort, txtUserName.getText(), new String(
									password.getPassword()), sb.toString(),
							presetZone, presetResource);
				}
			} else {
				sb.append('/');
				sb.append(txtZone.getText());
				sb.append("/home/");
				sb.append(txtUserName.getText());

				if (chkGuestLogin.isSelected()) {
					irodsAccount = IRODSAccount.instanceForAnonymous(txtHost
							.getText().trim(), Integer.parseInt(txtPort
							.getText().trim()), "", txtZone.getText().trim(),
							txtResource.getText().trim());
				} else {
					irodsAccount = IRODSAccount.instance(txtHost.getText()
							.trim(),
							Integer.parseInt(txtPort.getText().trim()),
							txtUserName.getText().trim(),
							new String(password.getPassword()).trim(), sb
									.toString().trim(), txtZone.getText()
									.trim(), txtResource.getText().trim());
				}
			}
		} catch (JargonException ex) {
			Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE,
					null, ex);
			MessageManager.showError(this, ex.getMessage(),
					MessageManager.TITLE_MESSAGE);
			return true;
		}

		if (comboLoginMode.getSelectedItem().toString()
				.equals(IRODSAccount.AuthScheme.PAM.name())) {
			irodsAccount.setAuthenticationScheme(IRODSAccount.AuthScheme.PAM);
		}

		IRODSFileSystem irodsFileSystem = null;

		/*
		 * getting userAO will attempt the login
		 */

		try {
			irodsFileSystem = idropCore.getIrodsFileSystem();
			AuthResponse authResponse = irodsFileSystem
					.getIRODSAccessObjectFactory().authenticateIRODSAccount(
							irodsAccount);
			idropCore.setIrodsAccount(authResponse
					.getAuthenticatedIRODSAccount());
			try {
				idropCore.getIdropConfigurationService()
						.saveLogin(irodsAccount);
			} catch (IdropException ex) {
				throw new IdropRuntimeException("error saving irodsAccount", ex);
			}
			dispose();
		} catch (JargonException ex) {
			if (ex.getMessage().indexOf("Connection refused") > -1) {
				Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE,
						null, ex);
				MessageManager.showError(this,
						"Cannot connect to the server, is it down?",
						"Login Error");

				return true;
			} else if (ex.getMessage().indexOf("Connection reset") > -1) {
				Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE,
						null, ex);
				MessageManager.showError(this,
						"Cannot connect to the server, is it down?",
						"Login Error");

				return true;
			} else if (ex.getMessage().indexOf("io exception opening socket") > -1) {
				Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE,
						null, ex);
				MessageManager.showError(this,
						"Cannot connect to the server, is it down?",
						"Login Error");

				return true;
			} else {
				Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE,
						null, ex);
				MessageManager.showError(this,
						"login error - unable to log in, or invalid user id",
						"Login Error");

				return true;
			}
		} finally {
			if (irodsFileSystem != null) {
				irodsFileSystem.closeAndEatExceptions();
			}
		}
		return false;
	}

	/**
	 * Register a listener for the enter event, so login can occur.
	 */
	private void registerKeystrokeListener() {

		KeyStroke enter = KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_ENTER, 0);
		Action enterAction = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3468144821565093455L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				processLogin();
			}
		};
		btnOK.registerKeyboardAction(enterAction, enter,
				JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlLoginInfo = new javax.swing.JPanel();
		lblHost = new javax.swing.JLabel();
		txtHost = new javax.swing.JTextField();
		lblPort = new javax.swing.JLabel();
		txtPort = new javax.swing.JTextField();
		lblZone = new javax.swing.JLabel();
		txtZone = new javax.swing.JTextField();
		lblResource = new javax.swing.JLabel();
		txtResource = new javax.swing.JTextField();
		lblUserName = new javax.swing.JLabel();
		txtUserName = new javax.swing.JTextField();
		lblPassword = new javax.swing.JLabel();
		password = new javax.swing.JPasswordField();
		jPanel1 = new javax.swing.JPanel();
		chkAdvancedLogin = new javax.swing.JCheckBox();
		chkGuestLogin = new javax.swing.JCheckBox();
		lblLoginMode = new javax.swing.JLabel();
		comboLoginMode = new javax.swing.JComboBox();
		pnlToolbar = new javax.swing.JPanel();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		lblLogin = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		pnlLoginInfo.setLayout(new java.awt.GridBagLayout());

		lblHost.setText("Host:");
		lblHost.setMaximumSize(new java.awt.Dimension(40, 14));
		lblHost.setMinimumSize(new java.awt.Dimension(30, 14));
		lblHost.setPreferredSize(null);
		lblHost.setRequestFocusEnabled(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		pnlLoginInfo.add(lblHost, gridBagConstraints);

		txtHost.setColumns(30);
		txtHost.setPreferredSize(null);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(txtHost, gridBagConstraints);

		lblPort.setText("Port:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		pnlLoginInfo.add(lblPort, gridBagConstraints);

		txtPort.setColumns(30);
		txtPort.setText("1247");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(txtPort, gridBagConstraints);

		lblZone.setText("Zone:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		pnlLoginInfo.add(lblZone, gridBagConstraints);

		txtZone.setColumns(30);
		txtZone.setPreferredSize(null);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(txtZone, gridBagConstraints);

		lblResource.setText("Resource:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		pnlLoginInfo.add(lblResource, gridBagConstraints);

		txtResource.setColumns(30);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(txtResource, gridBagConstraints);

		lblUserName.setText("User Name:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		pnlLoginInfo.add(lblUserName, gridBagConstraints);

		txtUserName.setColumns(30);
		txtUserName.setPreferredSize(null);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(txtUserName, gridBagConstraints);

		lblPassword.setText("Password:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
		pnlLoginInfo.add(lblPassword, gridBagConstraints);

		password.setColumns(30);
		password.setPreferredSize(null);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLoginInfo.add(password, gridBagConstraints);

		chkAdvancedLogin.setText("Advanced Login Settings");
		chkAdvancedLogin.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				chkAdvancedLoginActionPerformed(evt);
			}
		});
		jPanel1.add(chkAdvancedLogin);

		chkGuestLogin.setText("Login As Guest");
		chkGuestLogin.setToolTipText("Use a guest login");
		chkGuestLogin.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				chkGuestLoginActionPerformed(evt);
			}
		});
		jPanel1.add(chkGuestLogin);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 8;
		pnlLoginInfo.add(jPanel1, gridBagConstraints);

		lblLoginMode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		lblLoginMode.setText("Login Mode:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		pnlLoginInfo.add(lblLoginMode, gridBagConstraints);

		comboLoginMode.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Standard", "PAM" }));
		comboLoginMode.setToolTipText("Authentication mode used at login");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		pnlLoginInfo.add(comboLoginMode, gridBagConstraints);

		getContentPane().add(pnlLoginInfo, java.awt.BorderLayout.CENTER);

		pnlToolbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT,
				2, 5));

		btnOK.setMnemonic('L');
		btnOK.setText("Login");
		btnOK.setMaximumSize(new java.awt.Dimension(100, 100));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});
		pnlToolbar.add(btnOK);

		btnCancel.setMnemonic('c');
		btnCancel.setText("Cancel");
		btnCancel.setMaximumSize(new java.awt.Dimension(100, 100));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		pnlToolbar.add(btnCancel);

		getContentPane().add(pnlToolbar, java.awt.BorderLayout.SOUTH);

		lblLogin.setText("Please log in to your iDrop data grid");
		getContentPane().add(lblLogin, java.awt.BorderLayout.PAGE_START);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void chkAdvancedLoginActionPerformed(
			final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chkAdvancedLoginActionPerformed
		// TODO add your handling code here:
		if (chkAdvancedLogin.isSelected()) {
			showAdvancedViewFields();
		} else {
			hideAdvancedViewFields();
		}
	}// GEN-LAST:event_chkAdvancedLoginActionPerformed

	private void chkGuestLoginActionPerformed(
			final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chkGuestLoginActionPerformed
		if (chkGuestLogin.isSelected()) {
			hideForGuestLogin();
		} else {
			showWhenGuestLogin();
		}
	}// GEN-LAST:event_chkGuestLoginActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
		processLogin();
	}// GEN-LAST:event_btnOKActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
		dispose();
	}// GEN-LAST:event_btnCancelActionPerformed
		// Variables declaration - do not modify//GEN-BEGIN:variables

	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOK;
	private javax.swing.JCheckBox chkAdvancedLogin;
	private javax.swing.JCheckBox chkGuestLogin;
	private javax.swing.JComboBox comboLoginMode;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lblHost;
	private javax.swing.JLabel lblLogin;
	private javax.swing.JLabel lblLoginMode;
	private javax.swing.JLabel lblPassword;
	private javax.swing.JLabel lblPort;
	private javax.swing.JLabel lblResource;
	private javax.swing.JLabel lblUserName;
	private javax.swing.JLabel lblZone;
	private javax.swing.JPasswordField password;
	private javax.swing.JPanel pnlLoginInfo;
	private javax.swing.JPanel pnlToolbar;
	private javax.swing.JTextField txtHost;
	private javax.swing.JTextField txtPort;
	private javax.swing.JTextField txtResource;
	private javax.swing.JTextField txtUserName;
	private javax.swing.JTextField txtZone;

	// End of variables declaration//GEN-END:variables

	private void showAdvancedViewFields() {
		txtResource.setVisible(true);
		txtPort.setVisible(true);
		lblPort.setVisible(true);
		lblResource.setVisible(true);
	}

	private void hideAdvancedViewFields() {
		txtResource.setVisible(false);
		txtPort.setVisible(false);
		lblPort.setVisible(false);
		lblResource.setVisible(false);
	}

	private void hideForGuestLogin() {
		lblUserName.setVisible(false);
		txtUserName.setVisible(false);
		lblPassword.setVisible(false);
		password.setVisible(false);
		lblLoginMode.setVisible(false);
		comboLoginMode.setVisible(false);
	}

	private void showWhenGuestLogin() {
		lblUserName.setVisible(true);
		txtUserName.setVisible(true);
		lblPassword.setVisible(true);
		password.setVisible(true);
	}
}
