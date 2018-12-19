package com.dompine.himitsu;

import com.dompine.himitsu.dao.PostDao;
import com.dompine.himitsu.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HimitsuApplicationTests {
    @Autowired
    PostDao postDao;
    @Test
    public void contextLoads() {
    }

}
