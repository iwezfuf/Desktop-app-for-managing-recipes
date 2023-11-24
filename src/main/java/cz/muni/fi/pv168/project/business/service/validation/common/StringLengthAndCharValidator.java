package cz.muni.fi.pv168.project.business.service.validation.common;


import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import java.util.Set;

public final class StringLengthAndCharValidator extends PropertyValidator<String>  {
    private final int min;
    private final int max;
    private final Set<Character> forbiddenChars = Set.of('<', '>', ';', '\'', '\"', '\\', '/', '&', '#', '@', '{', '}', '[', ']', '(', ')', '^', '$', '%', '*', '!');



    public StringLengthAndCharValidator(int min, int max) {
        this(min, max, null);
    }

    public StringLengthAndCharValidator(int min, int max, String name) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult validate(String string) {
        var result = new ValidationResult();
        var length = string.length();

        if (min > length || length > max) {
            result.add("'%s' length is not between %d (inclusive) and %d (inclusive)"
                    .formatted(getName(), min, max)
            );
        }

        for (char c : string.toCharArray()) {
            if (forbiddenChars.contains(c)) {
                result.add("'%s' contains forbidden character: '%c'".formatted(getName(), c));
                break;
            }
        }

        return result;
    }
}
