package org.zwobble.mammoth.styles;

import org.zwobble.mammoth.documents.Paragraph;
import org.zwobble.mammoth.documents.Style;

import java.util.Optional;
import java.util.function.BiPredicate;

public class ParagraphMatcher {
    public static final ParagraphMatcher ANY = new ParagraphMatcher(Optional.empty(), Optional.empty());

    public static ParagraphMatcher styleId(String styleId) {
        return new ParagraphMatcher(Optional.of(styleId), Optional.empty());
    }

    public static ParagraphMatcher styleName(String styleName) {
        return new ParagraphMatcher(Optional.empty(), Optional.of(styleName));
    }

    private final Optional<String> styleId;
    private final Optional<String> styleName;

    public ParagraphMatcher(Optional<String> styleId, Optional<String> styleName) {
        this.styleId = styleId;
        this.styleName = styleName;
    }

    public boolean matches(Paragraph paragraph) {
        return matchesStyleId(paragraph) && matchesStyleName(paragraph);
    }

    private boolean matchesStyleId(Paragraph paragraph) {
        return matches(styleId, paragraph.getStyle().map(Style::getStyleId), Object::equals);
    }

    private boolean matchesStyleName(Paragraph paragraph) {
        return matches(styleName, paragraph.getStyle().flatMap(Style::getName), String::equalsIgnoreCase);
    }

    private boolean matches(Optional<String> required, Optional<String> actual, BiPredicate<String, String> areEqual) {
        return required
            .map(requiredValue -> actual.map(actualValue -> areEqual.test(requiredValue, actualValue)).orElse(false))
            .orElse(true);
    }
}
