package com.budgetbuddy.repository;

import com.budgetbuddy.repository.entity.ExpenseEntity;
import com.budgetbuddy.repository.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findAllByUserDetailsOrderByDateDesc(UserEntity userEntity);

    Optional<ExpenseEntity> findByItemId(String itemId);


}
