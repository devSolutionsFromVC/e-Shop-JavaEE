package com.solution.fromVC.store.exeptions;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Влад on 21.11.2016.
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper{

    private static final Logger logger = Logger.getLogger(CustomExceptionHandler.class.getCanonicalName());
    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context
                    = (ExceptionQueuedEventContext) event.getSource();

            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            try {
                logger.log(Level.INFO, "Custom Exception Handler", t);

                requestMap.put("exceptionMessage", t.getMessage());

                t.printStackTrace(pw);

                requestMap.put("exceptionTrace", sw.toString());
                nav.handleNavigation(fc, null, "/error");
                fc.renderResponse();

//                String viewId = "/error.xhtml";
//                ViewHandler viewHandler = fc.getApplication().getViewHandler();
//                fc.setViewRoot(viewHandler.createView(fc, viewId));
//                fc.getPartialViewContext().setRenderAll(true);
//                fc.renderResponse();

            } finally {
                i.remove();
            }
        }
        getWrapped().handle();
    }
}
