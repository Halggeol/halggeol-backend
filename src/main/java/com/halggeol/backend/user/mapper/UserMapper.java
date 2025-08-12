package com.halggeol.backend.user.mapper;

import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.user.dto.UserProfileResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // create
    int insert(User user);

    // read
    User findByEmail(String email);

    User findByEmailIncludingDeleted(String email);

    String findNameById(int id);

    User findById(int id);

    List<String> findEmailByNameAndPhone(
        @Param("name") String name,
        @Param("phone") String phone
    );

    List<User> findUsersDeletedBefore(LocalDateTime deletedDate);

    UserProfileResponseDTO getUserProfileByUserId(int id);

    // update
    int updatePasswordByEmail(
        @Param("email") String email,
        @Param("newPassword") String newPassword
    );

    int updateProfileById(
        @Param("id") int id,
        @Param("phone") String phone
    );

    int updateInsightCycleById(
        @Param("id") int id,
        @Param("insightCycle") String insightCycle
    );

    int updateDeletedDateById(int id);

    // delete
    int deleteUserByEmail(String email);

    int deleteUserById(int id);
}
