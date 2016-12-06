package com.loylty.util.otp.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Clock
	{
		public final static org.apache.log4j.Logger	LOGGER	= org.apache.log4j.Logger.getLogger(Clock.class);
		private final int							interval;
		private Calendar							calendar;
		
		public Clock()
			{
				interval = 30;
			}
			
		public Clock(int interval)
			{
				this.interval = interval;
			}
			
		public long getCurrentTimeIntervalInSeconds()
			{
				/*
				 * calendar =
				 * GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
				 * long currentTimeSeconds = calendar.getTimeInMillis() / 1000;
				 */
				long currentTimeInMilliSeconds = System.currentTimeMillis();
				long currentTimeSeconds = currentTimeInMilliSeconds / 1000;
				LOGGER.info("Time : " + (new SimpleDateFormat("dd_MM_YYYY_hh:mm:SSSS")).format(new Date(currentTimeInMilliSeconds)) + " Sec : " + currentTimeSeconds);
				return currentTimeSeconds;/// interval;
			}
			
	}
