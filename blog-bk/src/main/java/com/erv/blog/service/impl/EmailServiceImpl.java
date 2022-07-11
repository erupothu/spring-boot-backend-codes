package com.erv.blog.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.erv.blog.service.core.EmailService;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPSenderFailedException;


@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender emailSender;
	
	@Override
	public void sendEmail(String subject,String body,String email) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(subject);
		message.setText(body);
		emailSender.send(message);
	}
	
	public boolean sendHtmlEmail(String subject,String body,String email) {
		// TODO Auto-generated method stub
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper;
		boolean sent = false;
			try {
				helper = new MimeMessageHelper(message, true);
				helper.setTo(email);
				helper.setSubject(subject);
				helper.setText(body, true);
				emailSender.send(message);
				sent = true;
			} catch (MailSendException | MessagingException e) {	
				// TODO Auto-generated catch block
				
			}
		return sent;
		
	}
	
	public void sendEmailWithAttachment(ByteArrayOutputStream ckycZipFile, String fileName, String subject, String toEmail) {
		// TODO Auto-generated method stub
		MimeMessage message = emailSender.createMimeMessage();
		FileSystemResource file = new FileSystemResource(new File("testing.pdf"));
		ByteArrayDataSource ckycZipFile2 = new ByteArrayDataSource(ckycZipFile.toByteArray(),"application/zip" );
		
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			String[] to = {"ajit.k@vayaindia.com", "nazima.n@vayaindia.com", "swathi.g@vayaindia.com"};
			helper.setTo(to); //"anilk@vayaindia.com"
			helper.setSubject(subject);
			helper.setText(subject);
			helper.addAttachment(fileName, ckycZipFile2);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		emailSender.send(message);

	}

}
