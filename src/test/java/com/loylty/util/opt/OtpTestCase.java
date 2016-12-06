package com.loylty.util.opt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.loylty.util.otp.entity.OneTimePasswordConfig;
import com.loylty.util.otp.entity.SecretKey;
import com.loylty.util.otp.service.OTPGenerator;

public class OtpTestCase
	{
		public final static Logger	LOGGER		= Logger.getLogger(OtpTestCase.class);
		
		public static String		requestId	= "" + UUID.randomUUID();
		public static String		source		= "WEB";
		public static String		tenantId	= "" + UUID.randomUUID();
		public static String		userId		= "" + UUID.randomUUID();
		public static int			interval	= 30;
		public static int			count		= 6;
		
		@Test
		public void createOtp() throws InterruptedException
			{
				Long start = System.currentTimeMillis() / 1000;
				OneTimePasswordConfig oneTimePasswordConfig = new OneTimePasswordConfig(interval, count, new SecretKey(requestId, source, tenantId, userId));
				Integer otp = OTPGenerator.generateOtp(oneTimePasswordConfig);
				int delay = 29;
				Thread.sleep(delay * 1000);
				long currentTime = System.currentTimeMillis();
				LOGGER.info("Time : " + (new SimpleDateFormat("dd_MM_YYYY_hh:mm:SSSS")).format(new Date(currentTime)) + " Sec : " + currentTime / 1000);
				boolean result = OTPGenerator.verify(oneTimePasswordConfig, otp);
				LOGGER.info("START : " + ((System.currentTimeMillis() / 1000) - start) + " sec");
				Assert.assertTrue("OTP DOES NOT MATCH", result);
			}
			
	}
