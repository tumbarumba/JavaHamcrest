package org.hamcrest.core;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.collection.ArrayMatching;

import java.util.Arrays;

/**
 * Calculates the logical conjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns <code>false</code>.
 *
 * @param <T> the matched value type
 */
public class AllOf<T> extends DiagnosingMatcher<T> {

    private final Iterable<Matcher<? super T>> matchers;

    /**
     * Constructor, best called from {@link #allOf(Matcher[])}.
     * @param matchers the matchers
     * @see #allOf(Matcher[])
     */
    @SafeVarargs
    public AllOf(Matcher<? super T> ... matchers) {
        this(Arrays.asList(matchers));
    }

    /**
     * Constructor, best called from {@link #allOf(Iterable)}.
     * @param matchers the matchers
     * @see #allOf(Iterable)
     */
    public AllOf(Iterable<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Object o, Description mismatch) {
        for (Matcher<? super T> matcher : matchers) {
            if (!matcher.matches(o)) {
                mismatch.appendDescriptionOf(matcher).appendText(" ");
                matcher.describeMismatch(o, mismatch);
              return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("(", " " + "and" + " ", ")", matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param matchers
     *     all the matchers must pass.
     * @return The matcher.
     */
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> matchers) {
        return new AllOf<>(matchers);
    }

    /**
     * Creates a matcher that matches if the examined object matches <b>ALL</b> of the specified matchers.
     * For example:
     * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
     *
     * @param <T>
     *     the matcher type.
     * @param matchers
     *     all the matchers must pass.
     * @return The matcher.
     */
    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return allOf((Iterable) Arrays.asList(matchers));
    }

}
