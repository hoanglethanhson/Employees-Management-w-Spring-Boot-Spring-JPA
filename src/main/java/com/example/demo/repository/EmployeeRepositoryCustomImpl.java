package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    @Autowired
    EntityManager entityManager;

    @Override
    public Long getMaxEmpId() {
        try {
            String sql = "SELECT coalesce(max(e.id), 0) FROM Employee e";
            Query query = entityManager.createQuery(sql);
            return (Long) query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    @Override
    public long updateEmployee(Long empId, String fullName, Date hireDate) {
        Employee employee = entityManager.find(Employee.class, empId);
        if (employee == null) {
            return 0;
        }
        employee.setFullName(fullName);
        entityManager.flush();
        return 1;
    }
}
