package net.drs.myapp.repositpry;

import java.util.Optional;

import net.drs.myapp.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByName(String username);
}
