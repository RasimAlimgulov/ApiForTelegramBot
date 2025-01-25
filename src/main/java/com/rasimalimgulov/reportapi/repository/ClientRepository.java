package com.rasimalimgulov.reportapi.repository;

import com.rasimalimgulov.reportapi.entity.Client;
import com.rasimalimgulov.reportapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
  List<Client> findAllByUser(User user);
}
