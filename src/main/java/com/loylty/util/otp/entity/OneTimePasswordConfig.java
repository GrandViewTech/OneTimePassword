package com.loylty.util.otp.entity;

public class OneTimePasswordConfig
	{
		public final static org.apache.log4j.Logger	LOGGER	= org.apache.log4j.Logger.getLogger(OneTimePasswordConfig.class);
		
		final int									interval;
		final int									count;
		final SecretKey								secretKey;
		final int									delay;
		
		public OneTimePasswordConfig(int delay, int count, SecretKey secretKey)
			{
				super();
				this.delay = 30;
				this.interval = delay;
				this.count = count;
				this.secretKey = secretKey;
				LOGGER.info("One Time Password Config : " + this.toString());
			}
			
		public int getDelay()
			{
				return delay;
			}
			
		public int getInterval()
			{
				return interval;
			}
			
		public int getCount()
			{
				return count;
			}
			
		public SecretKey getSecretKey()
			{
				return secretKey;
			}
			
		@Override
		public String toString()
			{
				return "OneTimePasswordConfig [interval=" + interval + ", count=" + count + ", secretKey=" + secretKey + ", delay=" + delay + "]";
			}
			
	}
