package util;

import java.time.LocalDate;

public class DatasUtil {


    public static boolean dataMaiorQueHoje(LocalDate data) {
        if (data == null) {
            return false;
        }
        return data.isAfter(LocalDate.now());
    }

    
    public static boolean dataExpirada(LocalDate data) {
        if (data == null) {
            return false;
        }
        return data.isBefore(LocalDate.now());
    }
}

