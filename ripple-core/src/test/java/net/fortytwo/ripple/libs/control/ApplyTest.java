package net.fortytwo.ripple.libs.control;

import net.fortytwo.ripple.test.RippleTestCase;
import org.junit.Test;

/**
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class ApplyTest extends RippleTestCase {
    @Test
    public void testPrimitives() throws Exception {
        assertReducesTo("2 dup apply.", "2 2");
        assertReducesTo("2 dup apply apply apply apply apply.", "2 2");
    }

    @Test
    public void testLists() throws Exception {
        assertReducesTo("() apply.");
        assertReducesTo("2 () apply.", "2");
        assertReducesTo("2 rdf:nil apply.", "2");

        assertReducesTo("1 (2 3) apply.", "1 2 3");
        assertReducesTo("1 (2 dup.) apply.", "1 2 2");
    }

    @Test
    public void testNonprograms() throws Exception {
        assertReducesTo("2 apply.");
        assertReducesTo("1 1 both. apply.");
    }
}
