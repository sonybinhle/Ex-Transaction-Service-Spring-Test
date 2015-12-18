package xeasony.services;

import xeasony.models.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {
    List<Transaction> findAll();

    Transaction findOneById(Long id);

    List<Long> findByType(String type);

    BigDecimal getTotalAmount(Transaction transaction);

    void save(Long id, Transaction transaction);

    void save(Transaction transaction);

    void update(Transaction existed, Transaction transaction);
}
