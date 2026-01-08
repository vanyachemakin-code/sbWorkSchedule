package mapper;

import auth.Account;
import auth.AccountModel;

public class AccountMapper {

    public Account modelToEntity(AccountModel model) {
        Account account = new Account();
        account.setLogin(model.getLogin());
        account.setPassword(model.getPassword());
        return account;
    }

    public AccountModel entityToModel(Account account) {
        AccountModel accountModel = new AccountModel();
        accountModel.setLogin(account.getLogin());
        accountModel.setPassword(account.getPassword());
        return accountModel;
    }
}