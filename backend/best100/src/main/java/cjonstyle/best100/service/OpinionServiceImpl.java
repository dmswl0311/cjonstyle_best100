package cjonstyle.best100.service;

import cjonstyle.best100.repository.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService{
    private final OpinionRepo repo;
}
