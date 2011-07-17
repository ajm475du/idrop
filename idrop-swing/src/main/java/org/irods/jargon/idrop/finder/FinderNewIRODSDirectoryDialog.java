

/*
 * NewIRODSDirectoryDialog.java
 *
 * Created on Sep 3, 2010, 9:52:12 AM
 */
package org.irods.jargon.idrop.finder;

import org.irods.jargon.idrop.desktop.systraygui.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import org.irods.jargon.core.query.CollectionAndDataObjectListingEntry;
import org.irods.jargon.idrop.desktop.systraygui.services.IRODSFileService;
import org.irods.jargon.idrop.desktop.systraygui.utils.TreeUtils;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.IRODSNode;
import org.irods.jargon.idrop.desktop.systraygui.viscomponents.IRODSTree;
import org.irods.jargon.idrop.exceptions.IdropException;
import org.irods.jargon.idrop.exceptions.IdropRuntimeException;
import org.slf4j.LoggerFactory;

/**
 * Dialog to gather a new directory name
 * 
 * @author mikeconway
 */
public class FinderNewIRODSDirectoryDialog extends javax.swing.JDialog {

   private final IRODSFinderDialog irodsFinderDialog;
    private String parentDirectory = "";
     private final IRODSFinderTree irodsTree;
    private final IRODSNode parentNode;
    public static org.slf4j.Logger log = LoggerFactory.getLogger(FinderNewIRODSDirectoryDialog.class);

    public String getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(final String parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    /** Creates new form NewIRODSDirectoryDialog */
    public FinderNewIRODSDirectoryDialog(final IRODSFinderDialog parent, final boolean modal,
            final String parentDirectory, final IRODSFinderTree stagingViewTree,
            final IRODSNode parentNode) {
        super(parent, modal);
        this.irodsFinderDialog = parent;
        this.parentDirectory = parentDirectory;
        this.irodsTree = stagingViewTree;
        this.parentNode = parentNode;
        initComponents();
        txtAreaCurrentParent.setText(this.parentDirectory);
        registerKeystrokeListener();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        pnlFolderData = new javax.swing.JPanel();
        pnlCurrentParent = new javax.swing.JPanel();
        lblCurrentParent = new java.awt.Label();
        scrollCurrentParent = new javax.swing.JScrollPane();
        txtAreaCurrentParent = new javax.swing.JTextArea();
        lblNewDiretoryName = new java.awt.Label();
        txtNewFolder = new javax.swing.JTextField();
        pnlBottom = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create New Folder Dialog");
        setName("NewParentDialog"); // NOI18N

        lblTitle.setText("Please enter a name for the new folder");
        getContentPane().add(lblTitle, java.awt.BorderLayout.NORTH);

        pnlFolderData.setLayout(new java.awt.GridLayout(0, 1));

        lblCurrentParent.setText("Current parent directory:");

        txtAreaCurrentParent.setColumns(20);
        txtAreaCurrentParent.setEditable(false);
        txtAreaCurrentParent.setLineWrap(true);
        txtAreaCurrentParent.setRows(5);
        txtAreaCurrentParent.setFocusable(false);
        scrollCurrentParent.setViewportView(txtAreaCurrentParent);

        lblNewDiretoryName.setText("New folder name:");

        txtNewFolder.setToolTipText("A name for the new folder underneath the displayed parent");

        org.jdesktop.layout.GroupLayout pnlCurrentParentLayout = new org.jdesktop.layout.GroupLayout(pnlCurrentParent);
        pnlCurrentParent.setLayout(pnlCurrentParentLayout);
        pnlCurrentParentLayout.setHorizontalGroup(
            pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlCurrentParentLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .add(pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(lblCurrentParent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblNewDiretoryName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(txtNewFolder)
                    .add(scrollCurrentParent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCurrentParentLayout.setVerticalGroup(
            pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlCurrentParentLayout.createSequentialGroup()
                .add(58, 58, 58)
                .add(pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scrollCurrentParent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblCurrentParent, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCurrentParentLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtNewFolder, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblNewDiretoryName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        lblNewDiretoryName.getAccessibleContext().setAccessibleName("New directory name:");

        pnlFolderData.add(pnlCurrentParent);

        getContentPane().add(pnlFolderData, java.awt.BorderLayout.CENTER);

        pnlBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlBottom.add(btnCancel);

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        pnlBottom.add(btnOK);

        getContentPane().add(pnlBottom, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }// GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        processNew();
    }// GEN-LAST:event_btnOKActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private java.awt.Label lblCurrentParent;
    private java.awt.Label lblNewDiretoryName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlCurrentParent;
    private javax.swing.JPanel pnlFolderData;
    private javax.swing.JScrollPane scrollCurrentParent;
    private javax.swing.JTextArea txtAreaCurrentParent;
    private javax.swing.JTextField txtNewFolder;
    // End of variables declaration//GEN-END:variables
    private void processNew() {
        // add the new folder to irods, add to the tree, and scroll the tree
        // into view

        if (txtNewFolder.getText().isEmpty()) {
            txtNewFolder.setBackground(Color.red);
            MessageManager.showError(this, "please enter a new folder name", MessageManager.TITLE_MESSAGE);
            return;
        }

        final FinderNewIRODSDirectoryDialog thisDialog = this;

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    log.info("adding new folder named:{}",
                            txtNewFolder.getText());
                    thisDialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    IRODSFileService irodsFileService;
                    try {
                        irodsFileService = new IRODSFileService(irodsFinderDialog.getIdropCore().getIrodsAccount(), irodsFinderDialog.getIdropCore().getIrodsFileSystem());
                    } catch (IdropException ex) {
                        Logger.getLogger(
                                FinderNewIRODSDirectoryDialog.class.getName()).log(
                                Level.SEVERE, null, ex);
                        throw new IdropRuntimeException(ex);
                    } finally {
                        thisDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(parentDirectory);
                    sb.append('/');
                    sb.append(txtNewFolder.getText());
                    String newDirPath = sb.toString();

                    boolean created = irodsFileService.createNewFolder(newDirPath);

                    irodsTree.getModel();

                    if (!created) {
                        log.info("could not create new folder in:{}",
                                newDirPath);
                        MessageManager.showError(thisDialog, "directory could not be created", MessageManager.TITLE_MESSAGE);
                        thisDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }

                    // directory created, add to tree and show as first node
                    CollectionAndDataObjectListingEntry entry = new CollectionAndDataObjectListingEntry();
                    entry.setObjectType(CollectionAndDataObjectListingEntry.ObjectType.COLLECTION);
                    entry.setParentPath(parentDirectory);
                    entry.setPathOrName(newDirPath);
                    IRODSNode newNode = new IRODSNode(entry, irodsFinderDialog.getIdropCore().getIrodsAccount(), irodsFinderDialog.getIdropCore().getIrodsFileSystem(), irodsFinderDialog.getIrodsTree());
                    log.info("inserting node at 0");
                    if (parentNode.isCached()) {
                        parentNode.insert(newNode, parentNode.getChildCount());
                    } else {
                        parentNode.forceReloadOfChildrenOfThisNode();
                    }

                    try {
                        TreePath path = FinderUtils.buildTreePathForIrodsAbsolutePath(
                                irodsFinderDialog.getIrodsTree(), entry.getParentPath());
                        irodsFinderDialog.getIrodsTree().collapsePath(path);
                        irodsFinderDialog.getIrodsTree().expandPath(path);
                    } catch (IdropException ex) {
                        Logger.getLogger(
                                FinderNewIRODSDirectoryDialog.class.getName()).log(
                                Level.SEVERE, null, ex);
                        throw new IdropRuntimeException(ex);
                    } finally {
                        thisDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }

                    MessageManager.showMessage(thisDialog, "new folder created", MessageManager.TITLE_MESSAGE);

                    thisDialog.dispose();
                } catch (IdropException ex) {
                    Logger.getLogger(FinderNewIRODSDirectoryDialog.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IdropRuntimeException(ex);
                }
            }
        });
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
                processNew();
            }
        };
        btnOK.registerKeyboardAction(enterAction, enter,
                JComponent.WHEN_IN_FOCUSED_WINDOW);

    }
}
