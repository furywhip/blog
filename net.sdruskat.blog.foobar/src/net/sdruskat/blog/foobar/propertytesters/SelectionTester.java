package net.sdruskat.blog.foobar.propertytesters;

import java.util.Collection;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.TextSelection;

public class SelectionTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
	    if ("hasNonEmptyTextSelection".equals(property)) {
	        if (receiver instanceof Collection) {
	            @SuppressWarnings("unchecked")
	            Collection<Object> receiverCollection = (Collection<Object>) receiver;
	            TextSelection selection;
	            if (receiverCollection.toArray().length != 0 && receiverCollection.toArray()[0] instanceof TextSelection)  {
	            	selection = (TextSelection) receiverCollection.toArray()[0];
	                if (!selection.getText().equals("")) {
	                	return true;
	                }
	            }
	        }
	    }
	    return false;
	}

}
