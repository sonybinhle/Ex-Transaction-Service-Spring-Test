package xeasony.controllerApis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xeasony.exceptions.ResourceNotFoundException;
import xeasony.models.entities.Transaction;
import xeasony.models.dtos.StatusOkDTO;
import xeasony.models.dtos.TotalAmountDTO;
import xeasony.services.ITransactionService;

import java.util.List;

@RestController
@RequestMapping({ "/transactionservice" })
public class TransactionControllerApi {

    ITransactionService transactionService;

    @Autowired
    public TransactionControllerApi(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(path={"/transaction/{id:[\\d]+}"}, method={RequestMethod.PUT})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createOrUpdate(@PathVariable Long id, @Validated @RequestBody Transaction transaction) {
        Transaction existed = transactionService.findOneById(id);

        if (existed != null) {
            transactionService.update(existed, transaction);
            return new ResponseEntity<>(new StatusOkDTO(), HttpStatus.OK);
        } else {
            transactionService.save(id, transaction);
            return new ResponseEntity<>(new StatusOkDTO(), HttpStatus.CREATED);
        }
    }

    @RequestMapping(method={RequestMethod.GET})
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @RequestMapping(path={"/transaction/{id:[\\d]+}"}, method={RequestMethod.GET})
    public Transaction findOneById(@PathVariable Long id) throws Exception {
        Transaction transaction = transactionService.findOneById(id);
        if (transaction == null) {
            throw new ResourceNotFoundException();
        }
        return transaction;
    }

    @RequestMapping(path={"/types/{type}"}, method={RequestMethod.GET})
    public List<Long> findByType(@PathVariable String type) {
        return transactionService.findByType(type);
    }

    @RequestMapping(path={"/sum/{id:[\\d]+}"}, method={RequestMethod.GET})
    public TotalAmountDTO getTotalAmountById(@PathVariable Long id) throws Exception {
        Transaction transaction = transactionService.findOneById(id);
        if (transaction == null) {
            throw new ResourceNotFoundException();
        }
        double totalAmount = transactionService.getTotalAmount(transaction).doubleValue();
        return new TotalAmountDTO(totalAmount);
    }
}
