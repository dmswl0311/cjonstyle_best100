package cjonstyle.best100.service;

import cjonstyle.best100.domain.Opinion;
import cjonstyle.best100.domain.dto.OpinionReq;
import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.repository.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {
    private final OpinionRepo repo;

    @Override
    public List<OpinionRes> getAllOpinion(String itemId) {
        List<Opinion> opinions = repo.findAllByItemIdOrderByDate(itemId);
        return opinions.stream().map(OpinionRes::of).collect(Collectors.toList());
    }

    @Override
    public OpinionRes saveOpinion(String itemId, OpinionReq req) {
        Opinion opinion = repo.save(Opinion.of(itemId, req));
        return OpinionRes.of(opinion);
    }

    @Override
    public OpinionRes updateOpinion(Long opinionId, OpinionReq req) {
        Optional<Opinion> opinion=repo.findById(opinionId);
        if(!opinion.isPresent()) throw new NullPointerException();
        String reqPwd=req.getPwd();
        String oriPwd=opinion.get().getPwd();
        if(!reqPwd.equals(oriPwd)) throw new NullPointerException();
        Opinion updateOpinion=repo.save(Opinion.updateOf(OpinionRes.of(opinion.get()),req.getContents()));
        return OpinionRes.of(updateOpinion);
    }

    @Override
    public boolean deleteOpinion(Long opinionId, OpinionReq req) {
        boolean res=false;
        Optional<Opinion> opinion=repo.findById(opinionId);
        if(!opinion.isPresent()) throw new NullPointerException();
        String reqPwd=req.getPwd();
        String oriPwd=opinion.get().getPwd();
        if(!reqPwd.equals(oriPwd)) throw new NullPointerException();
        repo.delete(opinion.get());
        res=true;
        return res;
    }

    @Override
    public OpinionRes updateLikeOpinion(Long opinionId) {
        Optional<Opinion> opinion=repo.findById(opinionId);
        if(!opinion.isPresent()) throw new NullPointerException();
        Opinion updateOpinion=repo.save(Opinion.likeOf(OpinionRes.of(opinion.get())));
        return OpinionRes.of(updateOpinion);
    }


}
