package com.example.rayanlabmanagement.repository;

import com.example.rayanlabmanagement.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

    Test findByTestName(String testName);
}

