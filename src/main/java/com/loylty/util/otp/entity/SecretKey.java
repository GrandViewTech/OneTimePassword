package com.loylty.util.otp.entity;

public class SecretKey
	{
		public final static org.apache.log4j.Logger	LOGGER	= org.apache.log4j.Logger.getLogger(SecretKey.class);
		
		final private String						requestId;
		final private String						source;
		final private String						tenantId;
		final private String						userId;
		
		public SecretKey(String requestId, String source, String tenantId, String userId)
			{
				super();
				this.requestId = requestId.replaceAll("-", "");
				this.source = source;
				this.tenantId = tenantId;
				this.userId = userId;
			}
			
		public String key()
			{
				String key = filter(String.join(".", tenantId, source, requestId, userId));
				LOGGER.info("Secret Key : " + key);
				return key;
			}
			
		@Override
		public String toString()
			{
				return "SecretKey [requestId=" + requestId + ", source=" + source + ", tenantId=" + tenantId + ", userId=" + userId + "]";
			}
			
		private static String filter(String data)
			{
				if (data == null || data.trim().length() == 0)
					{
						data = "";
					}
				String filter = data.replaceAll("-", "");
				return filter;
			}
	}
