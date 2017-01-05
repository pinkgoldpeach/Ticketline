package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Carla on 08/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Show}
 */
@Repository
public interface ShowDao extends JpaRepository<Show, Integer>, ShowDaoCustom {

    /**
     * Finds all shows
     * @return the list of all found shows
     */
    @Query(value = "SELECT s FROM Show s ")
    List<Show> findAll();

    /**
     * Finds all shows that are not canceled, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found shows
     */
    @Query(value = "SELECT s FROM Show s WHERE s.canceled = false")
    Page<Show> findAllNotCanceledPageable(Pageable pageable);

    /**
     * Finds all shows, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found shows
     */
    @Query(value = "SELECT s FROM Show s")
    Page<Show> findAllPageable(Pageable pageable);


    /**
     * finds performances at exactly that date and time
     * @param dateOfPerformance date of performance
     * @return the list of all found shows
     */
    @Query(value = "SELECT s FROM Show s WHERE s.dateOfPerformance = :dateOfPerformance AND s.canceled = false")
    List<Show> findByDateAndTime(@Param("dateOfPerformance") Date dateOfPerformance);

    /**
     * performances beginning from that date
     * @param dateOfPerformance the starting point for the search of shows
     * @return the list of all found shows
     */
    @Query(value = "SELECT s FROM Show s WHERE s.dateOfPerformance >= :dateOfPerformance AND s.canceled = false")
    List<Show> findByDateFrom(@Param("dateOfPerformance") Date dateOfPerformance);







}
