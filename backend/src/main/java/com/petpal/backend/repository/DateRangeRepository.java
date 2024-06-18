package com.petpal.backend.repository;

import com.petpal.backend.domain.DateRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateRangeRepository extends JpaRepository<DateRange, Long> {
}