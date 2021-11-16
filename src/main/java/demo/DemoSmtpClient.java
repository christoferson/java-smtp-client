package demo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class DemoSmtpClient {

	public static void main(String[] args) throws Exception {

		Session session = createSession("xxx", "yyyy", "zzz");
		
		sendMessage(session, "xxx", "xxx");

	}

	private static void sendMessage(Session session, String from, String to) throws MessagingException, AddressException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject("Mail Subject " + System.currentTimeMillis());

		String msg = String.format("<html><body><p style='color:red;'>Foo %s</p></body></html>", System.currentTimeMillis());

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);
	}

	private static Session createSession(final String username, final String password, final String smtpEndpoint) {
		Properties prop = new Properties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", smtpEndpoint);
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
		return session;
	}

}
