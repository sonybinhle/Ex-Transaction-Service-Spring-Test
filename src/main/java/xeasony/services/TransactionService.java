package xeasony.services;

import org.springframework.stereotype.Service;
import xeasony.models.entities.Transaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    List<Transaction> transactions;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    @Override
    public List<Transaction> findAll() {
        return transactions;
    }

    @Override
    public Transaction findOneById(Long id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Long> findByType(String type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType().equals(type))
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalAmount(Transaction transaction) {
        Map<Transaction, List<Transaction>> map = transactions
                                .stream()
                                .filter(item -> item.getParentId() != null)
                                .collect(Collectors.groupingBy(
                                        item -> findOneById(item.getParentId())
                                ));

        return getTotalAmountByTransaction(transaction, map);
    }

    @Override
    public void save(Long id, Transaction transaction) {
        transaction.setId(id);
        save(transaction);
    }

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void update(Transaction existed, Transaction transaction) {
        existed.setAmount(transaction.getAmount());
        existed.setType(transaction.getType());
        existed.setParentId(transaction.getParentId());
    }

    private BigDecimal getTotalAmountByTransaction(Transaction transaction, Map<Transaction, List<Transaction>> map) {
        List<Transaction> childTransactions = map.get(transaction);
        BigDecimal childAmount = new BigDecimal(0);

        if (childTransactions != null) {
            childAmount = childTransactions
                            .stream()
                            .map(
                                child -> getTotalAmountByTransaction(child, map)
                            )
                            .reduce(new BigDecimal(0), BigDecimal::add);
        }

        return new BigDecimal(transaction.getAmount()).add(childAmount);
    }
}
