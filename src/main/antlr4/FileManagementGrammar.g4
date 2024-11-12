grammar FileManagementGrammar;

// 词法规则
WS : [ \t\r\n]+ -> skip ; // 忽略空白字符
PATH : ('/' | 'C:' | 'D:' | 'E:') (~[ \r\n]+) ;
COMMAND : [a-zA-Z]+ ;
ARGUMENT : [a-zA-Z0-9.-]+ ;
QUOTED_STRING : '"' (~["\r\n] )* '"' ;

// 语法规则
commandLine : command (argument | quotedArgument)* ;
command : COMMAND ;
argument : ARGUMENT | PATH ;
quotedArgument : QUOTED_STRING ;
