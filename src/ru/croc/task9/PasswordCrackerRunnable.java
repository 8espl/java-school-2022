package ru.croc.task9;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ru.croc.task9.PasswordCracking.alphabet;

public class PasswordCrackerRunnable implements Runnable {
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private String hash;
    private int passwordLength;
    private int start; // начальные символ алфавита, на который поток будет искать пароль
    private int end; // последний символ алфавита, на который поток будет искать пароль
    private boolean isFound = false;
    private String password;

    public PasswordCrackerRunnable(String hash, int passwordLength, int start, int end) {
        this.hash = hash;
        this.passwordLength = passwordLength;
        this.start = start;
        this.end = end;
    }

    public void getPossiblePassword(StringBuilder sb, int n) {
        if (isFound)
            return;
        // когда набрали новый пароль, проверяем его на совпадение с заданным хешированным
        if (n == sb.length()) {
            String possiblePassword = sb.toString();
            String possibleHashPassword = hashPassword(possiblePassword);

            if (hash.equals(possibleHashPassword)) {
                password = possiblePassword;
                isFound = true;
            }
            return;
        }

        // если символов не хватает, то рекурсивно добираем в алфавитном порядке символы
        for (int i = 0; i < alphabet.length && !isFound; ++i) {
            char nextSymbol = alphabet[i];
            sb.setCharAt(n, nextSymbol);
            getPossiblePassword(sb, n + 1);
        }
    }

    public void run() {
        /*
        область ответственности потока - начальные буквы пароля,
        которые содержатся в алфавите с индекса [start] до [end-1]
         */
        for (int i = start; i < end; ++i) {
            StringBuilder sb = new StringBuilder();
            sb.setLength(passwordLength);
            sb.setCharAt(0, alphabet[i]); // устанавливаем первый символ пароля
            getPossiblePassword(sb, 1); // ищем остальные символы и проверяем на совпадение
        }

        if (isFound) {
            System.out.println("Password = '" + password + "'");
        } else {
            System.out.println("Password starting with symbol from '" + alphabet[start] +
                    "' to '" + alphabet[end-1] + "' is not found.");
        }
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(HEX_DIGITS[(b & 0xff) >> 4]);
            hex.append(HEX_DIGITS[b & 0x0f]);
        }
        return hex.toString();
    }

    static String hashPassword(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(password.getBytes());
        byte[] bytes = digest.digest();
        return toHexString(bytes);
    }
}


