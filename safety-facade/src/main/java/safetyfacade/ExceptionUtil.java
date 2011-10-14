package safetyfacade;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exception-Helper for Safety-Facade. <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public class ExceptionUtil {

    private final static int STACKTRACE_OFFSET = 3;

    /**
     * Convert Exception and nested Exceptions to a Safe Exception (removal of class-References).
     *
     * @param exception
     * @param wrapInException
     * @return Instance of wrapInException with nested Exception-Proxies, if necessary.
     */
    public <X extends BaseException> X getSafeException(Exception exception, Class<X> wrapInException) {

        StackTraceElement[] shortStack = getStackTraceElements();

        try {

            return getSafeExceptionImpl(exception, wrapInException, shortStack);

        } catch (IllegalAccessException e) {
            throw new SafetyFacadeException(e);
        } catch (InstantiationException e) {
            throw new SafetyFacadeException(e);
        } catch (InvocationTargetException e) {
            throw new SafetyFacadeException(e);
        }

    }

    private <X extends BaseException> X getSafeExceptionImpl(Exception exception, Class<X> wrapInException,
            StackTraceElement[] shortStack) throws InstantiationException, IllegalAccessException,
            InvocationTargetException {

        List<Throwable> stack = getExceptionStack(exception);
        Throwable messageContainer = null;
        if (!stack.isEmpty() && stack.get(stack.size() - 1).getClass() == wrapInException) {
            messageContainer = stack.remove(stack.size() - 1);
        }

        NestedProxyException nested = createNestedProxyException(stack);

        X result = createInstance(wrapInException, nested, messageContainer);
        result.setStackTrace(shortStack);

        return result;
    }

    /**
     * Create Instance of Exception
     *
     * @param <X>
     *            SafeException-Subtype
     * @param wrapInException
     * @param result
     * @param nested
     *            Optional: Nested Exception.
     * @return Instance of wrapInException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    private <X extends BaseException> X createInstance(Class<X> wrapInException, NestedProxyException nested,
            Throwable messageContainer) throws InstantiationException, IllegalAccessException,
            InvocationTargetException {

        Constructor<?> constructors[] = wrapInException.getConstructors();
        X result = null;

        for (Constructor<?> constructor : constructors) {

            if (messageContainer != null && hasMessage(messageContainer)) {
                if (nested != null && constructor.getParameterTypes().length == 2
                        && constructor.getParameterTypes()[0] == String.class
                        && constructor.getParameterTypes()[1] == Throwable.class) {
                    return (X) constructor.newInstance(messageContainer.getMessage(), nested);
                }

                if (nested == null && constructor.getParameterTypes().length == 1
                        && constructor.getParameterTypes()[0] == String.class) {
                    return (X) constructor.newInstance(messageContainer.getMessage());
                }

            } else {
                if (nested != null && constructor.getParameterTypes().length == 1
                        && constructor.getParameterTypes()[0] == Throwable.class) {
                    return (X) constructor.newInstance(nested);
                }
            }

        }

        for (Constructor<?> constructor : constructors) {

            if (constructor.getParameterTypes().length == 0) {
                result = (X) constructor.newInstance();
                break;
            }
        }

        if (result == null) {
            throw new SafetyFacadeException("no appropriate constructor for " + wrapInException + " found");
        }

        if (nested != null) {
            result.initCause(nested);
        }
        return result;
    }

    /**
     * @param throwable
     * @return
     */
    private boolean hasMessage(Throwable throwable) {

        if (throwable.getMessage() != null && throwable.getCause() != throwable) {
            return true;
        }

        return false;

    }

    /**
     * @param stack
     *            Exception Stack, Root Cause first.
     * @return nested Exception-Proxy
     */
    private NestedProxyException createNestedProxyException(List<Throwable> stack) {
        NestedProxyException nested = null;
        for (Throwable throwable : stack) {
            if (nested == null) {
                nested = new NestedProxyException(throwable.getMessage(), getExceptionClassName(throwable));
            } else {
                nested = new NestedProxyException(throwable.getMessage(), nested, getExceptionClassName(throwable));
            }
            nested.setStackTrace(throwable.getStackTrace());
        }
        return nested;
    }

    /**
     * @param throwable
     * @return Class-Name as String.
     */
    private String getExceptionClassName(Throwable throwable) {
        if (throwable instanceof NestedProxyException) {
            NestedProxyException nested = (NestedProxyException) throwable;
            return nested.getClassName();
        }
        return throwable.getClass().getName();
    }

    /**
     * @param throwable
     * @return List<Throwable> Exception-Stack in reverse Order (Root Cause first).
     */
    private List<Throwable> getExceptionStack(Throwable throwable) {
        List<Throwable> stack = new ArrayList<Throwable>();
        stack.add(throwable);

        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
            stack.add(cause);
        }
        Collections.reverse(stack);
        return stack;
    }

    /**
     * @return Current Stack-Trace without Facade Stack-Elements.
     */
    private StackTraceElement[] getStackTraceElements() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StackTraceElement[] shortStack = stack;

        if (shortStack.length > STACKTRACE_OFFSET - 1) {
            shortStack = new StackTraceElement[stack.length - STACKTRACE_OFFSET];
            System.arraycopy(stack, STACKTRACE_OFFSET, shortStack, 0, stack.length - STACKTRACE_OFFSET);
        }
        return shortStack;
    }

}
