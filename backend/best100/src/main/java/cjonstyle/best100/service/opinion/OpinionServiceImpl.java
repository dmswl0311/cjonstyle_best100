package cjonstyle.best100.service.opinion;

import cjonstyle.best100.domain.entity.opinion.Opinion;
import cjonstyle.best100.domain.dto.opinion.OpinionReq;
import cjonstyle.best100.domain.dto.opinion.OpinionRes;
import cjonstyle.best100.repository.opinion.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
            opinions = repo.findAllByItemIdOrderByLikeDescIdDesc(itemId); // 좋아요순
        } else {
            opinions = repo.findAllByItemIdOrderByDateDescIdDesc(itemId); // 날짜순
        }
        return opinions.stream().map(OpinionRes::of).collect(Collectors.toList());
    }

    @Override
    public OpinionRes saveOpinion(String itemId, OpinionReq req) {
        return OpinionRes.of(repo.save(Opinion.of(itemId, req)));
    }

    @Override
    public OpinionRes updateOpinion(Long opinionId, OpinionReq req) {
        Optional<Opinion> opinion=getOpinion(opinionId, req);
        OpinionRes dto = OpinionRes.of(opinion.get());
        dto.setContents(req.getContents());
        return OpinionRes.of(repo.save(Opinion.of(dto)));
    }

    private Optional<Opinion> getOpinion(Long opinionId, OpinionReq req) {
        Optional<Opinion> opinion = repo.findById(opinionId);
        if (!opinion.isPresent()) throw new NullPointerException();
        String reqPwd = req.getPwd();
        String oriPwd = opinion.get().getPwd();
        if (!reqPwd.equals(oriPwd)) throw new NullPointerException();
        return opinion;
    }

    @Override
    public boolean deleteOpinion(Long opinionId, OpinionReq req) {
        boolean res = false;
        Optional<Opinion> opinion=getOpinion(opinionId, req);
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
        } else {
            throw new NullPointerException();
        }
        Opinion updateOpinion = repo.save(Opinion.of(dto));
        return OpinionRes.of(updateOpinion);
    }
}
