package com.jpmc.midascore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    // JPA gives us basic save, find, delete methods automatically
}
