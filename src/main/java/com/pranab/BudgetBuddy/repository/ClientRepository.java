package com.pranab.BudgetBuddy.repository;

import com.pranab.BudgetBuddy.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
