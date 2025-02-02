package com.rasimalimgulov.reportapi.repository;

import com.rasimalimgulov.reportapi.entity.ServiceType;
import com.rasimalimgulov.reportapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
List<ServiceType> findByUser(User user);
ServiceType findServiceTypeByNameAndUser(String serviceTypeName,User user);
}

