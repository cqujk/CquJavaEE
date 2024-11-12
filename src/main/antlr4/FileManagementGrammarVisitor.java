// Generated from D:/.ObsidianRepo/CQU/2024Autumn/JAVA/HomeWork/Project2/exp2Code/src/main/antlr4/FileManagementGrammar.g4 by ANTLR 4.13.2
package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FileManagementGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface FileManagementGrammarVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link FileManagementGrammarParser#commandLine}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCommandLine(FileManagementGrammarParser.CommandLineContext ctx);

    /**
     * Visit a parse tree produced by {@link FileManagementGrammarParser#command}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCommand(FileManagementGrammarParser.CommandContext ctx);

    /**
     * Visit a parse tree produced by {@link FileManagementGrammarParser#argument}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArgument(FileManagementGrammarParser.ArgumentContext ctx);

    /**
     * Visit a parse tree produced by {@link FileManagementGrammarParser#quotedArgument}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitQuotedArgument(FileManagementGrammarParser.QuotedArgumentContext ctx);
}