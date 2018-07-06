package org.liquid.common.command;

import org.liquid.common.contant.StringConstant;
import org.liquid.common.util.Blank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author _Chf
 * @since 7/5/2018
 */
public class CommandBuilder {

    private List<String> commands;

    private CommandBuilder() {
        this.commands = new ArrayList<String>();
    }

    private CommandBuilder(String command) {
        this.commands = new ArrayList<String>();
        this.commands.add(command);
    }

    public CommandBuilder command(String command) {
        if (this.commands.size() > 0) {
            throw new IllegalArgumentException("This builder has already set the command");
        }
        this.commands.add(command);
        return this;
    }

    public CommandBuilder withOption(String option) {
        if (this.commands.size() <= 0) {
            throw new IllegalArgumentException("This builder has not set the command yet");
        }
        this.commands.add(option);
        return this;
    }

    public CommandBuilder withParam(String param) {
        if (this.commands.size() <= 0) {
            throw new IllegalArgumentException("This builder has not set the command yet");
        }
        this.commands.add(param);
        return this;
    }

    public List<String> build() {
        return this.commands;
    }

    @Override
    public String toString() {
        if (Blank.isNullOrEmpty(commands)) {
            return StringConstant.EMPTY;
        }
        return String.join(StringConstant.SPACE, commands);
    }

    public static CommandBuilder newBuilder() {
        return new CommandBuilder();
    }

    public static CommandBuilder of(String command) {
        return new CommandBuilder(command);
    }
}
