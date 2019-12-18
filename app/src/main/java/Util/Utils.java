package Util;

import java.text.DecimalFormat;

public class Utils {

    public static String formatNumber( int value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}
