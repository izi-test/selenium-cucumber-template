package com.example;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AtoBMapper {

    private static final Map<String, String> propertyMap = new HashMap<>();

    static {
        propertyMap.put("name", "fullName");
        propertyMap.put("address", "location");
        propertyMap.put("email", "emailAddress");
    }

    public static B aToB(A a) {
        if (a == null) {
            return null;
        }
        B b = new B();
        mapProperties(a, b, propertyMap);
        return b;
    }

    public static A bToA(B b) {
        if (b == null) {
            return null;
        }
        A a = new A();
        mapProperties(b, a, propertyMap);
        return a;
    }

    private static void mapProperties(Object source, Object target, Map<String, String> propertyMap) {
        for (Field sourceField : source.getClass().getFields()) {
            try {
                String sourceFieldName = sourceField.getName();
                String targetFieldName = propertyMap.getOrDefault(sourceFieldName, sourceFieldName);
                Field targetField = target.getClass().getField(targetFieldName);
                targetField.set(target, sourceField.get(source));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();  // Vous pouvez gérer les exceptions plus proprement ici
            }
        }
    }
}
