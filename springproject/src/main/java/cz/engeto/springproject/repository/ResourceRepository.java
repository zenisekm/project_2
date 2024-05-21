package cz.engeto.springproject.repository;

import cz.engeto.springproject.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
