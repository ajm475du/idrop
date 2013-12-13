/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.irods.jargon.idrop.desktop.systraygui;

import static org.irods.jargon.idrop.desktop.systraygui.TransferDashboardDialog.log;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.irods.jargon.conveyor.core.ConveyorBusyException;
import org.irods.jargon.conveyor.core.ConveyorExecutionException;
import org.irods.jargon.idrop.desktop.systraygui.utils.TransferInformationMessageBuilder;
import org.irods.jargon.idrop.desktop.systraygui.utils.TransferInformationMessageBuilder.AttemptType;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.DashboardAttempt;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.DashboardLayoutService;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.TransferAttemptTableModel;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.TransferDashboardLayout;
import org.irods.jargon.idrop.exceptions.IdropRuntimeException;
import org.irods.jargon.transfer.dao.domain.Transfer;
import org.irods.jargon.transfer.dao.domain.TransferAttempt;
import org.irods.jargon.transfer.dao.domain.TransferStatusEnum;
import org.openide.util.Exceptions;
import org.slf4j.LoggerFactory;

/**
 * @author Mike
 */
public class TransferDashboardDialog extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4865008170726715901L;
	public static org.slf4j.Logger log = LoggerFactory
			.getLogger(TransferDashboardDialog.class);
	protected Transfer transfer;
	private final IDROPCore idropCore;
	private MyPanel myPanel;

	public void setTransferAttemptDetails(final String details) {
		final TransferDashboardDialog dialog = this;

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				dialog.lblTransferAttemptDetails.setText(details);
			}
		});

	}

	/**
	 * Creates new form TransferDashboardDialog
	 */
	public TransferDashboardDialog(final javax.swing.JDialog parent,
			final Transfer transfer, final IDROPCore idropCore) {
		super(parent, true);

		if (transfer == null) {
			throw new IllegalArgumentException("null transfer");
		}

		this.idropCore = idropCore;
		try {
			this.transfer = idropCore.getConveyorService()
					.getQueueManagerService()
					.initializeGivenTransferByLoadingChildren(transfer);
		} catch (ConveyorExecutionException ex) {
			MessageManager.showError(this, ex.getMessage());

			throw new IdropRuntimeException(ex);
		}

		initComponents();
		initData();

		ListSelectionModel listSelectionModel = jTableAttempts
				.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new SharedListSelectionHandler(this));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        toolBarTop = new javax.swing.JToolBar();
        btnRemoveSelected = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnCancel = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnRestartSelected = new javax.swing.JButton();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnResubmitSelected = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jSeparator3 = new javax.swing.JToolBar.Separator();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnRefresh = new javax.swing.JButton();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        pnlInfo = new javax.swing.JPanel();
        pnlTransferDetails = new javax.swing.JPanel();
        lblTransferDetails = new javax.swing.JLabel();
        pnlTransferAttemptDetails = new javax.swing.JPanel();
        lblTransferAttemptDetails = new javax.swing.JLabel();
        transferTabs = new javax.swing.JTabbedPane();
        pnlDashboard = new javax.swing.JPanel();
        pnlTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAttempts = new javax.swing.JTable();
        pnlBottom = new javax.swing.JPanel();
        bntClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 800));

        pnlTop.setLayout(new java.awt.BorderLayout());

        toolBarTop.setFloatable(false);
        toolBarTop.setRollover(true);

        btnRemoveSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_191_circle_minus.png"))); // NOI18N
        btnRemoveSelected.setMnemonic('d');
        btnRemoveSelected.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRemoveSelected.text")); // NOI18N
        btnRemoveSelected.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRemoveSelected.toolTipText")); // NOI18N
        btnRemoveSelected.setFocusable(false);
        btnRemoveSelected.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveSelected.setMaximumSize(new java.awt.Dimension(50, 50));
        btnRemoveSelected.setMinimumSize(new java.awt.Dimension(50, 50));
        btnRemoveSelected.setPreferredSize(new java.awt.Dimension(50, 50));
        btnRemoveSelected.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSelectedActionPerformed(evt);
            }
        });
        toolBarTop.add(btnRemoveSelected);
        toolBarTop.add(filler6);

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_175_stop.png"))); // NOI18N
        btnCancel.setMnemonic('l');
        btnCancel.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnCancel.text")); // NOI18N
        btnCancel.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnCancel.toolTipText")); // NOI18N
        btnCancel.setFocusable(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setMaximumSize(new java.awt.Dimension(50, 50));
        btnCancel.setMinimumSize(new java.awt.Dimension(50, 50));
        btnCancel.setPreferredSize(new java.awt.Dimension(50, 50));
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        toolBarTop.add(btnCancel);
        toolBarTop.add(filler7);

        btnRestartSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_085_repeat.png"))); // NOI18N
        btnRestartSelected.setMnemonic('t');
        btnRestartSelected.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRestartSelected.text")); // NOI18N
        btnRestartSelected.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRestartSelected.toolTipText")); // NOI18N
        btnRestartSelected.setFocusable(false);
        btnRestartSelected.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRestartSelected.setMaximumSize(new java.awt.Dimension(50, 50));
        btnRestartSelected.setMinimumSize(new java.awt.Dimension(50, 50));
        btnRestartSelected.setPreferredSize(new java.awt.Dimension(50, 50));
        btnRestartSelected.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRestartSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartSelectedActionPerformed(evt);
            }
        });
        toolBarTop.add(btnRestartSelected);
        toolBarTop.add(filler8);

        btnResubmitSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_434_redo.png"))); // NOI18N
        btnResubmitSelected.setMnemonic('b');
        btnResubmitSelected.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnResubmitSelected.text")); // NOI18N
        btnResubmitSelected.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnResubmitSelected.toolTipText")); // NOI18N
        btnResubmitSelected.setFocusable(false);
        btnResubmitSelected.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnResubmitSelected.setMaximumSize(new java.awt.Dimension(50, 50));
        btnResubmitSelected.setMinimumSize(new java.awt.Dimension(50, 50));
        btnResubmitSelected.setPreferredSize(new java.awt.Dimension(50, 50));
        btnResubmitSelected.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnResubmitSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResubmitSelectedActionPerformed(evt);
            }
        });
        toolBarTop.add(btnResubmitSelected);
        toolBarTop.add(filler9);
        toolBarTop.add(jSeparator3);
        toolBarTop.add(filler10);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_081_refresh.png"))); // NOI18N
        btnRefresh.setMnemonic('f');
        btnRefresh.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRefresh.text")); // NOI18N
        btnRefresh.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setMaximumSize(new java.awt.Dimension(50, 50));
        btnRefresh.setMinimumSize(new java.awt.Dimension(50, 50));
        btnRefresh.setPreferredSize(new java.awt.Dimension(50, 50));
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        toolBarTop.add(btnRefresh);
        toolBarTop.add(filler11);
        toolBarTop.add(filler13);

        pnlTop.add(toolBarTop, java.awt.BorderLayout.NORTH);

        pnlInfo.setLayout(new java.awt.BorderLayout());

        lblTransferDetails.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.lblTransferDetails.text")); // NOI18N
        pnlTransferDetails.add(lblTransferDetails);

        pnlInfo.add(pnlTransferDetails, java.awt.BorderLayout.CENTER);

        lblTransferAttemptDetails.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.lblTransferAttemptDetails.text")); // NOI18N
        pnlTransferAttemptDetails.add(lblTransferAttemptDetails);

        pnlInfo.add(pnlTransferAttemptDetails, java.awt.BorderLayout.CENTER);

        pnlTop.add(pnlInfo, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlTop, java.awt.BorderLayout.NORTH);

        pnlDashboard.setPreferredSize(new java.awt.Dimension(700, 400));
        pnlDashboard.setLayout(new java.awt.GridLayout(1, 0));
        transferTabs.addTab(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.pnlDashboard.TabConstraints.tabTitle"), pnlDashboard); // NOI18N

        pnlTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlTableComponentShown(evt);
            }
        });
        pnlTable.setLayout(new java.awt.GridLayout(1, 0));

        jTableAttempts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableAttempts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableAttempts);

        pnlTable.add(jScrollPane1);

        transferTabs.addTab(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.pnlTable.TabConstraints.tabTitle"), pnlTable); // NOI18N

        getContentPane().add(transferTabs, java.awt.BorderLayout.CENTER);

        pnlBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        bntClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/irods/jargon/idrop/desktop/systraygui/images/glyphicons_193_circle_ok.png"))); // NOI18N
        bntClose.setMnemonic('l');
        bntClose.setText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.bntClose.text")); // NOI18N
        bntClose.setToolTipText(org.openide.util.NbBundle.getMessage(TransferDashboardDialog.class, "TransferDashboardDialog.bntClose.toolTipText")); // NOI18N
        bntClose.setFocusable(false);
        bntClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bntClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bntClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCloseActionPerformed(evt);
            }
        });
        pnlBottom.add(bntClose);

        getContentPane().add(pnlBottom, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btnRemoveSelectedActionPerformed(
			final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveSelectedActionPerformed
		if (transfer != null) {
			try {
				idropCore.getConveyorService().getQueueManagerService()
						.deleteTransferFromQueue(transfer);
			} catch (ConveyorBusyException ex) {
				Exceptions.printStackTrace(ex);
			} catch (ConveyorExecutionException ex) {
				Exceptions.printStackTrace(ex);
			}
		}
	}// GEN-LAST:event_btnRemoveSelectedActionPerformed

	private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
		if (transfer != null) {
			try {
				idropCore.getConveyorService().getQueueManagerService()
						.cancelTransfer(transfer.getId());
			} catch (ConveyorBusyException ex) {
				log.error("Error restarting transfer: {}", ex.getMessage());
				MessageManager
						.showError(
								this,
								"Transfer Queue Manager is currently busy. Please try again later.",
								MessageManager.TITLE_MESSAGE);
			} catch (ConveyorExecutionException ex) {
				String msg = "Error cancelling transfer. ";
				log.error(msg + " {}", ex.getMessage());
				MessageManager.showError(this, msg,
						MessageManager.TITLE_MESSAGE);
			}
		}
	}// GEN-LAST:event_btnCancelActionPerformed

	private void btnRestartSelectedActionPerformed(
			final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRestartSelectedActionPerformed
		if (transfer != null) {
			try {
				idropCore.getConveyorService().getQueueManagerService()
						.enqueueRestartOfTransferOperation(transfer.getId());
			} catch (ConveyorBusyException ex) {
				log.error("Error restarting transfer: {}", ex.getMessage());
				MessageManager
						.showError(
								this,
								"Transfer Queue Manager is currently busy. Please try again later.",
								MessageManager.TITLE_MESSAGE);
			} catch (ConveyorExecutionException ex) {
				String msg = "Error restarting transfer. Transfer may have already completed.";
				log.error(msg + " {}", ex.getMessage());
				MessageManager.showError(this, msg,
						MessageManager.TITLE_MESSAGE);
			}
		}
	}// GEN-LAST:event_btnRestartSelectedActionPerformed

	private void btnResubmitSelectedActionPerformed(
			final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResubmitSelectedActionPerformed
		if (transfer != null) {
			try {
				idropCore.getConveyorService().getQueueManagerService()
						.enqueueResubmitOfTransferOperation(transfer.getId());
			} catch (ConveyorBusyException ex) {
				log.error("Error resubmitting transfer: {}", ex.getMessage());
				MessageManager
						.showError(
								this,
								"Transfer Queue Manager is currently busy. Please try again later.",
								MessageManager.TITLE_MESSAGE);
			} catch (ConveyorExecutionException ex) {
				String msg = "Error resubmitting transfer. Transfer may have already completed.";
				log.error(msg + " {}", ex.getMessage());
				MessageManager.showError(this, msg,
						MessageManager.TITLE_MESSAGE);
			}
		}
	}// GEN-LAST:event_btnResubmitSelectedActionPerformed

	private void btnRefreshActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRefreshActionPerformed
		// do something to refresh this stuff

		if (transfer != null) {
			try {
				transfer = idropCore.getConveyorService()
						.getQueueManagerService()
						.findTransferByTransferId(transfer.getId());
				initData();
			} catch (ConveyorExecutionException ex) {
				String msg = "Error refreshing transfer.";
				log.error(msg + " {}", ex.getMessage());
				MessageManager.showError(this, msg,
						MessageManager.TITLE_MESSAGE);
			}
		}

	}// GEN-LAST:event_btnRefreshActionPerformed

	private void bntCloseActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntCloseActionPerformed
		dispose();
	}// GEN-LAST:event_bntCloseActionPerformed

	private void pnlTableComponentShown(final java.awt.event.ComponentEvent evt) {// GEN-FIRST:event_pnlTableComponentShown

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				TransferAttemptTableModel transferAttemptTableModel = new TransferAttemptTableModel(
						transfer);
				jTableAttempts.setModel(transferAttemptTableModel);
			}
		});
	}// GEN-LAST:event_pnlTableComponentShown

	public JTable getjTableAttempts() {
		return jTableAttempts;
	}

	public void setjTableAttempts(final JTable jTableAttempts) {
		this.jTableAttempts = jTableAttempts;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntClose;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveSelected;
    private javax.swing.JButton btnRestartSelected;
    private javax.swing.JButton btnResubmitSelected;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JTable jTableAttempts;
    private javax.swing.JLabel lblTransferAttemptDetails;
    private javax.swing.JLabel lblTransferDetails;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlTransferAttemptDetails;
    private javax.swing.JPanel pnlTransferDetails;
    private javax.swing.JToolBar toolBarTop;
    private javax.swing.JTabbedPane transferTabs;
    // End of variables declaration//GEN-END:variables

	private void initData() {
		log.info("initData()");
		log.info("making sure transfer attemtps are expanded...");
		lblTransferDetails.setText(TransferInformationMessageBuilder
				.buildTransferSummary(transfer));
		buildDashboardForTransfer();
	}

	private void buildDashboardForTransfer() {

		final TransferDashboardDialog dialog = this;
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				
			}
		});
	}

	public void showTransferAttemptDetailsDialog(
			final TransferAttempt transferAttempt) {
		log.info("showing transfser attempt:{}", transferAttempt);
		if (transferAttempt == null) {
			throw new IllegalArgumentException("null transferAttempt");
		}

		TransferFileListDialog transferFileListDialog = new TransferFileListDialog(
				this, transferAttempt, idropCore);
		transferFileListDialog.setVisible(true);
	}
}


