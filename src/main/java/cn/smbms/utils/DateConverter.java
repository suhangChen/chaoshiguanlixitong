package cn.smbms.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description:
 * Created by Ray on 2019-09-19
 */
public class DateConverter implements Converter<String, Date> {
    //2019-09-02
    @Override
    public Date convert(String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
