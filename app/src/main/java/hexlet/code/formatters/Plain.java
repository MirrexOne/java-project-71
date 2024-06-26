package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Plain extends Format {
    public final String outputFormatting(List<Map<String, Object>> differenceTree) {
        StringBuilder plain = new StringBuilder();
        String definitionOfKey = "Property";

        for (Map<String, Object> entry : differenceTree) {
            Set<Map.Entry<String, Object>> entries = entry.entrySet();

            for (Map.Entry<String, Object> pairs : entries) {
                String key = pairs.getKey();
                Object value = pairs.getValue();

                if ("modified".equals(value)) {
                    Object valueBefore = changeRenderingValue(entry.get("value1"));
                    Object valueAfter = changeRenderingValue(entry.get("value2"));

                    plain.append(definitionOfKey).append(" '").append(key).append("' ").append("was updated. ")
                            .append("From ").append(valueBefore).append(" to ").append(valueAfter).append("\n");

                } else if ("added".equals(value)) {
                    Object addedValue = changeRenderingValue(entry.get("value"));

                    plain.append(definitionOfKey).append(" '").append(key).append("' ")
                            .append("was added with value: ").append(addedValue).append("\n");

                } else if ("deleted".equals(value)) {

                    plain.append(definitionOfKey).append(" '").append(key).append("' ")
                            .append("was removed").append("\n");
                }
            }
        }

        return plain.substring(0, plain.length() - 1);
    }

    private String changeRenderingValue(Object value) {
        boolean isComplex = isComplexValue(value);
        String renderedValue;

        if (isComplex) {
            renderedValue = "[complex value]";
        } else if (value instanceof String) {
            renderedValue = "'" + value + "'";
        } else {
            renderedValue = String.valueOf(value);
        }

        return renderedValue;
    }

    private boolean isComplexValue(Object value) {
        return (Objects.nonNull(value)) && (value instanceof List<?> || value instanceof Map<?, ?>);
    }
}
