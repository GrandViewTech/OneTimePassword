package com.loylty.util.otp.service;

import com.loylty.util.otp.entity.OneTimePasswordConfig;

public class OTPGenerator
	{
		public static Integer generateOtp(OneTimePasswordConfig config)
			{
				String opt = OTPFactory.getInstance(config).now();
				return new Integer(opt);
			}
			
		public static boolean verify(OneTimePasswordConfig config, Integer otp)
			{
				return OTPFactory.getInstance(config).verify("" + otp);
			}
	}
