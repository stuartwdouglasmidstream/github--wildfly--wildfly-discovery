/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.discovery;

import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class EqualsFilterSpec extends FilterSpec {

    private final String attribute;
    private final AttributeValue value;

    EqualsFilterSpec(final String attribute, final AttributeValue value) {
        this.attribute = attribute;
        this.value = value;
    }

    public boolean matchesSimple(final Map<String, AttributeValue> attributes) {
        return value.equals(attributes.get(attribute));
    }

    public boolean matchesMulti(final Map<String, ? extends Collection<AttributeValue>> attributes) {
        final Collection<AttributeValue> collection = attributes.get(attribute);
        return collection != null && collection.contains(value);
    }

    public boolean mayMatch(final Collection<String> attributeNames) {
        return attributeNames.contains(attribute);
    }

    public boolean mayNotMatch(final Collection<String> attributeNames) {
        return true;
    }

    public <P, R, E extends Exception> R accept(Visitor<P, R, E> visitor, P parameter) throws E {
        return visitor.handle(this, parameter);
    }

    /**
     * Get the attribute to compare.
     *
     * @return the attribute to compare (not {@code null})
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Get the value to check for.
     *
     * @return the value to check for (not {@code null})
     */
    public AttributeValue getValue() {
        return value;
    }

    void toString(final StringBuilder builder) {
        builder.append('(');
        FilterSpec.escapeTo(attribute, builder);
        builder.append('=');
        builder.append(value);
        builder.append(')');
    }
}
