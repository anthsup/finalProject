package by.dziuba.subscription.command.impl.user;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.command.JspResourceManager;
import by.dziuba.subscription.command.RequestContent;

public class ShowSignUpCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(JspResourceManager.SIGNUP_PAGE);
    }
}
