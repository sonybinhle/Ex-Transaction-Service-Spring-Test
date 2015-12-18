package xeasony.validations;

import org.springframework.beans.factory.annotation.Autowired;
import xeasony.services.TransactionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionParentIdValidation implements ConstraintValidator<TransactionParentId, Long> {
    @Autowired
    private TransactionService transactionService;

    @Override
    public void initialize(TransactionParentId paramA) {
    }

    @Override
    public boolean isValid(Long parentId, ConstraintValidatorContext ctx) {
        return parentId == null || (transactionService.findOneById(parentId) != null);
    }

}