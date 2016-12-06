package com.loylty.util.otp.entity;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.loylty.util.otp.entity.constants.Hash;

public class Hmac
	{
		
		public static final String	ALGORITHM	= "RAW";
		private final Hash			hash;
		private final byte[]		secret;
		private final long			currentInterval;
		
		public Hmac(Hash hash, byte[] secret, long currentInterval)
			{
				this.hash = hash;
				this.secret = secret;
				this.currentInterval = currentInterval;
			}
			
		public byte[] digest() throws NoSuchAlgorithmException, InvalidKeyException
			{
				byte[] challenge = ByteBuffer.allocate(8).putLong(currentInterval).array();
				Mac mac = Mac.getInstance(hash.toString());
				SecretKeySpec macKey = new SecretKeySpec(secret, ALGORITHM);
				mac.init(macKey);
				return mac.doFinal(challenge);
			}
	}
