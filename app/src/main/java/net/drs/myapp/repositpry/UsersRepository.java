package net.drs.myapp.repositpry;

import java.util.List;
import java.util.Optional;

import net.drs.myapp.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsersnameOrEmail(String Usersname, String email);

    List<Users> findByIdIn(List<Long> UsersIds);

    Optional<Users> findByUsersname(String Usersname);

    Boolean existsByUsersname(String Usersname);

    Boolean existsByEmail(String email);
    
    Optional<Users> findByName(String username);
}
