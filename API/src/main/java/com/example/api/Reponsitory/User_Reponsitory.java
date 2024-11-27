package com.example.api.Reponsitory;

import com.example.api.Entity.User;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface User_Reponsitory extends JpaRepository<User,Integer> {
  // @Query("from User u  join fetch u.roles")

   User findByUsername(String name);

   boolean existsByNumberphone(String phone);
   @Query("select u  from User u where u.id=:id")
   User findById(@Param("id") int id);
   @Query("SELECT  u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
   Page<User> findAllUsersWithRoleUser(Pageable pageable);
   ArrayList<User> findByUsernameContaining(String username);
}
