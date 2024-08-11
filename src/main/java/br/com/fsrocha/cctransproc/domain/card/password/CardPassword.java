package br.com.fsrocha.cctransproc.domain.card.password;

import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class CardPassword {

    public static String encrypt(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static boolean match(String password, String encryptedPassword) {
        return encryptedPassword.equals(encrypt(password));
    }
}
