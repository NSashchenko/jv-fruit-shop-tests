package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final String SEPARATOR = ",";
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;
    private static final int TITLE = 0;

    @Override

    public List<FruitTransaction> parseRecords(List<String> records) {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        for (int i = 1; i < records.size(); i++) {
            String[] array = records.get(i).split(SEPARATOR);
            if (array.length < 3) {
                throw new IllegalArgumentException("Invalid record");
            }
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.getOption(array[OPERATION]));
            transaction.setFruit(array[FRUIT]);
            transaction.setQuantity(Integer.parseInt(array[QUANTITY]));
            fruitTransactionList.add(transaction);
        }
        return fruitTransactionList;
    }

}
