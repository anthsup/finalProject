package by.dziuba.subscription.command;


import by.dziuba.subscription.command.exception.BadRequestException;


public class CommandProvider {

    private CommandProvider() {
    }

    public static Command defineCommand(RequestContent requestContent) throws BadRequestException {
        String commandType = requestContent.getRequestParameter("command");
        if (commandType == null || commandType.isEmpty()){
            throw new BadRequestException("Invalid command type.");
        }

        try {
            CommandType currentCommand = CommandType.valueOf(convertCommandType(commandType));
            return currentCommand.getCommand();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("No such command", e);
        }
    }

    public static String convertCommandType(String commandType) {
        return commandType.replace('-', '_').toUpperCase();
    }
}
