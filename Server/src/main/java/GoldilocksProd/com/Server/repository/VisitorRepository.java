package GoldilocksProd.com.Server.repository;

import GoldilocksProd.com.Server.projects.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
