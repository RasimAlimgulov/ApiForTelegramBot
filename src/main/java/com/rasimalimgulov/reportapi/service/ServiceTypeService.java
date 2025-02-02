package com.rasimalimgulov.reportapi.service;

import com.rasimalimgulov.reportapi.entity.ServiceType;
import com.rasimalimgulov.reportapi.entity.User;
import com.rasimalimgulov.reportapi.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final UserServiceImpl userService;
    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository, UserServiceImpl userService) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.userService = userService;
    }
    public List<ServiceType> getAllServiceTypes(String username) {
        User user = userService.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return serviceTypeRepository.findByUser(user);
    }
    public ServiceType addServiceType(String username,String nameServiceType) {
        User user = userService.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        ServiceType serviceType=new ServiceType();
        serviceType.setUser(user);
        serviceType.setName(nameServiceType);
        return serviceTypeRepository.save(serviceType);
    }
    public ServiceType findServiceTypeByNameAndUser(String serviceTypeName,User user) {
        return serviceTypeRepository.findServiceTypeByNameAndUser(serviceTypeName,user);
    }
}
