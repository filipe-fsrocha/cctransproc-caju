package br.com.fsrocha.cctransproc.domain.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MaskUtils {

    public static String applyMask(String number) {
        return number.replaceAll("(.{4})", "$1 ").trim();
    }

}
