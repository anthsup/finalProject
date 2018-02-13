package by.dziuba.subscription.command.impl.cart;

import by.dziuba.subscription.command.Command;
import by.dziuba.subscription.command.CommandResult;
import by.dziuba.subscription.constant.JspPath;
import by.dziuba.subscription.command.RequestContent;

public class ShowCartCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(JspPath.CART_PAGE);
    }
}
