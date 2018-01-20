package by.dziuba.subscription.command;


import by.dziuba.subscription.command.exception.BadRequestException;


public class CommandProvider {
    public Command defineCommand(RequestContent requestContent) throws BadRequestException {
        String commandType = requestContent.getRequestParameter("command");
        Command command;
        if (commandType == null || commandType.isEmpty()){
            throw new BadRequestException("Invalid command type.");
        }

        try {
            CommandType currentCommand = CommandType.valueOf(convertCommandType(commandType));
            command = currentCommand.getCommand();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
        return command;
    }

    private String convertCommandType(String commandType) {
        return commandType.replace('-', '_').toUpperCase();
    }
}
