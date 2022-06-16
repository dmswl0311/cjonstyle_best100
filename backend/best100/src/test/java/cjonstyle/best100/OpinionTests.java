package cjonstyle.best100;

import cjonstyle.best100.domain.dto.opinion.OpinionReq;
import cjonstyle.best100.domain.dto.opinion.OpinionRes;
import cjonstyle.best100.service.opinion.OpinionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpinionTests {
    @Autowired
    private OpinionService service;
    @Test
    @Transactional
    public void getAllOpinionTest() {
        saveOpinionTest();
        String itemId="123";
        String state="like"; //or date
        List<OpinionRes> opinions=service.getAllOpinion(itemId, state);
        Assert.assertEquals(1,opinions.size());
    }

    @Test
    @Transactional
    public void saveOpinionTest() {
        String itemId="123";
        String pwd="pwd";
        String contents="opinion save test";
        OpinionRes opinion=service.saveOpinion(itemId,new OpinionReq(pwd,contents));
        Assert.assertEquals("opinion save test",opinion.getContents());
    }

    @Test
    @Transactional
    public void deleteOpinionTest() {
        String pwd="pwd";
        String contents=null;
        Long opinionId=7L;
        boolean res=service.deleteOpinion(opinionId,new OpinionReq(pwd,contents));
        Assert.assertEquals(true,res);
    }

    @Test
    @Transactional
    public void updateOpinionTest() {
        String pwd="pwd";
        String contents="update test";
        OpinionRes res=service.updateOpinion(7L,new OpinionReq(pwd,contents));
        Assert.assertEquals("update test",res.getContents());
    }
}
