package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dto.UsedSeatsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrick on 20/05/16.
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner
 *
 * @deprecated
 */
@Service
public class SimpleSeatProcessingServiceMemory { //implements SeatProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSeatProcessingServiceMemory.class);

    /**
     * First integer = ShowId
     * Second Integer = SeatId
     * String = username of Emplyee
     */
    private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, String>> processingSeats = new ConcurrentHashMap<>();

    /**
     * Returns the logged in username
     *
     * @return username of logged in person
     */
    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    /**
     * {@inheritDoc}
     */
    //@Override
    public boolean checkProcessingSeats(int showId, List<Integer> seats) {
        LOGGER.info("checkProcessingSeats() called for show {} and seats: {}", showId, seats);
        if (processingSeats.containsKey(showId)) {
            ConcurrentHashMap<Integer, String> storedSeats = processingSeats.get(showId);
            //String username = getUserName();
            for (Integer idToCheck : seats) {
                if (storedSeats.containsKey(idToCheck)) {
                    //Check if requester is user himself
                    //if (!storedSeats.get(idToCheck).equals(storedSeats)) {
                    LOGGER.debug("checkProcessingSeats() seat {} already in processing mode", idToCheck);
                    return false;
                    //}
                }
            }
        }
        LOGGER.debug("checkProcessingSeats() all seats are free");
        return true;
    }


    /**
     * {@inheritDoc}
     */
    //@Override
    public boolean addToProcessingSeats(UsedSeatsDto usedSeatsDto) {
        return addToProcessingSeats(usedSeatsDto.getShowId(), usedSeatsDto.getIds());
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    @Transactional
    public boolean addToProcessingSeats(int showId, List<Integer> seats) {
        LOGGER.info("addToProcessingSeats() called for showId {} and seats: ", showId, seats);

        if (!checkProcessingSeats(showId, seats)) {
            return false;
        }

        String username = getUserName();

        // add username and seats which the client want to process to a hashmap
        ConcurrentHashMap<Integer, String> hashMap = new ConcurrentHashMap<>();


        if(processingSeats.containsKey(showId))
            hashMap = processingSeats.get(showId);

        //if(hashMap.containsKey(showId))
         //   hashMap = processingSeats.get(showId);


        for (Integer integer : seats) {
            hashMap.put(integer, username);
        }

        LOGGER.info("addToProcessingSeats() all seats put in processing mode");
        processingSeats.put(showId, hashMap);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    @Transactional
    public void removeProcessingSeatsForUser(int showId) {
        String username = getUserName();
        LOGGER.info("removeProcessingSeatsForUser() called for User {} and Show {}", username, showId);

        if (processingSeats.containsKey(showId)) {
            ConcurrentHashMap<Integer, String> hashMap = processingSeats.get(showId);
            if (hashMap.containsValue(username)) {
                LOGGER.info("addToProcessingSeats() User {} has seats to be removed", username);
                hashMap.keySet().stream().filter(seatId -> hashMap.get(seatId).equals(username)).forEach(hashMap::remove);
            } else {
                LOGGER.info("addToProcessingSeats() User {} has no seats to be removed", username);
            }
        } else {
            LOGGER.info("addToProcessingSeats() Show {} has no seats to be removed", showId);
        }
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    public UsedSeatsDto getProcessingSeats(int showId) {
        LOGGER.info("getProcessingSeats() called for showId {}", showId);

        if (processingSeats.containsKey(showId)) {
            return new UsedSeatsDto(Collections.list(processingSeats.get(showId).keys()));
        }
        return new UsedSeatsDto();
    }


}
