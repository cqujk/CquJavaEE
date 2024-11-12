// Generated from D:/.ObsidianRepo/CQU/2024Autumn/JAVA/HomeWork/Project2/exp2Code/src/main/antlr4/FileManagementGrammar.g4 by ANTLR 4.13.2
package antlr4;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link FileManagementGrammarVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class FileManagementGrammarBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements FileManagementGrammarVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitCommandLine(FileManagementGrammarParser.CommandLineContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitCommand(FileManagementGrammarParser.CommandContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitArgument(FileManagementGrammarParser.ArgumentContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitQuotedArgument(FileManagementGrammarParser.QuotedArgumentContext ctx) {
        return visitChildren(ctx);
    }
}