package com.Assignment.notification.service;

import com.Assignment.notification.services.blacklistRedisService.BlacklistServiceImpl;
import com.Assignment.notification.services.blacklistRedisService.RedisService;
import com.Assignment.notification.services.blacklistRedisService.RedisServiceImpl;
import com.Assignment.notification.services.otherServices.HelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BlacklistServiceTest {

    @Mock
    private RedisService theRedisService;

    @Mock
    private HelperService theHelperService;

    @InjectMocks
    private BlacklistServiceImpl underTest;

    @Test
    void blockNumberTest() {
        String number1="+919191919191";
        String number2="+919191919192";
        List<String>list=new ArrayList<>();
        list.add(number1);
        list.add(number2);
        underTest.addToBlacklist(list);

    }

    @Test
    void removeNumberTest() {
        String number1="+919191919191";
        String number2="+919191919192";
        List<String>list=new ArrayList<>();
        list.add(number1);
        list.add(number2);
        underTest.whitelist(list);
    }

    @Test
    void getAllTest() {
        Set<String> set=new HashSet<>();
        set.add("+919191919191");
        when(theRedisService.getAllRedis()).thenReturn(set);
        Set<String> num = new HashSet<>();
        Iterator<String> it= set.iterator();
        Set<String>al=underTest.getAll();
        while(it.hasNext()) {
            num.add(it.next());
        }
        assertThat(num.equals(al)).isTrue();
    }

    @Test
    void isBlockedTest() {
        String num="+919191919191";
        when(theRedisService.CheckIfBlacklistedRedis("+919191919191")).thenReturn(true);
        underTest.checkIfBlackListed(num);

    }
}
