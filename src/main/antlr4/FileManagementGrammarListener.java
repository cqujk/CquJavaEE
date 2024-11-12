// Generated from D:/.ObsidianRepo/CQU/2024Autumn/JAVA/HomeWork/Project2/exp2Code/src/main/antlr4/FileManagementGrammar.g4 by ANTLR 4.13.2
package antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FileManagementGrammarParser}.
 */
public interface FileManagementGrammarListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link FileManagementGrammarParser#commandLine}.
     *
     * @param ctx the parse tree
     */
    void enterCommandLine(FileManagementGrammarParser.CommandLineContext ctx);

    /**
     * Exit a parse tree produced by {@link FileManagementGrammarParser#commandLine}.
     *
     * @param ctx the parse tree
     */
    void exitCommandLine(FileManagementGrammarParser.CommandLineContext ctx);

    /**
     * Enter a parse tree produced by {@link FileManagementGrammarParser#command}.
     *
     * @param ctx the parse tree
     */
    void enterCommand(FileManagementGrammarParser.CommandContext ctx);

    /**
     * Exit a parse tree produced by {@link FileManagementGrammarParser#command}.
     *
     * @param ctx the parse tree
     */
    void exitCommand(FileManagementGrammarParser.CommandContext ctx);

    /**
     * Enter a parse tree produced by {@link FileManagementGrammarParser#argument}.
     *
     * @param ctx the parse tree
     */
    void enterArgument(FileManagementGrammarParser.ArgumentContext ctx);

    /**
     * Exit a parse tree produced by {@link FileManagementGrammarParser#argument}.
     *
     * @param ctx the parse tree
     */
    void exitArgument(FileManagementGrammarParser.ArgumentContext ctx);

    /**
     * Enter a parse tree produced by {@link FileManagementGrammarParser#quotedArgument}.
     *
     * @param ctx the parse tree
     */
    void enterQuotedArgument(FileManagementGrammarParser.QuotedArgumentContext ctx);

    /**
     * Exit a parse tree produced by {@link FileManagementGrammarParser#quotedArgument}.
     *
     * @param ctx the parse tree
     */
    void exitQuotedArgument(FileManagementGrammarParser.QuotedArgumentContext ctx);
}