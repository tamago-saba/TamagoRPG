package net.nutchi.tamagorpg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    public static List<String> getScores(String command) {
        String regex =
                "(?<!^\\s{0,99}#.{0,99})(((?<=scores=\\{([^\\}]{0,99},)?\\s{0,99})[^\\s]*?(?==.{0,99}?\\}))|((?<=scoreboard\\s{1,99}objectives\\s{1,99}(add|remove|modify|(setdisplay\\s{1,99}[^\\s]{1,99}?))\\s{1,99}).*?(?=(\\s|$|\")))|((?<=execute\\s{1,99}.{1,99}?\\s{1,99}store\\s{1,99}result\\s{1,99}score\\s{1,99}[^\\s]{1,99}?\\s{1,99}).*?(?=\\s{1,99}run)))";
        return getMatches(command, regex);
    }

    public static List<String> getTags(String command) {
        String regex =
                "(?<!^\\s{0,99}#.{0,99})(((?<=tag=!?)[^!].*?(?=,|]))|((?<=tag.{1,99}?(add|remove)\\s{1,99}).*?$)|((?<=Tags:\\[([^\\]]{0,99},)?\\s{0,99}\").*?(?=\"\\s{0,99}(,|]))))";
        return getMatches(command, regex);
    }

    private static List<String> getMatches(String command, String regex) {
        List<String> scores = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            scores.add(matcher.group());
        }
        return scores;
    }
}
