package com.oocl.easyparkbackend.Clerk.Repository;

import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ClerkRepositoryTest {

    @Autowired
    private ManageRepository manageRepository;

    @Test
    public void should_return_clerk_message_when_find_given_name(){
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        List<Manage> manageList =  manageRepository.findAllByNameLike("%fan%");

        assertThat(manageList.get(0).getName()).isEqualTo("st77fan");

    }

    @Test
    public void should_return_clerk_message_when_find_given_email(){
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        List<Manage> manageList = manageRepository.findAllByEmailLike("%555@%");

        assertThat(manageList.get(0).getEmail()).isEqualTo("953188555@qq.com");
    }

    @Test
    public void should_return_clerk_message_when_find_given_phone(){
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        List<Manage> manageList = manageRepository.findAllByPhoneNumberLike("%2545%");

        assertThat(manageList.get(0).getPhoneNumber()).isEqualTo("13192545625");
    }

    @Test
    public void should_return_clerk_message_when_find_given_id(){
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        Manage returnManage = manageRepository.save(manage);

        Manage manageList = manageRepository.findById(returnManage.getId()).get();

        assertThat(manageList.getPhoneNumber()).isEqualTo("13192545625");
    }


}
