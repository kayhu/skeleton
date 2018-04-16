//package org.iakuh.skeleton.common.log;
//
//import ch.qos.logback.classic.PatternLayout;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import com.google.common.collect.Maps;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Map;
//import java.util.Set;
//
//@Slf4j
//public class SensitivePatternLayout extends PatternLayout {
//
//    private static boolean sensitive;
//
//    private static final Map<String, String> regexMap = Maps.newHashMap();
//
//    static {
//        sensitive = Boolean.valueOf(System.getenv("LOG_SENSITIVE"));
//        log.info("Logback sensitive config :{}", sensitive);
//        if (sensitive) {
//            //phone
//            regexMap.put("([^0-9]1\\d{2})\\d{4}(\\d{4}[^0-9])", "$1****$2");
//            //ssn
//            regexMap.put("([^0-9\\-]\\d{10})\\d{6}(\\d{1})([0-9X])([^0-9X])", "$1******$2*$4");
//
//            regexMap.put("(password\"s*:s*\").*?(\",)", "$1****$2");
//            regexMap.put("(password\\\\\"s*:\\s*\\\\\").*?(\\\\\",)", "$1****$2");
//            regexMap.put("(password=).*?(,)", "$1****$2");
//
//            regexMap.put("([^a-zA-Z]name=.{1}).*?(,)", "$1**$2");
//            regexMap.put("([^a-zA-Z]name\"s*:s*\".{1}).*?(\",)", "$1**$2");
//            regexMap.put("([^a-zA-Z]name\\\\\"s*:\\s*\\\\\".{1}).*?(\\\\\",)", "$1**$2");
//        }
//    }
//
//    @Override
//    public String doLayout(ILoggingEvent event) {
//        return replaceByRegex(super.doLayout(event));
//    }
//
//    private String replaceByRegex(String message) {
//        if (sensitive) {
//            Set<String> keySet = regexMap.keySet();
//            for (String key : keySet) {
//                message = message.replaceAll(key, regexMap.get(key));
//            }
//        }
//        return message;
//    }
//}
