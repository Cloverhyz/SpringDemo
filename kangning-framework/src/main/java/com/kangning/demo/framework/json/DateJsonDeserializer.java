package com.kangning.demo.framework.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 加康宁 Date: 2019-07-01 Time: 19:04
 * @version $Id$
 */
/**
 * @author 加康宁 Date: 2019-02-19 Time: 19:00
 * @version $Id$
 */
public class DateJsonDeserializer extends JsonDeserializer<Date> implements ContextualDeserializer {

    private final static Logger logger = LoggerFactory.getLogger(DateJsonDeserializer.class);

    public final DateFormat df;

    public final String formatString;

    public DateJsonDeserializer() {
        this.df = null;
        this.formatString = null;
    }

    public DateJsonDeserializer(DateFormat df) {
        this.df = df;
        this.formatString = "";
    }

    public DateJsonDeserializer(DateFormat df, String formatString) {
        this.df = df;
        this.formatString = formatString;
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            String dateValue = p.getText();
            if (df == null || StringUtils.isEmpty(dateValue)) {
                return null;
            }
            logger.info("使用自定义解析器解析字段:{}:时间:{}",p.getCurrentName(),p.getText());
            Date date;
            if (StringUtils.isNumeric(dateValue)){
                date = new Date(Long.valueOf(dateValue));
                return df.parse(df.format(date));
            }else {
                return df.parse(p.getText());
            }
        } catch (ParseException e) {
            logger.error("JSON反序列化，时间解析失败", e);
            return null;
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            JsonFormat.Value format = ctxt.getAnnotationIntrospector().findFormat(property.getMember());

            if (format != null) {
                TimeZone tz = format.getTimeZone();
                // First: fully custom pattern?
                if (format.hasPattern()) {
                    final String pattern = format.getPattern();

                    final Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                    SimpleDateFormat df = new SimpleDateFormat(pattern, loc);
                    if (tz == null) {
                        tz = ctxt.getTimeZone();
                    }
                    df.setTimeZone(tz);
                    return new DateJsonDeserializer(df, pattern);
                }
                // But if not, can still override timezone
                if (tz != null) {
                    DateFormat df = ctxt.getConfig().getDateFormat();
                    // one shortcut: with our custom format, can simplify handling a bit
                    if (df.getClass() == StdDateFormat.class) {
                        final Locale loc = format.hasLocale() ? format.getLocale() : ctxt.getLocale();
                        StdDateFormat std = (StdDateFormat) df;
                        std = std.withTimeZone(tz);
                        std = std.withLocale(loc);
                        df = std;
                    } else {
                        // otherwise need to clone, re-set timezone:
                        df = (DateFormat) df.clone();
                        df.setTimeZone(tz);
                    }
                    return new DateJsonDeserializer(df);
                }
            }
        }
        return this;
    }
}