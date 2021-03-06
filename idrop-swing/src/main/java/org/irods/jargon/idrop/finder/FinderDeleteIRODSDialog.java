/*
 * FinderDeleteIRODSDialog.java
 *
 * Created on Sep 4, 2010, 7:40:23 AM
 */
package org.irods.jargon.idrop.finder;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.CollectionAndDataObjectListingEntry;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.IRODSNode;
import org.irods.jargon.idrop.exceptions.IdropException;
import org.irods.jargon.idrop.exceptions.IdropRuntimeException;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author mikeconway
 */
public class FinderDeleteIRODSDialog extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2566706975162196188L;
	private final IRODSFinderTree irodsTree;
	private final IRODSNode deletedNode;
	private final List<IRODSNode> deletedNodes;
	private final IRODSFinderDialog irodsFinderDialog;
	public static org.slf4j.Logger log = LoggerFactory
			.getLogger(FinderDeleteIRODSDialog.class);

	/** Creates new form NewIRODSDirectoryDialog */
	public FinderDeleteIRODSDialog(final IRODSFinderDialog parent,
			final boolean modal, final IRODSFinderTree irodsTree,
			final IRODSNode deletedNode) {
		super(parent, modal);
		irodsFinderDialog = parent;
		this.irodsTree = irodsTree;
		this.deletedNode = deletedNode;
		deletedNodes = null;
		StringBuilder sb = new StringBuilder();
		CollectionAndDataObjectListingEntry entry = (CollectionAndDataObjectListingEntry) deletedNode
				.getUserObject();
		if (entry.getObjectType() == CollectionAndDataObjectListingEntry.ObjectType.COLLECTION) {
			sb.append(entry.getPathOrName());
		} else {
			sb.append(entry.getParentPath());
			sb.append('/');
			sb.append(entry.getPathOrName());
		}

		initialize();
		txtAreaFileToDelete.setText(sb.toString());
	}

	/** Creates new form NewIRODSDirectoryDialog */
	public FinderDeleteIRODSDialog(final IRODSFinderDialog parent,
			final boolean modal, final IRODSFinderTree irodsTree,
			final List<IRODSNode> deletedNodes) {
		super(parent, modal);
		irodsFinderDialog = parent;
		this.irodsTree = irodsTree;
		this.deletedNodes = deletedNodes;
		deletedNode = null;
		initialize();
		txtAreaFileToDelete.setText("multiple selections");
	}

	private void initialize() {
		initComponents();
		registerKeystrokeListener();
	}

	/**
	 * Register a listener for the enter event, so login can occur.
	 */
	private void registerKeystrokeListener() {

		KeyStroke enter = KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_ENTER, 0);
		Action enterAction = new AbstractAction() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					processDelete();
				} catch (IdropException ex) {
					Logger.getLogger(FinderDeleteIRODSDialog.class.getName())
							.log(Level.SEVERE, null, ex);
					throw new IdropRuntimeException(ex);
				}
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
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lblTitle = new javax.swing.JLabel();
		pnlFolderData = new javax.swing.JPanel();
		pnlCurrentParent = new javax.swing.JPanel();
		lblCurrentFolder = new java.awt.Label();
		scrollFileToDelete = new javax.swing.JScrollPane();
		txtAreaFileToDelete = new javax.swing.JTextArea();
		pnlBottom = new javax.swing.JPanel();
		btnCancel = new javax.swing.JButton();
		btnOK = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		lblTitle.setText("Please hit OK to delete the following file/folder from iRODS");
		getContentPane().add(lblTitle, java.awt.BorderLayout.NORTH);

		pnlFolderData.setLayout(new java.awt.GridLayout(0, 1));

		lblCurrentFolder.setText("File/folder to delete:");

		txtAreaFileToDelete.setColumns(20);
		txtAreaFileToDelete.setEditable(false);
		txtAreaFileToDelete.setLineWrap(true);
		txtAreaFileToDelete.setRows(5);
		scrollFileToDelete.setViewportView(txtAreaFileToDelete);

		org.jdesktop.layout.GroupLayout pnlCurrentParentLayout = new org.jdesktop.layout.GroupLayout(
				pnlCurrentParent);
		pnlCurrentParent.setLayout(pnlCurrentParentLayout);
		pnlCurrentParentLayout
				.setHorizontalGroup(pnlCurrentParentLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING,
								pnlCurrentParentLayout
										.createSequentialGroup()
										.addContainerGap(40, Short.MAX_VALUE)
										.add(lblCurrentFolder,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(scrollFileToDelete,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												413,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		pnlCurrentParentLayout
				.setVerticalGroup(pnlCurrentParentLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(pnlCurrentParentLayout
								.createSequentialGroup()
								.add(pnlCurrentParentLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(pnlCurrentParentLayout
												.createSequentialGroup()
												.add(35, 35, 35)
												.add(lblCurrentFolder,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(pnlCurrentParentLayout
												.createSequentialGroup()
												.add(22, 22, 22)
												.add(scrollFileToDelete,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pnlFolderData.add(pnlCurrentParent);

		getContentPane().add(pnlFolderData, java.awt.BorderLayout.CENTER);

		pnlBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		btnCancel.setText("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		pnlBottom.add(btnCancel);

		btnOK.setText("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});
		pnlBottom.add(btnOK);

		getContentPane().add(pnlBottom, java.awt.BorderLayout.SOUTH);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
		dispose();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnOKActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
		try {
			processDelete();
		} catch (IdropException ex) {
			Logger.getLogger(FinderDeleteIRODSDialog.class.getName()).log(
					Level.SEVERE, null, ex);
			throw new IdropRuntimeException(ex);
		}

	}// GEN-LAST:event_btnOKActionPerformed
		// Variables declaration - do not modify//GEN-BEGIN:variables

	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOK;
	private java.awt.Label lblCurrentFolder;
	private javax.swing.JLabel lblTitle;
	private javax.swing.JPanel pnlBottom;
	private javax.swing.JPanel pnlCurrentParent;
	private javax.swing.JPanel pnlFolderData;
	private javax.swing.JScrollPane scrollFileToDelete;
	private javax.swing.JTextArea txtAreaFileToDelete;

	// End of variables declaration//GEN-END:variables
	private void processDelete() throws IdropException {
		log.info("delete folder named:{}", txtAreaFileToDelete.getText());
		final FinderDeleteIRODSDialog thisDialog = this;

		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				thisDialog.setCursor(Cursor
						.getPredefinedCursor(Cursor.WAIT_CURSOR));
				(new DeleteWorker(thisDialog)).execute();
			}
		});
	}

	private void deleteASingleFile(final IRODSFileFactory irodsFileFactory,
			final IRODSNode deletedNode) throws IdropException {

		log.info("node to delete  is: {}", deletedNode);
		CollectionAndDataObjectListingEntry dataEntry = (CollectionAndDataObjectListingEntry) deletedNode
				.getUserObject();

		// dialog uses absolute path, so munge it for files
		StringBuilder sb = new StringBuilder();
		if (dataEntry.getObjectType() == CollectionAndDataObjectListingEntry.ObjectType.COLLECTION) {
			sb.append(dataEntry.getPathOrName());
		} else {
			sb.append(dataEntry.getParentPath());
			sb.append('/');
			sb.append(dataEntry.getPathOrName());
		}

		IRODSFile fileToDelete;
		try {
			fileToDelete = irodsFileFactory.instanceIRODSFile(sb.toString());
			fileToDelete.delete();

		} catch (JargonException ex) {
			Logger.getLogger(FinderDeleteIRODSDialog.class.getName()).log(
					Level.SEVERE, null, ex);
			throw new IdropException(ex);
		} finally {
			irodsFinderDialog.getIdropCore()
					.closeIRODSConnectionForLoggedInAccount();
		}

	}

	class DeleteWorker extends SwingWorker<String, Object> {

		public static final String DELETE_SUCCESSFUL = "Deletion complete";
		public static final String DELETE_ERRORS = "The deletion was not successful";
		private FinderDeleteIRODSDialog dialog;
		private String message = "";

		DeleteWorker(final FinderDeleteIRODSDialog dialog) {
			if (dialog == null) {
				throw new IllegalArgumentException("null dialog");
			}

			this.dialog = dialog;

		}

		@Override
		public String doInBackground() {

			try {

				IRODSFileFactory irodsFileFactory = irodsFinderDialog
						.getIdropCore().getIRODSFileFactoryForLoggedInAccount();
				if (deletedNode != null) {
					log.info("deleting a single node");
					deleteASingleFile(irodsFileFactory, deletedNode);
				} else if (deletedNodes != null) {
					log.info("deleting multiple nodes");
					for (IRODSNode deletedNodeEntry : deletedNodes) {
						deleteASingleFile(irodsFileFactory, deletedNodeEntry);
					}
				}
				message = DELETE_SUCCESSFUL;
			} catch (Exception ex) {
				Logger.getLogger(FinderDeleteIRODSDialog.class.getName()).log(
						Level.SEVERE, null, ex);

				throw new IdropRuntimeException(ex);
			} finally {
				irodsFinderDialog.getIdropCore().closeAllIRODSConnections();
			}

			return null;
		}

		@Override
		protected void done() {
			dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			final IRODSFinderOutlineModel irodsFileSystemModel = (IRODSFinderOutlineModel) irodsTree
					.getModel();
			try {
				if (deletedNode != null) {
					irodsFileSystemModel.notifyFileShouldBeRemoved(deletedNode);
				} else if (deletedNodes != null) {
					for (IRODSNode deletedNode : deletedNodes) {
						irodsFileSystemModel
								.notifyFileShouldBeRemoved(deletedNode);
					}
				}
			} catch (IdropException ex) {
				Logger.getLogger(FinderDeleteIRODSDialog.class.getName()).log(
						Level.SEVERE, null, ex);
				throw new IdropRuntimeException(ex);
			}
			dialog.dispose();

		}
	}
}
