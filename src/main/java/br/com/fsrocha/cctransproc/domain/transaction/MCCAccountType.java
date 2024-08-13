package br.com.fsrocha.cctransproc.domain.transaction;

import br.com.fsrocha.cctransproc.domain.card.AccountType;

import java.util.HashMap;
import java.util.Map;

public class MCCAccountType {
    private static final MCCAccountType INSTANCE = new MCCAccountType();
    private final Map<String, AccountType> mccTypeMap;

    public MCCAccountType() {
        this.mccTypeMap = new HashMap<>();
        initMCC();
    }

    public static MCCAccountType getInstance() {
        return INSTANCE;
    }

    public AccountType getAccountType(String mcc) {
        return mccTypeMap.getOrDefault(mcc, AccountType.CASH);
    }

    private void initMCC() {
        mccTypeMap.put("5411", AccountType.FOOD);
        mccTypeMap.put("5412", AccountType.FOOD);
        mccTypeMap.put("5811", AccountType.MEAL);
        mccTypeMap.put("5812", AccountType.MEAL);
    }
}
