package net.sdruskat.blog.foobar.propertytesters;

import net.sdruskat.blog.foobar.FoobarView;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class SelectionTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if ("hasNonEmptyTextSelection".equals(property)) {
			try {
				IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
				if (activePart instanceof FoobarView) {
					FoobarView view = (FoobarView) activePart;
					ISelection viewSiteSelection = view.getViewSite().getSelectionProvider().getSelection();
					if (viewSiteSelection instanceof TextSelection) {
						TextSelection textSelection = (TextSelection) viewSiteSelection;
						if (!textSelection.getText().isEmpty()) {
							return true;
						}
					}
				}
			} catch (Exception e) {
				// Do nothing. Will throw an NPE when the application is closed as there is no longer an active part.
			}
		}
		return false;
	}

}
