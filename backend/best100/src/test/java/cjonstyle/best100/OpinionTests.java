package cjonstyle.best100;

import cjonstyle.best100.domain.dto.OpinionReq;
import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.service.OpinionServiceImpl;
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
    OpinionServiceImpl service;
    @Test
    @Transactional
    public void getAllOpinionTest() {
        String itemId="123";
        List<OpinionRes> opinions=service.getAllOpinion(itemId);
        Assert.assertEquals(2,opinions.size());
    }

    @Test
    @Transactional
    public void saveOpinionTest() {
        String itemId="1111";
        String pwd="pwd";
        String contents="opinion save test";
        OpinionReq req=new OpinionReq(pwd,contents);
        OpinionRes opinion=service.saveOpinion(itemId,req);
        Assert.assertEquals("opinion save test",opinion.getContents());
    }

    @Test
    @Transactional
    public void updateOpinionTest() {
        String pwd="pwd";
        String contents="update test";
        OpinionReq req=new OpinionReq(pwd,contents);
        OpinionRes res=service.updateOpinion(7L,req);
        Assert.assertEquals("update test",res.getContents());
    }
}
