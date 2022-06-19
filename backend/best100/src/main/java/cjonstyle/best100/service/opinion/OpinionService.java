package cjonstyle.best100.service.opinion;

import cjonstyle.best100.domain.dto.opinion.OpinionReq;
import cjonstyle.best100.domain.dto.opinion.OpinionRes;
import cjonstyle.best100.domain.entity.opinion.Opinion;
import cjonstyle.best100.exception.OpinionNotFoundException;
import cjonstyle.best100.exception.OpinionPasswordNotMatchException;
import cjonstyle.best100.repository.opinion.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpinionService {
    private final OpinionRepo repo;

    public List<OpinionRes> getAllOpinion(String itemId, String state) {
        List<Opinion> opinions = "like".equals(state)
                ? repo.findAllByItemIdOrderByLikeDescIdDesc(itemId) // 좋아요순
                : repo.findAllByItemIdOrderByDateDescIdDesc(itemId); // 날짜순

        return opinions.stream()
                .map(OpinionRes::of)
                .collect(Collectors.toList());
    }

    public OpinionRes saveOpinion(String itemId, OpinionReq req) {
        return Optional.of(itemId)
                .map(id -> Opinion.of(id, req))
                .map(repo::save)
                .map(OpinionRes::of)
                .orElseThrow(RuntimeException::new);
    }

    public OpinionRes updateOpinion(Long opinionId, OpinionReq req) {
        Opinion opinion = getOpinion(opinionId, req);
        OpinionRes dto = OpinionRes.of(opinion);
        dto.setContents(req.getContents());
        return OpinionRes.of(repo.save(Opinion.of(dto)));
    }

    private Opinion getOpinion(Long opinionId, OpinionReq req) {
        Opinion opinion = repo.findById(opinionId).orElseThrow(() -> new OpinionNotFoundException("해당하는 한줄 평이 없습니다."));
        String reqPwd = req.getPwd();
        if (opinion.isPwdNotMatch(reqPwd)) {
            throw new OpinionPasswordNotMatchException("비밀번호가 틀렸습니다.");
        }
        return opinion;
    }

    public boolean deleteOpinion(Long opinionId, OpinionReq req) {
        try {
            Opinion opinion = getOpinion(opinionId, req);
            repo.delete(opinion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public OpinionRes updateExprOpinion(Long opinionId, String expr) {
        Opinion opinion = repo.findById(opinionId)
                .orElseThrow(() -> new OpinionNotFoundException("해당하는 한줄 평이 없습니다."));

        OpinionRes dto = OpinionRes.of(opinion);
        if ("like".equals(expr)) {
            dto.incrementLike();
        } else if ("hate".equals(expr)) {
            dto.incrementHate();
        }
        Opinion updatedOpinion = repo.save(Opinion.of(dto));
        return OpinionRes.of(updatedOpinion);
    }
}
