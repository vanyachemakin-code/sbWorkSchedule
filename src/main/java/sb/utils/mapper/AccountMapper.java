package sb.utils.mapper;

import sb.auth.Account;
import sb.auth.AccountModel;

public class AccountMapper {

    public Account modelToEntity(AccountModel model) {
        Account account = new Account();
        account.setLogin(model.getLogin());
        account.setPassword(model.getPassword());

        return account;
    }
}