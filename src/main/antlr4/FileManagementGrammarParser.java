// Generated from D:/.ObsidianRepo/CQU/2024Autumn/JAVA/HomeWork/Project2/exp2Code/src/main/antlr4/FileManagementGrammar.g4 by ANTLR 4.13.2
package antlr4;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FileManagementGrammarParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            WS = 1, PATH = 2, COMMAND = 3, ARGUMENT = 4, QUOTED_STRING = 5;
    public static final int
            RULE_commandLine = 0, RULE_command = 1, RULE_argument = 2, RULE_quotedArgument = 3;

    private static String[] makeRuleNames() {
        return new String[]{
                "commandLine", "command", "argument", "quotedArgument"
        };
    }

    public static final String[] ruleNames = makeRuleNames();

    private static String[] makeLiteralNames() {
        return new String[]{
        };
    }

    private static final String[] _LITERAL_NAMES = makeLiteralNames();

    private static String[] makeSymbolicNames() {
        return new String[]{
                null, "WS", "PATH", "COMMAND", "ARGUMENT", "QUOTED_STRING"
        };
    }

    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "FileManagementGrammar.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public FileManagementGrammarParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @SuppressWarnings("CheckReturnValue")
    public static class CommandLineContext extends ParserRuleContext {
        public CommandContext command() {
            return getRuleContext(CommandContext.class, 0);
        }

        public List<ArgumentContext> argument() {
            return getRuleContexts(ArgumentContext.class);
        }

        public ArgumentContext argument(int i) {
            return getRuleContext(ArgumentContext.class, i);
        }

        public List<QuotedArgumentContext> quotedArgument() {
            return getRuleContexts(QuotedArgumentContext.class);
        }

        public QuotedArgumentContext quotedArgument(int i) {
            return getRuleContext(QuotedArgumentContext.class, i);
        }

        public CommandLineContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_commandLine;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).enterCommandLine(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).exitCommandLine(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FileManagementGrammarVisitor)
                return ((FileManagementGrammarVisitor<? extends T>) visitor).visitCommandLine(this);
            else return visitor.visitChildren(this);
        }
    }

    public final CommandLineContext commandLine() throws RecognitionException {
        CommandLineContext _localctx = new CommandLineContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_commandLine);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(8);
                command();
                setState(13);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 52L) != 0)) {
                    {
                        setState(11);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case PATH:
                            case ARGUMENT: {
                                setState(9);
                                argument();
                            }
                            break;
                            case QUOTED_STRING: {
                                setState(10);
                                quotedArgument();
                            }
                            break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(15);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class CommandContext extends ParserRuleContext {
        public TerminalNode COMMAND() {
            return getToken(FileManagementGrammarParser.COMMAND, 0);
        }

        public CommandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_command;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).enterCommand(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).exitCommand(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FileManagementGrammarVisitor)
                return ((FileManagementGrammarVisitor<? extends T>) visitor).visitCommand(this);
            else return visitor.visitChildren(this);
        }
    }

    public final CommandContext command() throws RecognitionException {
        CommandContext _localctx = new CommandContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_command);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(16);
                match(COMMAND);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class ArgumentContext extends ParserRuleContext {
        public TerminalNode ARGUMENT() {
            return getToken(FileManagementGrammarParser.ARGUMENT, 0);
        }

        public TerminalNode PATH() {
            return getToken(FileManagementGrammarParser.PATH, 0);
        }

        public ArgumentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_argument;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).enterArgument(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).exitArgument(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FileManagementGrammarVisitor)
                return ((FileManagementGrammarVisitor<? extends T>) visitor).visitArgument(this);
            else return visitor.visitChildren(this);
        }
    }

    public final ArgumentContext argument() throws RecognitionException {
        ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_argument);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(18);
                _la = _input.LA(1);
                if (!(_la == PATH || _la == ARGUMENT)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    @SuppressWarnings("CheckReturnValue")
    public static class QuotedArgumentContext extends ParserRuleContext {
        public TerminalNode QUOTED_STRING() {
            return getToken(FileManagementGrammarParser.QUOTED_STRING, 0);
        }

        public QuotedArgumentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_quotedArgument;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).enterQuotedArgument(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof FileManagementGrammarListener)
                ((FileManagementGrammarListener) listener).exitQuotedArgument(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof FileManagementGrammarVisitor)
                return ((FileManagementGrammarVisitor<? extends T>) visitor).visitQuotedArgument(this);
            else return visitor.visitChildren(this);
        }
    }

    public final QuotedArgumentContext quotedArgument() throws RecognitionException {
        QuotedArgumentContext _localctx = new QuotedArgumentContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_quotedArgument);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(20);
                match(QUOTED_STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\u0004\u0001\u0005\u0017\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001" +
                    "\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000" +
                    "\u0001\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001" +
                    "\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001" +
                    "\u0003\u0000\u0000\u0004\u0000\u0002\u0004\u0006\u0000\u0001\u0002\u0000" +
                    "\u0002\u0002\u0004\u0004\u0014\u0000\b\u0001\u0000\u0000\u0000\u0002\u0010" +
                    "\u0001\u0000\u0000\u0000\u0004\u0012\u0001\u0000\u0000\u0000\u0006\u0014" +
                    "\u0001\u0000\u0000\u0000\b\r\u0003\u0002\u0001\u0000\t\f\u0003\u0004\u0002" +
                    "\u0000\n\f\u0003\u0006\u0003\u0000\u000b\t\u0001\u0000\u0000\u0000\u000b" +
                    "\n\u0001\u0000\u0000\u0000\f\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001" +
                    "\u0000\u0000\u0000\r\u000e\u0001\u0000\u0000\u0000\u000e\u0001\u0001\u0000" +
                    "\u0000\u0000\u000f\r\u0001\u0000\u0000\u0000\u0010\u0011\u0005\u0003\u0000" +
                    "\u0000\u0011\u0003\u0001\u0000\u0000\u0000\u0012\u0013\u0007\u0000\u0000" +
                    "\u0000\u0013\u0005\u0001\u0000\u0000\u0000\u0014\u0015\u0005\u0005\u0000" +
                    "\u0000\u0015\u0007\u0001\u0000\u0000\u0000\u0002\u000b\r";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}