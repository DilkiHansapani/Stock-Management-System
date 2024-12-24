package Assignment.StockManagementSystem.repositories;

import Assignment.StockManagementSystem.models.Materials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials,Integer> {

    @Query("SELECT m FROM Materials m " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "m.materialType LIKE %:searchTerm% OR m.materialName LIKE %:searchTerm%)")
    Page<Materials> findMaterials(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    Optional<Materials> findByMaterialNameAndMaterialType(String materialName, String materialType);
}
