package net.fortytwo.ripple.libs.control;

import net.fortytwo.flow.Sink;
import net.fortytwo.ripple.RippleException;
import net.fortytwo.ripple.model.ModelConnection;
import net.fortytwo.ripple.model.Operator;
import net.fortytwo.ripple.model.PrimitiveStackMapping;
import net.fortytwo.ripple.model.RippleList;
import net.fortytwo.ripple.model.StackMappingWrapper;
import net.fortytwo.ripple.model.regex.OptionalQuantifier;


/**
 * A primitive which activates ("applies") the topmost item on the stack any
 * number of times.
 *
 * @author Joshua Shinavier (http://fortytwo.net)
 */
public class OptionApply extends PrimitiveStackMapping {
    private static final String[] IDENTIFIERS = {
            // Note: this primitive different semantics than its predecessor, stack:optApply
            ControlLibrary.NS_2013_03 + "option-apply"};

    public String[] getIdentifiers() {
        return IDENTIFIERS;
    }

    public OptionApply() {
        super();
    }

    public Parameter[] getParameters() {
        return new Parameter[]{
                new Parameter("p", "the program to be executed", true)};
    }

    public String getComment() {
        return "p  =>  p?  -- optionally execute the program p";
    }

    public void apply(final RippleList arg,
                      final Sink<RippleList> solutions,
                      final ModelConnection mc) throws RippleException {

        Object first = arg.getFirst();
        final RippleList rest = arg.getRest();

        Sink<Operator> opSink = op -> solutions.accept(rest.push(
                new StackMappingWrapper(new OptionalQuantifier(op), mc)));

        Operator.createOperator(first, opSink, mc);
    }
}
