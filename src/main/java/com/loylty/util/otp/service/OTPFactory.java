package com.loylty.util.otp.service;

import com.loylty.util.otp.entity.OneTimePasswordConfig;
import com.loylty.util.otp.service.impl.TimeBaseOtpGenerator;

public class OTPFactory
	{
		private static TimeBaseOtpGenerator timeBaseOtpGenerator;
		
		public static TimeBaseOtpGenerator getInstance(OneTimePasswordConfig config)
			{
				if (timeBaseOtpGenerator == null)
					{
						timeBaseOtpGenerator = new TimeBaseOtpGenerator(config);
					}
				return timeBaseOtpGenerator;
			}
	}
