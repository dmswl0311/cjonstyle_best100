package cjonstyle.best100.service;

import cjonstyle.best100.domain.Opinion;
import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.repository.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService{
    private final OpinionRepo repo;

    @Override
    public List<OpinionRes> getAllOpinion(String itemId) {
        List<Opinion> opinions=repo.findAllByItemIdOrderByDate(itemId);
        return opinions.stream().map(OpinionRes::of).collect(Collectors.toList());
    }
}
