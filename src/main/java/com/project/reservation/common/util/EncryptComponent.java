package com.project.reservation.common.util;

public interface EncryptComponent {

    String encryptString(String originalString);

    String decryptString(String encryptedString);
}
