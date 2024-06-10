package com.project.securityservice.util.impl;

import com.project.common.exception.CustomException;
import com.project.securityservice.util.EncryptComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static com.project.common.exception.ErrorCode.*;


/**
 * 암호화, 복호화 위한 class
 */
@Component
@RequiredArgsConstructor
public class EncryptComponentImpl implements EncryptComponent {

    private final Environment env;

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();


    @Override
    public String encryptString(String originalString) {

        String secretKey = env.getProperty("encrypt.secretKey");

        try {
            byte[] encryptedString =
                    cipherPkcs5(Cipher.ENCRYPT_MODE, secretKey)
                            .doFinal(originalString.getBytes(StandardCharsets.UTF_8));

            return new String(encoder.encode(encryptedString));

        } catch (IllegalArgumentException | GeneralSecurityException e) {
            throw new CustomException(ENCRYPT_FAIL);
        }

    }

    @Override
    public String decryptString(String encryptedString) {

        String secretKey = env.getProperty("encrypt.secretKey");

        try {
            byte[] byteString =
                    decoder.decode(encryptedString.getBytes(StandardCharsets.UTF_8));

            return new String(cipherPkcs5(Cipher.DECRYPT_MODE, secretKey)
                    .doFinal(byteString));

        } catch (IllegalArgumentException | GeneralSecurityException e) {
            throw new CustomException(DECRYPT_FAIL);
        }

    }

    private Cipher cipherPkcs5(Integer opMode, String secretKey) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec sk = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8), "AES"
            );
            IvParameterSpec iv = new IvParameterSpec(
                    secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8)
            );
            c.init(opMode, sk, iv);
            return c;

        } catch (Exception e) {
            throw new CustomException(CIPHER_GENERATING_FAIL);
        }
    }
}
