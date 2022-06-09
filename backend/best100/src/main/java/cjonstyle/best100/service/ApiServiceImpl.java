package cjonstyle.best100.service;


import cjonstyle.best100.repository.ApiRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApiServiceImpl implements ApiService {
    private final ApiRepo repo;

    @Override
    public boolean saveAllBestItem() {
        boolean result = false;
        // DB 저장
        return result;
    }
}
