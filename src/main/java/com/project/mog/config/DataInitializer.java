package com.project.mog.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;
import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.bios.BiosRepository;
import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.auth.AuthRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsersRepository usersRepo;
    private final BiosRepository biosRepo;
    private final AuthRepository authRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usersRepo.count() > 0) return;  // 이미 데이터가 있으면 건너뛰기

        // 1) 유저 엔티티 생성
        UsersEntity u = UsersEntity.builder()
                .usersName("테스트유저1")
                .email("test1@test.com")
                .nickName("테스터")
                .profileImg(null)
                .regDate(LocalDateTime.now())
                .build();
        usersRepo.save(u);

        // 2) Bios 연결
        BiosEntity bios = BiosEntity.builder()
                .user(u)
                .age(30)
                .gender(false)  // 예: false = 여자, true = 남자
                .height(170)
                .weight(65)
                .build();
        biosRepo.save(bios);

        // 3) Auth 연결
        AuthEntity auth = AuthEntity.builder()
                .user(u)
                .password("test1")      // 실제론 BCrypt 암호화 필요!
                .connectTime(LocalDateTime.now())
                .build();
        authRepo.save(auth);

        // (원하는 만큼 반복)
    }
}
