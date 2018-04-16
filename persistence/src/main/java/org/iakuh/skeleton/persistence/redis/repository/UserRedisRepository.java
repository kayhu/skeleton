package org.iakuh.skeleton.persistence.redis.repository;

import org.iakuh.skeleton.persistence.redis.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRedisRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
