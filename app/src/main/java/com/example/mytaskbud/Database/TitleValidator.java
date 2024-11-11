package com.example.mytaskbud.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleValidator {
    public List<String> validates(List<String> stringList) {
        List<String> validatedList = new ArrayList<>();
        Map<String, Integer> stringCountMap = new HashMap<>();

        for (String string : stringList) {
            if (!stringCountMap.containsKey(string)) {
                // If the string is not already in the count map, add it to the validated list
                validatedList.add(string);
                stringCountMap.put(string, 1);
            } else {
                // If the string is already in the count map, append a number to make it unique
                int count = stringCountMap.get(string);
                String validatedString = string + "_" + (count + 1);
                validatedList.add(validatedString);
                stringCountMap.put(string, count + 1);
            }
        }

        return validatedList;
    }
}
