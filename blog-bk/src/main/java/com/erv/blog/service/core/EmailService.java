package com.erv.blog.service.core;

import java.io.ByteArrayOutputStream;

public interface EmailService {
	
	public void sendEmail(String subject,String body,String email);
	
	public boolean sendHtmlEmail(String subject,String body,String email);
	
	public void sendEmailWithAttachment(ByteArrayOutputStream ckycZipFile, String fileName, String subject, String toEmail);

}
