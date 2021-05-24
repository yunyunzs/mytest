package cn.zxp.demo.config;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName StringToDateConverter
 * @Description TODO  自定义字符串向日期转换器
 */
public class StringToDateConverter implements Converter<String, Date> {

    /*日期正则表达式： yyyy-MM-dd*/
    private String datePattern;

    public StringToDateConverter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public Date convert(String s) {
        Date date = null;
        try {
            date = new SimpleDateFormat(datePattern).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
