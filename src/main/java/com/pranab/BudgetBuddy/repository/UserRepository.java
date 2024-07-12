package com.pranab.BudgetBuddy.repository;

import com.pranab.BudgetBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
