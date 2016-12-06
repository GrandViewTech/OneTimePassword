package com.loylty.util.otp.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.loylty.util.otp.entity.Clock;
import com.loylty.util.otp.entity.Hmac;
import com.loylty.util.otp.entity.OneTimePasswordConfig;
import com.loylty.util.otp.entity.constants.Digits;
import com.loylty.util.otp.entity.constants.Hash;

public class TimeBaseOtpGenerator
	{
		public final static org.apache.log4j.Logger	LOGGER	= org.apache.log4j.Logger.getLogger(TimeBaseOtpGenerator.class);
		private final String						secret;
		private final Clock							clock;
		private final OneTimePasswordConfig			config;
		
		/**
		 * Initialize an OTP instance with the shared secret generated on
		 * Registration process
		 *
		 * @param secret
		 *            Shared secret
		 * @param clock
		 *            Clock responsible for retrieve the current interval
		 */
		public TimeBaseOtpGenerator(OneTimePasswordConfig config)
			{
				this.config = config;
				this.secret = config.getSecretKey().key();
				this.clock = new Clock(config.getCount());
				
			}
			
		/**
		 * Prover - To be used only on the client side Retrieves the encoded URI
		 * to generated the QRCode required by Google Authenticator
		 *
		 * @param name
		 *            Account name
		 * @return Encoded URI
		 */
		public String uri(String name)
			{
				try
					{
						return String.format("otpauth://totp/%s?secret=%s", URLEncoder.encode(name, "UTF-8"), secret);
					}
				catch (UnsupportedEncodingException e)
					{
						throw new IllegalArgumentException(e.getMessage(), e);
					}
			}
			
		/**
		 * Retrieves the current OTP
		 *
		 * @return OTP
		 */
		public String now()
			{
				return leftPadding(hash(secret, clock.getCurrentTimeIntervalInSeconds()));
			}
			
		/**
		 * Verifier - To be used only on the server side
		 * <p/>
		 * Taken from Google Authenticator with small modifications from
		 * {@see <a href=
		 * "http://code.google.com/p/google-authenticator/source/browse/src/com/google/android/apps/authenticator/PasscodeGenerator.java?repo=android#212">PasscodeGenerator.java</a>}
		 * <p/>
		 * Verify a timeout code. The timeout code will be valid for a time
		 * determined by the interval period and the number of adjacent
		 * intervals checked.
		 *
		 * @param otp
		 *            Timeout code
		 * @return True if the timeout code is valid
		 *         <p/>
		 */
		public boolean verify(String otp)
			{
				long code = Long.parseLong(otp);
				long currentTimeInSeconds = clock.getCurrentTimeIntervalInSeconds();
				int pastResponse = Math.max(config.getDelay(), 0);
				int counter = 1;
				int expireCounter = config.getInterval();
				for (int i = (pastResponse); i >= 0; --i)
					{
						Long current = (currentTimeInSeconds - i);
						int candidate = generate(this.secret, current);
						if (candidate == code)
							{
								if (counter > expireCounter)
									{
										LOGGER.info("Iteration  " + counter + " | PRE : " + candidate + " | POST : " + code + " | STATUS : EXPIRE | TIME : " + current);
									}
								else
									{
										LOGGER.info("Iteration  " + counter + " | PRE : " + candidate + " | POST : " + code + " | STATUS : ACCEPTED | TIME : " + current);
									}
								return true;
							}
						else
							{
								LOGGER.info("Iteration  " + counter + " | PRE : " + candidate + " | POST : " + code + " | STATUS : NOT FOUND | TIME : " + current);
							}
						counter = counter + 1;
					}
				return false;
			}
			
		private int generate(String secret, long interval)
			{
				return hash(secret, interval);
			}
			
		private int hash(String secret, long interval)
			{
				byte[] hash = new byte[0];
				try
					{
						// Base32 encoding is just a requirement for google
						// authenticator. We can remove it on the next releases.
						// hash = new Hmac(Hash.SHA1, Base32.decode(secret),
						// interval).digest();
						hash = new Hmac(Hash.SHA1, secret.getBytes(), interval).digest();
						
					}
				catch (NoSuchAlgorithmException exception)
					{
						LOGGER.error(exception.getLocalizedMessage(), exception);
					}
				catch (InvalidKeyException exception)
					{
						LOGGER.error(exception.getLocalizedMessage(), exception);
					}
				return bytesToInt(hash);
			}
			
		private int bytesToInt(byte[] hash)
			{
				// put selected bytes into result int
				if (hash.length > 0)
					{
						int offset = hash[hash.length - 1] & 0xf;
						
						int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
						
						return binary % Digits.SIX.getValue();
					}
				return -1;
			}
			
		private String leftPadding(int otp)
			{
				LOGGER.info("OPT GENERATED : " + otp);
				return String.format("%06d", otp);
			}
			
	}
