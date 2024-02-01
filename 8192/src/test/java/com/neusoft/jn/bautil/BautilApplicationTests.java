package com.neusoft.jn.bautil;

import com.qiyuan.bautil.BautilApplication;
import com.qiyuan.bautil.test.Address;
import com.qiyuan.bautil.test.Country;
import com.qiyuan.bautil.test.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {BautilApplication.class})
class BautilApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void test1() {
        User user = new User();
        String isocode = user.getAddress().getCountry().getIsocode().toUpperCase();
        System.out.println(isocode);
    }

    @Test
    void test2() {
        User user = new User();
        if (user != null) {
            Address address = user.getAddress();
            if (address != null) {
                Country country = address.getCountry();
                if (country != null) {
                    String isocode = country.getIsocode();
                    if (isocode != null) {
                        isocode = isocode.toUpperCase();
                        System.out.println(isocode);
                    }
                }
            }
        }
    }

    @Test
    void test3() {
        User user = new User();
        String result = Optional.ofNullable(user)
                .map(u1 -> u1.getAddress())
                .map(a1 -> a1.getCountry())
                .map(c1 -> c1.getIsocode())
                .orElse("default");
        assertEquals(result, "default");
    }

    @Test
    void test4() {
        User user = null;
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isPresent()) {
            System.out.println("OK");
        } else {
            System.out.println("ERROR");
        }
    }

    @Test
    void test5() {
        User user = new User();
        String result = Optional.ofNullable(user)
                .map(User::getAddress)
                .map(Address::getCountry)
                .map(Country::getIsocode)
                .orElse("default");
        assertEquals(result, "default");
    }
}
