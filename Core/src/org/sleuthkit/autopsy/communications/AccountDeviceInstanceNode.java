/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2017 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
import org.openide.util.lookup.Lookups;
import org.sleuthkit.autopsy.casemodule.Case;
import org.sleuthkit.autopsy.datamodel.NodeProperty;
import org.sleuthkit.datamodel.AccountDeviceInstance;
import org.sleuthkit.datamodel.CommunicationsFilter;
import org.sleuthkit.datamodel.TskCoreException;

/**
 * Node to represent an Account in the AccountsBrowser
 */
class AccountDeviceInstanceNode extends AbstractNode {

    private static final Logger LOGGER = Logger.getLogger(AccountDeviceInstanceNode.class.getName());
    private final AccountDeviceInstance accountDeviceInstance;
    private final CommunicationsFilter filter;

    AccountDeviceInstanceNode(AccountDeviceInstance accountDeviceInstance, CommunicationsFilter filter) {
        super(Children.LEAF, Lookups.fixed(accountDeviceInstance));
        this.accountDeviceInstance = accountDeviceInstance;
        this.filter = filter;
        
        setName(accountDeviceInstance.getAccount().getAccountUniqueID());
        setIconBaseWithExtension("org/sleuthkit/autopsy/communications/images/"
                + AccountUtils.getIconFileName(accountDeviceInstance.getAccount().getAccountType()));
    }

    @Override
    @NbBundle.Messages({
        "AccountNode.device=Device",
        "AccountNode.accountName=Account",
        "AccountNode.accountType=Type",
        "AccountNode.messageCount=Msg Count"})
    protected Sheet createSheet() {
        Sheet s = super.createSheet();
        Sheet.Set properties = s.get(Sheet.PROPERTIES);
        if (properties == null) {
            properties = Sheet.createPropertiesSet();
            s.put(properties);
        }

        long msgCount = 0;
        try {
            msgCount = Case.getCurrentCase().getSleuthkitCase().getCommunicationsManager().getRelationshipsCount(this.filter, accountDeviceInstance);
        } catch (TskCoreException ex) {
            LOGGER.log(Level.WARNING, "Failed to get message count for account", ex); //NON-NLS
        }

        properties.put(new NodeProperty<>("type",
                Bundle.AccountNode_accountType(),
                "type",
                accountDeviceInstance.getAccount().getAccountType().getDisplayName())); // NON-NLS
       
        properties.put(new NodeProperty<>("count",
                Bundle.AccountNode_messageCount(),
                "count",
                msgCount)); // NON-NLS

        properties.put(new NodeProperty<>("device",
                Bundle.AccountNode_device(),
                "device",
                accountDeviceInstance.getDeviceId())); // NON-NLS
        return s;
    }
}
