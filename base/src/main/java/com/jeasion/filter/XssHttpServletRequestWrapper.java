package com.jeasion.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 防止sql注入,xss攻击.
 * <p>
 * 代码参照自：{@code https://github.com/2010yhh/springBoot-demos/blob/master/springboot-html/src/main/java/com/ctg/test/springboothtml/filter/XssHttpServletRequestWrapper.java}
 *
 * @author liushanping
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final String KEY_WORDS = "and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+";
    private static final Set<String> NOT_ALLOWED_KEYWORDS = new HashSet<>();
    private static final String REPLACED_STRING = "INVALID";

    static {
        String[] keyStr = KEY_WORDS.split("\\|");
        Collections.addAll(NOT_ALLOWED_KEYWORDS, keyStr);
    }

    private final String currentUrl;

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        currentUrl = servletRequest.getRequestURI();
    }


    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXss(value);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXss(values[i]);
        }
        return encodedValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> values = super.getParameterMap();
        if (values == null) {
            return null;
        }
        Map<String, String[]> result = new HashMap<>(10);
        for (String key : values.keySet()) {
            String encodedKey = cleanXss(key);
            int count = values.get(key).length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = cleanXss(values.get(key)[i]);
            }
            result.put(encodedKey, encodedValues);
        }
        return result;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXss(value);
    }

    private String cleanXss(String valueP) {
        // You'll need to remove the spaces from the html entities below
        String value = valueP.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\"']\\s*javascript:(.*)[\"']", "\"\"");
        value = value.replaceAll("script", "");
        value = cleanSqlKeyWords(value);
        return value;
    }

    private String cleanSqlKeyWords(String value) {
        String paramValue = value;
        for (String keyword : NOT_ALLOWED_KEYWORDS) {
            boolean isValidSqlKeyWords =
                    paramValue.length() > keyword.length() + 4
                            && (
                            paramValue.contains(" " + keyword)
                                    || paramValue.contains(keyword + " ")
                                    || paramValue.contains(" " + keyword + " ")
                    );
            if (isValidSqlKeyWords) {
                paramValue = StringUtils.replace(paramValue, keyword, REPLACED_STRING);
                log.error(this.currentUrl + "已被过滤，因为参数中包含不允许sql的关键词(" + keyword + ")" + ";参数：" + value + ";过滤后的参数：" + paramValue);
            }
        }
        return paramValue;
    }

}