package util;

import java.util.regex.Pattern;

public class Validador {

    public static boolean placaValida(String placa) {
        if (placa == null || placa.isBlank()) {

            return false;
        }


        placa = placa.trim().toUpperCase();

        // Padrão antigo e Mercosul
        String padraoAntigo = "^[A-Z]{3}-?\\d{4}$";
        String padraoMercosul = "^[A-Z]{3}\\d[A-Z]\\d{2}$";
        return Pattern.matches(padraoAntigo, placa) || Pattern.matches(padraoMercosul, placa);
    }


    public static boolean cnhValidaFormat(String cnh) {
        if (cnh == null) {
            return false;
        }

        String cnhLimpa = cnh.trim();

        // CNH deve ter exatamente 11 dígitos numéricos
        return cnhLimpa.matches("\\d{11}");
    }
}

