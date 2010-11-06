/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.servicetools.mail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * 
 * @author chrismylonas
 */
public class Email {

	/**
	 * 
	 */
	private static Logger maillogger = Logger.getLogger(Email.class);

	/**
	 * 
	 */
	private String to;

	/**
	 * 
	 */
	private String from;

	/**
	 * 
	 */
	private String subject;

	/**
	 * 
	 */
	private String filename;

	/**
	 * 
	 */
	private String smtphost;

	/**
	 * 
	 */
	private String msgText;

	/**
	 * 
	 */
	private MimeBodyPart mbp1;

	/**
	 * 
	 */
	private MimeBodyPart mbp2;

	/**
	 * 
	 */
	private int id = 0;

	/**
     * 
     */
	public Email() {

	}

	/**
	 * @param _to
	 * @param _from
	 * @param _subject
	 * @param _msgText
	 * @param _attachment
	 * @param _smtphost
	 */
	public Email(String _to, String _from, String _subject, String _msgText,
			String _attachment, String _smtphost) {
		setTo(_to);
		setFrom(_from);
		setSubject(_subject);
		setMsgText(_msgText);
		setFilename(_attachment);
		setSmtphost(_smtphost);
		maillogger.info("New Email Created with ID: "
				+ Integer.toString(getId()));
	}

	/**
	 * @param _to
	 * @param _from
	 * @param _subject
	 * @param _msgText
	 * @param _attachment
	 */
	public Email(String _to, String _from, String _subject, String _msgText,
			String _attachment) {
		setTo(_to);
		setFrom(_from);
		setSubject(_subject);
		setMsgText(_msgText);
		setFilename(_attachment);
		maillogger.info("New Email Created with ID: "
				+ Integer.toString(getId()));
	}

	/**
	 * @param _to
	 * @param _from
	 * @param _subject
	 * @param _filename
	 */
	public Email(String _to, String _from, String _subject, String _filename) {
		setTo(_to);
		setFrom(_from);
		setSubject(_subject);
		setFilename(_filename);
		maillogger.info("New Email Created with ID: "
				+ Integer.toString(getId()));
	}

	/**
	 * @param _to
	 * @param _from
	 * @param _subject
	 */
	public Email(String _to, String _from, String _subject) {
		setTo(_to);
		setFrom(_from);
		setSubject(_subject);
		maillogger.info("New Email Created with ID: "
				+ Integer.toString(getId()));
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " to field: " + to);
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " from field: " + from);
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " subject field: " + subject);
		this.subject = subject;
	}

	/**
	 * @return the attachment
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setFilename(String attachment) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " attachment field: " + attachment);
		this.filename = attachment;
	}

	/**
	 * @return the smtphost
	 */
	public String getSmtphost() {
		return smtphost;
	}

	/**
	 * @param smtphost
	 *            the smtphost to set
	 */
	public void setSmtphost(String smtphost) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " smtphost field: " + smtphost);
		this.smtphost = smtphost;
	}

	/**
     * 
     */
	public void run() {
		Properties props = System.getProperties();
		if (getSmtphost() != null)
			props.put("mail.smtp.host", getSmtphost());
		else
			props.put("mail.smtp.host", "localhost");

		Session session = Session.getInstance(props, null);

		try {
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(getFrom()));
			InternetAddress[] address = { new InternetAddress(getTo()) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(getSubject());

			// create and fill the first message part
			mbp1 = new MimeBodyPart();
			mbp1.setText(getMsgText());

			if (getFilename() != null) {
				// create the second message part
				mbp2 = new MimeBodyPart();

				// attach the file to the message
				FileDataSource fds = new FileDataSource(getFilename());
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
			}

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			if (mbp2 != null) {
				mp.addBodyPart(mbp2);
			}
			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);
			maillogger.info("Mail with ID " + Integer.toString(getId())
					+ " has been sent to " + getTo());

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @return the msgText
	 */
	public String getMsgText() {
		return msgText;
	}

	/**
	 * @param msgText
	 *            the msgText to set
	 */
	public void setMsgText(String msgText) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " msg body field: " + msgText);
		this.msgText = msgText;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		maillogger.info("Setting Email with ID " + Integer.toString(id)
				+ " id field: " + id);
		this.id = id;
	}
}
