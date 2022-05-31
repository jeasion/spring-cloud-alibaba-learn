package com.jeasion.until;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author liushanping
 */
public class FunctionUtil {

    public static <S, T> T parse(S source, Function<S, T> parseFunction) {
        return parseFunction.apply(source);
    }

    public static <S, T> List<T> parseToList(List<S> sources, Function<S, T> parseFunction) {
        List<T> targetList = new ArrayList<>();
        for (S source : sources) {
            targetList.add(parseFunction.apply(source));
        }
        return targetList;
    }

}
