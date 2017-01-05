package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Carla on 08/05/2016.
 */
/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Room}
 */
@Repository
public interface RoomDao extends JpaRepository<Room, Integer>, RoomDaoCustom {

    /**
     * Finds all rooms ordered by name, pagination is possible
     * @param pageable Abstract interface for pagination information.
     * @return the list of all found rooms
     */
    @Query(value = "SELECT r FROM Room r ORDER BY r.name ASC")
    Page<Room> findAllPageable(Pageable pageable);


    /**
     * finds a room by id
     * @param id the id of the room
     * @return one room
     */

    @Override
    Room findOne(Integer id);
}
