/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obt ain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.communications;

import java.awt.CardLayout;
import org.openide.explorer.ExplorerManager;
import static org.openide.explorer.ExplorerUtils.createLookup;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.Node;;
import org.openide.util.Lookup;

/**
 * This class is a simple wrapper around a OutlineView with its own ExplorerManager.
 * 
 * This panel has the added feature of being able to hide the OutlineView and show
 * a message.
 * 
 */
public class OutlineViewPanel extends javax.swing.JPanel implements ExplorerManager.Provider, Lookup.Provider{

    private final ExplorerManager tableEm; 
    private final Lookup lookup;
    /**
     * Creates new form OutlineViewPanel
     */
    public OutlineViewPanel() {
        tableEm = new ExplorerManager();
        lookup = createLookup(tableEm, getActionMap());
        
        initComponents();
    }
    
    @Override
    public ExplorerManager getExplorerManager() {
        return tableEm;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    /**
     * Hide the OutlineView and replace with a panel with the given message.
     * 
     * @param message String message to show on the panel.
     */
    public void hideOutlineView(String message) {
        CardLayout layout = (CardLayout)this.getLayout();
        layout.show(this, "messageCard"); //NON-NLS
        messageLabel.setText(message);
    }
    
    /**
     * Hides the message panel and shows the OutlineView.
     */
    public void showOutlineView() {
        CardLayout layout = (CardLayout)this.getLayout();
        layout.show(this, "outlineCard"); //NON-NLS
    }
    
    /**
     * Returns the OutlineView instance for ease of customization.
     * 
     * @return Returns the OutlineView
     */
    public OutlineView getOutlineView() {
        return outlineView;
    }
    
    public void setNode(Node node) {
        tableEm.setRootContext(node);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        outlineView.setEnabled(enabled);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outlineView = new org.openide.explorer.view.OutlineView();
        messagePanel = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout(5, 5));

        outlineView.setPreferredSize(new java.awt.Dimension(300, 400));
        add(outlineView, "outlineCard");

        messagePanel.setLayout(new java.awt.BorderLayout());

        messageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(messageLabel, org.openide.util.NbBundle.getMessage(OutlineViewPanel.class, "OutlineViewPanel.messageLabel.text")); // NOI18N
        messageLabel.setEnabled(false);
        messagePanel.add(messageLabel, java.awt.BorderLayout.CENTER);

        add(messagePanel, "messageCard");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel messageLabel;
    private javax.swing.JPanel messagePanel;
    private org.openide.explorer.view.OutlineView outlineView;
    // End of variables declaration//GEN-END:variables

}
