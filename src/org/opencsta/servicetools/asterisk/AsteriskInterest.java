/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.servicetools.asterisk;

import org.asteriskjava.manager.event.ManagerEvent;

/**
 *
 * @author cm
 */
public interface AsteriskInterest {
    public void AsteriskManagerEventReceived(ManagerEvent me) ;

}
