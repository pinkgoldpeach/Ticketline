package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
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
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Performance}
 */
@Repository
public interface PerformanceDao extends JpaRepository<Performance, Integer>, PerformanceDaoCustom {

    Performance findOne(Integer id);
    //public Performance findByName(String name);

    /**
     * Finds all performances ordered by name
     * @return the list of all found performances
     */
    @Query(value = "SELECT p FROM Performance p ORDER BY p.name ASC")
    List<Performance> findAllOrderByNameAsc();

    /**
     * Finds all performances ordered by name, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found performances
     */
    @Query(value = "SELECT p FROM Performance p ORDER BY p.name ASC")
    Page<Performance> findAllPageable(Pageable pageable);


    /**
     * Finds performance by name
     * @param name part of firstname
     * @return the list of all found performances
     */
    @Query(value = "SELECT p FROM Performance p WHERE lower(p.name) LIKE lower(CONCAT('%',:name,'%'))")
    List<Performance> findByNameLike(@Param("name") String name);

    /**
     * Finds performances by duration in a range
     * @param minduration beginning of range
     * @param maxduration end of range
     * @return the list of all found performances
     */
    @Query(value = "SELECT p FROM Performance p WHERE p.duration BETWEEN  ?1 AND ?2")
    List<Performance> findByDurationBetween(int minduration, int maxduration);


    /**
     * Finds performance by description
     * @param description part of description
     * @return list of all found performances
     */
    @Query(value = "SELECT p FROM Performance p WHERE lower(p.description) LIKE lower(CONCAT('%',:description,'%'))")
    List<Performance> findByDescription(@Param("description") String description);

    /**
     * Finds performance by performance type
     * @param performanceType type of performance
     * @return list of all found performances
     */
    List<Performance> findByPerformanceType(PerformanceType performanceType);

    // @Query()
   // public List<Performance> findByArtistName();

  //  suchString = "%" + suchString + "%";
   // sql = "SELECET x,y,z FROM abc WHERE LOWER(field) LIKE " + suchString;

    /**SELECT performance.id, performance.description, performance.duration,
     * performance.name, show.canceled, show.dateofperformance
     * FROM Performance JOIN Participation ON performance.id=participation.performance_id
     * JOIN Artist ON artist.id=participation.artist_id
     * JOIN Show ON show.performance_id=performance.id
     * WHERE lower(artist.firstname) LIKE '%anthony%'
     */



}
