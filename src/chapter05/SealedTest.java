package chapter05;

import java.util.ArrayList;
import java.util.HashMap;

class SealedTest {
    static void main() {
        var object = new JSONObject();
        object.put("name", new JSONString("Berry Kim"));
        object.put("salary", new JSONNumber(68_000));
        object.put("is_married", JSONBoolean.FALSE);
        var array = new JSONArray();
        array.add(new JSONNumber(42));
        array.add(JSONNull.INSTANCE);
        object.put("lucky_numbers", array);
        System.out.println(object);
        System.out.println(object.type());
    }
}

sealed interface JSONValue permits JSONArray, JSONObject, JSONPrimitive {
    default String type() {
        if (this instanceof JSONArray) {
            return "Array";
        }
        if (this instanceof JSONObject) {
            return "Object";
        }
        if (this instanceof JSONNumber) {
            return "Number";
        }
        if (this instanceof JSONString) {
            return "String";
        }
        if (this instanceof JSONBoolean) {
            return "Boolean";
        }
        return "null";
    }
}

final class JSONArray extends ArrayList<JSONValue> implements JSONValue {
}

final class JSONObject extends HashMap<String, JSONValue> implements JSONValue {
    @Override
    public String toString() {
        var result = new StringBuilder();
        result.append("{");
        for (var entry : entrySet()) {
            if (result.length() > 1) {
                result.append(",");
            }
            result.append(" \"");
            result.append(entry.getKey());
            result.append("\": ");
            result.append(entry.getValue());
        }
        result.append(" }");
        return result.toString();
    }
}

sealed interface JSONPrimitive extends JSONValue
        permits JSONNumber, JSONString, JSONBoolean, JSONNull {
}

final record JSONNumber(double value) implements JSONPrimitive {
    @Override
    public final String toString() {
        return "" + value;
    }
}

final record JSONString(String value) implements JSONPrimitive {
    @Override
    public final String toString() {
        return "\"" + value.translateEscapes() + "\"";
    }
}

enum JSONBoolean implements JSONPrimitive {
    FALSE, TRUE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

enum JSONNull implements JSONPrimitive {
    INSTANCE;

    @Override
    public String toString() {
        return "null";
    }
}