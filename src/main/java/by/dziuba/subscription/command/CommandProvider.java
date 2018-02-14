package by.dziuba.subscription.command;


import by.dziuba.subscription.constant.MessageConstant;
import by.dziuba.subscription.exception.BadRequestException;
import by.dziuba.subscription.constant.ParameterConstant;
import by.dziuba.subscription.util.MessageManager;


public class CommandProvider {

    private CommandProvider() {
    }

    public static Command defineCommand(RequestContent requestContent) throws BadRequestException {
        String commandType = requestContent.getRequestParameter(ParameterConstant.COMMAND);
        if (commandType == null || commandType.isEmpty()){
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            String msg = MessageManager.getMessage(MessageConstant.INVALID_COMMAND, locale);
            throw new BadRequestException(msg);
        }

        try {
            CommandType currentCommand = CommandType.valueOf(convertCommandType(commandType));
            return currentCommand.getCommand();
        } catch (IllegalArgumentException e) {
            String locale = (String) requestContent.getSessionAttribute(ParameterConstant.LOCALE);
            String msg = MessageManager.getMessage(MessageConstant.COMMAND_NOT_FOUND, locale);
            throw new BadRequestException(msg, e);
        }
    }

    public static String convertCommandType(String commandType) {
        return commandType.replace('-', '_').toUpperCase();
    }
}
