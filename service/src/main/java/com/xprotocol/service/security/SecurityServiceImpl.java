/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Service;

/**
 * original code @ http://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
 * 
 * @author Tao Zhao
 */
@Service
public class SecurityServiceImpl implements SecurityService {
    
    /**
     * Each token produced by this class uses this identifier as a prefix.
     */
    public static final String ID = "$31$";

    /**
     * The minimum recommended cost, used by default
     */
    public static final int DEFAULT_COST = 16;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    private static final int SIZE = 128;

    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");

    private final SecureRandom random;

    private final int cost;

    public SecurityServiceImpl()
    {
        this(DEFAULT_COST);
    }

    /**
     * Create a password manager with a specified cost
     * 
     * @param cost the exponential computational cost of hashing a password, 0 to 30
     */
    public SecurityServiceImpl(int cost)
    {
        iterations(cost); /* Validate cost */
        this.cost = cost;
        this.random = new SecureRandom();
    }

    private static int iterations(int cost)
    {
        if ((cost & ~0x1F) != 0){
            throw new IllegalArgumentException("cost: " + cost);
        }
        return 1 << cost;
    }

    /**
     * Hash a password for storage.
     * 
     * @param password
     * @return a secure authentication token to be stored for later authentication 
     */
    @Override
    public String hashPassword(String password)
    {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password.toCharArray(), salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return ID + cost + '$' + enc.encodeToString(hash);
    }

    /**
     * Authenticate with a password and a stored password token.
     * 
     * @param password
     * @param token : hash code stored in database
     * 
     * @return true if the password and token match
     */
    @Override
    public boolean authenticate(String password, String token)
    {
        Matcher m = layout.matcher(token);
        if (!m.matches()){
            throw new IllegalArgumentException("Invalid token format");
        }
        int iterations = iterations(Integer.parseInt(m.group(1)));
        byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
        byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
        byte[] check = pbkdf2(password.toCharArray(), salt, iterations);
        int zero = 0;
        for (int idx = 0; idx < check.length; ++idx){
            zero |= hash[salt.length + idx] ^ check[idx];
        }
        return zero == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations)
    {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
          SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
          return f.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException ex) {
          throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        }
        catch (InvalidKeySpecException ex) {
          throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }

}