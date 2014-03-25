package net.sdruskat.blog.foobar.commands;

import net.sdruskat.blog.foobar.FoobarView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class FoobarHandler extends AbstractHandler {

	@Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        System.out.println("The selected text is: " + getSelection().getText());
        return null;
    }
 
    private TextSelection getSelection() {
        TextSelection selection = null;
        try {              
            IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
            if (part instanceof FoobarView) {
                final FoobarView view = (FoobarView) part;
                selection = (TextSelection) view.getSite().getSelectionProvider().getSelection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selection;
    }
    
}
