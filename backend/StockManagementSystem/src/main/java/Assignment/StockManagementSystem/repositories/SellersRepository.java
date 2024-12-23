package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Sellers;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellersRepository extends JpaRepository<Sellers,Integer> {

    Optional<Sellers> findByEmail(String email);

    @Query("SELECT s FROM Sellers s " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "s.sellerName LIKE %:searchTerm% OR s.email LIKE %:searchTerm%)")
    Page<Sellers> findSellers(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);




}
