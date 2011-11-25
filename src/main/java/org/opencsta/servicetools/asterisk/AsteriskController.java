/*
This file is part of Open CSTA.

    Open CSTA is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Open CSTA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Open CSTA.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.opencsta.servicetools.asterisk;

import org.apache.log4j.*;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.live.ManagerCommunicationException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.EventTimeoutException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.response.ManagerResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import org.asteriskjava.manager.ResponseEvents;
import org.asteriskjava.manager.action.QueueAddAction;
import org.asteriskjava.manager.action.QueueRemoveAction;
import org.asteriskjava.manager.action.QueueStatusAction;

/**
 * 
 * @author chrismylonas
 */
public class AsteriskController implements ManagerEventListener {

	/**
	 * 
	 */
	protected static Logger alog = Logger.getLogger(AsteriskController.class);

	/**
	 * 
	 */
	private ManagerConnectionFactory factory;

	/**
	 * 
	 */
	private ManagerConnection managerConnection;

	/**
	 * 
	 */
	private DefaultAsteriskServer asteriskserver;

	/**
	 * 
	 */
	static String asteriskserverip;

	/**
	 * 
	 */
	private AsteriskInterest parent;

	/**
	 * 
	 */
	private boolean loggedIn;

	/**
	 * 
	 */
	private String astManUser;

	/**
	 * 
	 */
	private String astManPword;

	/**
	 * @param _ipaddy
	 */
	public AsteriskController(String _ipaddy) {
		this.parent = null;
		this.asteriskserverip = _ipaddy;
	}

	/**
	 * @param _parent
	 * @param _ipaddy
	 */
	@Deprecated
	public AsteriskController(AsteriskInterest _parent, String _ipaddy) {
		this.parent = _parent;
		this.asteriskserverip = _ipaddy;
	}

	/**
	 * @param _parent
	 * @param _ipaddy
	 * @param _amu
	 * @param _amp
	 */
	public AsteriskController(AsteriskInterest _parent, String _ipaddy,
			String _amu, String _amp) {
		this.parent = _parent;
		this.asteriskserverip = _ipaddy;
		this.astManUser = _amu;
		this.astManPword = _amp;
	}

	/**
	 * @throws IOException
	 */
	public void HelloManager() throws IOException {
		factory = new ManagerConnectionFactory(asteriskserverip,
				getAstManUser(), getAstManPword());
		managerConnection = factory.createManagerConnection();
		try {
			ManagerLogin();
		} catch (TimeoutException te) {

		} catch (AuthenticationFailedException afe) {

		} catch (IOException ioe) {

		}
		asteriskserver = new DefaultAsteriskServer(managerConnection);
		setLoggedIn(true);
	}

	/**
	 * @throws TimeoutException
	 * @throws AuthenticationFailedException
	 * @throws IOException
	 */
	public void ManagerLogin() throws TimeoutException,
			AuthenticationFailedException, IOException {
		managerConnection.addEventListener(this);
		managerConnection.login();
	}

	/**
	 * 
	 */
	public void ManagerLogoff() {
		managerConnection.logoff();
	}

	/**
	 * @param ev
	 */
	public void onManagerEvent(ManagerEvent ev) {
		if (parent != null) {
			parent.AsteriskManagerEventReceived(ev);
		}
	}

	/**
	 * @param callfrom
	 * @param callto
	 * @param callParams
	 */
	public void Make_Call(String callfrom, String callto,
			Map<String, String> callParams) {
		OriginateAction originateAction;
		ManagerResponse originateResponse;

		originateAction = new OriginateAction();
		originateAction.setChannel("Local/999");
		originateAction.setVariables(callParams);
		originateAction.setContext("ext-local-custom");
		originateAction.setExten("999");
		originateAction.setPriority(new Integer(1));
		originateAction.setTimeout(1000);
		try {
			originateResponse = managerConnection.sendAction(originateAction);
			System.out.println("RESPONSE: \n" + originateResponse.getResponse()
					+ "\nDONE.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param queue
	 * @param iface
	 */
	public void queueMemberAdd(String queue, String iface) {
		ManagerResponse mr;
		QueueAddAction qadd = new QueueAddAction(queue, iface);
		try {
			mr = managerConnection.sendAction(qadd);
		} catch (IOException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (TimeoutException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalArgumentException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalStateException ex) {
			alog.fatal(AsteriskController.class.getName());
		}

	}

	/**
	 * @param queue
	 * @param iface
	 */
	public void queueMemberRemove(String queue, String iface) {
		ManagerResponse mr;
		QueueRemoveAction qrem = new QueueRemoveAction(queue, iface);
		try {
			mr = managerConnection.sendAction(qrem);
		} catch (IOException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (TimeoutException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalArgumentException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalStateException ex) {
			alog.fatal(AsteriskController.class.getName());
		}

	}

	/**
	 * @param queue
	 * @param iface
	 */
	public void queueMemberPause(String queue, String iface) {
		ManagerResponse mr;
		QueueRemoveAction qpaus = new QueueRemoveAction(queue, iface);
		try {
			mr = managerConnection.sendAction(qpaus);
		} catch (IOException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (TimeoutException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalArgumentException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalStateException ex) {
			alog.fatal(AsteriskController.class.getName());
		}

	}

	/**
	 * @param queue
	 * @param iface
	 */
	public void queueMemberUnpause(String queue, String iface) {
		ManagerResponse mr;
		QueueRemoveAction qunpaus = new QueueRemoveAction(queue, iface);
		try {
			mr = managerConnection.sendAction(qunpaus);
		} catch (IOException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (TimeoutException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalArgumentException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalStateException ex) {
			alog.fatal(AsteriskController.class.getName());
		}

	}

	/**
	 * 
	 */
	public void queueStatus() {
		ResponseEvents re;
		QueueStatusAction qsa = new QueueStatusAction();
		try {
			re = managerConnection.sendEventGeneratingAction(qsa, 2000);
		} catch (IOException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (EventTimeoutException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalArgumentException ex) {
			alog.fatal(AsteriskController.class.getName());
		} catch (IllegalStateException ex) {
			alog.fatal(AsteriskController.class.getName());
		}
	}

	/**
	 * @param from
	 * @param to
	 */
	public void connect_makecall(String from, String to) {
		System.out.println("Connect pressed, making call to boomgate's room");
		// sw_connect = !sw_connect;

		Map<String, String> callParams = new HashMap<String, String>();
		// callParams.put("publicendpoint", "302");
		callParams.put("CALLERID(num)", "1602");
		try {
			// asteriskserver.originateToExtensionAsync( "Local/601", "default",
			// "499", 1, 5000, null, callParams, null);
			asteriskserver.originateToExtensionAsync(from, "from-internal", to,
					1, 50000, null, callParams, null);
		} catch (ManagerCommunicationException mce) {

		}
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn
	 *            the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the astManUser
	 */
	public String getAstManUser() {
		return astManUser;
	}

	/**
	 * @param astManUser
	 *            the astManUser to set
	 */
	public void setAstManUser(String astManUser) {
		this.astManUser = astManUser;
	}

	/**
	 * @return the astManPword
	 */
	public String getAstManPword() {
		return astManPword;
	}

	/**
	 * @param astManPword
	 *            the astManPword to set
	 */
	public void setAstManPword(String astManPword) {
		this.astManPword = astManPword;
	}

}
