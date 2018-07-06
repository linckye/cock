package org.liquid.common.command;

/**
 * 命令枚举
 *
 * @author _Chf
 * @since 7/5/2018
 */
public abstract class Commands {

    /** java命令 */
    public static final String JAVA = "java";

    public static CommandBuilder newJavaCommandBuilder(){
        return CommandBuilder.of(JAVA);
    }
}
