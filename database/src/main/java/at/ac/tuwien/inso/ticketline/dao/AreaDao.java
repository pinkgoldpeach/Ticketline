package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Carla on 15/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Area}
 */
public interface AreaDao extends JpaRepository<Area, Integer>, AreaDaoCustom {

    /**
     * Finds all areas by page
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found areas
     */
    @Query(value = "SELECT a FROM Area a")
    Page<Area> findAllPageable(Pageable pageable);
}
