package com.enrollment.grade.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GpaResult {
    private BigDecimal gpa;
    private BigDecimal totalUnits;
} 