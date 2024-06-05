package com.project.securityservice.util;

public interface EncryptComponent {

    String encryptString(String originalString);

    String decryptString(String encryptedString);
}
