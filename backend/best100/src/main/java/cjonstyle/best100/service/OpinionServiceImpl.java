package cjonstyle.best100.service;

import cjonstyle.best100.domain.Opinion;
import cjonstyle.best100.domain.dto.Opinion.OpinionReq;
import cjonstyle.best100.domain.dto.Opinion.OpinionRes;
import cjonstyle.best100.repository.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {
    private final OpinionRepo repo;

    @Override
    public List<OpinionRes> getAllOpinion(String itemId, String state) {
        List<Opinion> opinions = null;
        if ("like".equals(state)) {
            opinions = repo.findAllByItemIdOrderByLikeDesc(itemId); // 좋아요순
        } else {
            opinions = repo.findAllByItemIdOrderByDateDesc(itemId); // 날짜순
        }
        return opinions.stream().map(OpinionRes::of).collect(Collectors.toList());
    }

    @Override
    public OpinionRes saveOpinion(String itemId, OpinionReq req) {
        Opinion opinion = repo.save(Opinion.of(itemId, req));
        return OpinionRes.of(opinion);
    }

    @Override
    public OpinionRes updateOpinion(Long opinionId, OpinionReq req) {
        // 중복되는 부분
        Optional<Opinion> opinion = repo.findById(opinionId);
        if (!opinion.isPresent()) throw new NullPointerException();
        String reqPwd = req.getPwd();
        String oriPwd = opinion.get().getPwd();
        if (!reqPwd.equals(oriPwd)) throw new NullPointerException();
        OpinionRes dto = OpinionRes.of(opinion.get());
        dto.setContents(req.getContents());
        Opinion updateOpinion = repo.save(Opinion.of(dto));
        return OpinionRes.of(updateOpinion);
    }

    @Override
    public boolean deleteOpinion(Long opinionId, OpinionReq req) {
        boolean res = false;
        Optional<Opinion> opinion = repo.findById(opinionId);
        if (!opinion.isPresent()) throw new NullPointerException();
        String reqPwd = req.getPwd();
        String oriPwd = opinion.get().getPwd();
        if (!reqPwd.equals(oriPwd)) throw new NullPointerException();
        try {
            repo.delete(opinion.get());
            res = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public OpinionRes updateExprOpinion(Long opinionId, String expr) {
        Optional<Opinion> opinion = repo.findById(opinionId);
        if (!opinion.isPresent()) throw new NullPointerException();
        OpinionRes dto = OpinionRes.of(opinion.get());
        if ("like".equals(expr)) {
            dto.setLike(dto.getLike() + 1);
        } else if ("hate".equals(expr)) {
            dto.setHate(dto.getHate() + 1);
        }else {
            throw new NullPointerException();
        }
        Opinion updateOpinion = repo.save(Opinion.of(dto));
        return OpinionRes.of(updateOpinion);
    }
}
