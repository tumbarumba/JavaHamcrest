package org.hamcrest;

/**
 * A description of a Matcher. A Matcher will describe itself to a description
 * which can later be used for reporting.
 *
 * @see Matcher#describeTo(Description)
 */
public interface Description {

    /**
     * A description that consumes input but does nothing, implemented by
     * {@link NullDescription}.
     */
    Description NONE = new NullDescription();

    /**
     * Appends some plain text to the description.
     *
     * @param text the text to append.
     * @return the update description when displaying the matcher error.
     */
    Description appendText(String text);

    /**
     * Appends the description of a {@link SelfDescribing} value to this description.
     *
     * @param value the value to append.
     * @return the update description when displaying the matcher error.
     */
    Description appendDescriptionOf(SelfDescribing value);

    /**
     * Appends an arbitrary value to the description.
     *
     * @param value the object to append.
     * @return the update description when displaying the matcher error.
     */
    Description appendValue(Object value);

    /**
     * Appends a list of values to the description.
     *
     * @param <T>       the description type.
     * @param start     the prefix.
     * @param separator the separator.
     * @param end       the suffix.
     * @param values    the values to append.
     * @return the update description when displaying the matcher error.
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    T... values);

    /**
     * Appends a list of values to the description.
     *
     * @param <T>       the description type.
     * @param start     the prefix.
     * @param separator the separator.
     * @param end       the suffix.
     * @param values    the values to append.
     * @return the update description when displaying the matcher error.
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    Iterable<T> values);

    /**
     * Appends a list of {@link org.hamcrest.SelfDescribing} objects
     * to the description.
     *
     * @param start     the prefix.
     * @param separator the separator.
     * @param end       the suffix.
     * @param values    the values to append.
     * @return the update description when displaying the matcher error.
     */
    Description appendList(String start, String separator, String end,
                           Iterable<? extends SelfDescribing> values);

    /**
     * A description that consumes input but does nothing.
     */
    final class NullDescription implements Description {
        /**
         * Constructor.
         */
        public NullDescription() {
        }

        @Override
        public Description appendDescriptionOf(SelfDescribing value) {
            return this;
        }

        @Override
        public Description appendList(String start, String separator,
                                      String end, Iterable<? extends SelfDescribing> values) {
            return this;
        }

        @Override
        public Description appendText(String text) {
            return this;
        }

        @Override
        public Description appendValue(Object value) {
            return this;
        }

        @Override
        public <T> Description appendValueList(String start, String separator,
                                               String end, T... values) {
            return this;
        }

        @Override
        public <T> Description appendValueList(String start, String separator,
                                               String end, Iterable<T> values) {
            return this;
        }

        @Override
        public String toString() {
            return "";
        }
    }

}
