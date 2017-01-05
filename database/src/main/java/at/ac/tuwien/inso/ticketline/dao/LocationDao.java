package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Location}
 */
@Repository
public interface LocationDao extends JpaRepository<Location, Integer>, LocationDaoCustom {

    /**
     * Finds all locations ordered by name, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found locations
     */
    @Query(value = "SELECT l FROM Location l ORDER BY l.name ASC")
    Page<Location> findAllPageable(Pageable pageable);

    /**
     * find all locations by name - no pagination, no ordering
     * @param name of the location
     * @return the list of all found locations with that name
     */

    @Query(value = "SELECT l FROM Location l WHERE lower(l.name) LIKE lower(CONCAT('%',:name,'%'))")
    List<Location> findByName(@Param("name") String name);

    /**
     * Find a Location by its ID
     * @param id ID of location
     * @return Found location or null
     */
    Location findOne(Integer id);
}
