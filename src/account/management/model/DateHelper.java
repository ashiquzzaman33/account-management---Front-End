/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account.management.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mohar
 */
public class DateHelper {
    public static String getDateFromTimestamp(String timestamp, String format){
        return new SimpleDateFormat(format).format(new Date(Long.parseLong(timestamp)));
    }
}
