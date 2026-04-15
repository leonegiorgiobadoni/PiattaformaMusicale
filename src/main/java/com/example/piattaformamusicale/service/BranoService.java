package com.example.piattaformamusicale.service;

import com.example.piattaformamusicale.model.Brano;
import com.example.piattaformamusicale.repository.BranoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranoService {

    @Autowired
    private BranoRepository branoRepo;

}