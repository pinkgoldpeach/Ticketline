package at.ac.tuwien.inso.ticketline.server.util;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides static converter methods from entities to DTOs
 *
 * @author Patrick Weiszkirchner
 * @author Alexander Poschenreithner (1328924)
 */
public class EntityToDto {

    /**
     * Converts a list of news entities to news DTOs
     *
     * @param news             the list of news entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of news DTOs
     */
    public static List<NewsDto> convertNews(List<News> news, List<Class<?>> convertedClasses) {
        List<NewsDto> ret = new ArrayList<>();
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        if (null != news) {
            for (News n : news) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                NewsDto dto = convert(n, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a news entity to a news DTO.
     *
     * @param news             the news
     * @param convertedClasses a list that contains already converted classes
     * @return the news DTO
     */
    public static NewsDto convert(News news, List<Class<?>> convertedClasses) {
        convertedClasses.add(News.class);
        NewsDto dto = new NewsDto();
        dto.setTitle(news.getTitle());
        dto.setNewsText(news.getNewsText());
        dto.setSubmittedOn(news.getSubmittedOn());
        return dto;
    }

    /**
     * Converts a list of performance entities to performance DTOs
     *
     * @param performances     the list of performance entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of news DTOs
     */
    public static List<PerformanceDto> convertPerformance(List<Performance> performances, List<Class<?>> convertedClasses) {
        List<PerformanceDto> ret = new ArrayList<>();
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        if (null != performances) {
            for (Performance p : performances) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                PerformanceDto dto = convert(p, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a performance entity to a performance DTO.
     *
     * @param performance      the performance
     * @param convertedClasses a list that contains already converted classes
     * @return the performance DTO
     */
    public static PerformanceDto convert(Performance performance, List<Class<?>> convertedClasses) {
        convertedClasses.add(Performance.class);
        PerformanceDto dto = new PerformanceDto();
        dto.setId(performance.getId());
        dto.setName(performance.getName());
        dto.setDuration(performance.getDuration());
        if (performance.getPerformanceType() != null) {
            if ("MOVIE".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.MOVIE);
            } else if ("FESTIVAL".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.FESTIVAL);
            } else if ("CONCERT".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.CONCERT);
            } else if ("MUSICAL".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.MUSICAL);
            } else if ("OPERA".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.OPERA);
            } else if ("THEATER".equals(performance.getPerformanceType().name())) {
                dto.setPerformanceType(PerformanceTypeDto.THEATER);
            }
        }

        if (performance.getDescription() != null)
            dto.setDescription(performance.getDescription());

        /********************* convert shows ******************/
        if (!convertedClasses.contains(Show.class) && performance.getShows() != null) {
            dto.setShows(convertShows(performance.getShows(), convertedClasses));
        }

        /********************* convert participations ******************/
        if (!convertedClasses.contains(Performance.class) && performance.getParticipations() != null) {
            dto.setParticipations(convertParticipations(performance.getParticipations(), convertedClasses));
        }

        return dto;
    }

    public static ParticipationDto convert(Participation participation, List<Class<?>> convertedClasses) {
        convertedClasses.add(Participation.class);
        ParticipationDto dto = new ParticipationDto();

        if (participation.getDescription() != null) {
            dto.setDescription(participation.getDescription());
        }
        if (participation.getArtistRole() != null) {
            dto.setArtistRole(participation.getArtistRole());
        }
        /********************* convert performances ******************/
        if (!convertedClasses.contains(Performance.class) && participation.getPerformance() != null) {
            dto.setPerformance(convert(participation.getPerformance(), convertedClasses));
        }

        /********************* convert artists ******************/

        if (!convertedClasses.contains(Artist.class) && participation.getArtist() != null) {
            dto.setArtist(convert(participation.getArtist(), convertedClasses));
        }

        return dto;
    }

    /**
     * Converts a list of participation entities to participation DTOs
     *
     * @param participations   the list of participation entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of show DTOs
     */
    public static List<ParticipationDto> convertParticipations(List<Participation> participations, List<Class<?>> convertedClasses) {
        List<ParticipationDto> ret = new ArrayList<>();
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        if (null != participations) {
            for (Participation p : participations) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ParticipationDto dto = convert(p, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a list of artist entities to news DTOs
     *
     * @param artists          the list of news entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of artists DTOs
     */
    public static List<ArtistDto> convertArtists(List<Artist> artists, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<ArtistDto> ret = new ArrayList<>();
        if (null != artists) {
            for (Artist a : artists) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ArtistDto dto = convert(a, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts an artist entity to a artist DTO.
     *
     * @param artist           the artist
     * @param convertedClasses a list that contains already converted classes
     * @return the artist DTO
     */
    public static ArtistDto convert(Artist artist, List<Class<?>> convertedClasses) {
        convertedClasses.add(Participation.class);
        ArtistDto dto = new ArtistDto();

        dto.setId(artist.getId());
        dto.setFirstname(artist.getFirstname());
        dto.setLastname(artist.getLastname());
        dto.setDescription(artist.getDescription());

        /********************* convert participations ******************/
        if (!convertedClasses.contains(Participation.class) && artist.getParticipations() != null) {
            dto.setParticipations(convertParticipations(artist.getParticipations(), convertedClasses));
        }
        return dto;
    }

    public static ShowDto convert(Show show, List<Class<?>> convertedClasses) {
        convertedClasses.add(Show.class);
        ShowDto dto = new ShowDto();
        dto.setId(show.getId());
        dto.setCanceled(show.getCanceled());
        dto.setDateOfPerformance(show.getDateOfPerformance());


        /********************* convert room ******************/
        if (!convertedClasses.contains(Room.class) && show.getRoom() != null) {
            dto.setRoom(convert(show.getRoom(), convertedClasses));
        }

        /********************* convert performance ******************/
        if (!convertedClasses.contains(Performance.class) && show.getPerformance() != null) {
            dto.setPerformance(convert(show.getPerformance(), convertedClasses));
        }

        /********************* convert tickets ******************/
        /**
         * deleted this from model and dto, because of bidirectional issues.
         * to get a list of tickets - join tables and make a custom query
         if(!convertedClasses.contains(Ticket.class) && show.getTickets() != null) {
         dto.setTickets(convertTickets(show.getTickets(), convertedClasses));
         }
         */

        return dto;
    }

    /**
     * Converts a list of show entities to show DTOs
     *
     * @param shows            the list of show entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of show DTOs
     */
    public static List<ShowDto> convertShows(List<Show> shows, List<Class<?>> convertedClasses) {
        List<ShowDto> ret = new ArrayList<>();
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        if (null != shows) {
            for (Show s : shows) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ShowDto dto = convert(s, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    public static ShowTopTenDto convert(Show show, Long timesSold, List<Class<?>> convertedClasses) {
        convertedClasses.add(ShowTopTenDto.class);
        ShowTopTenDto showTopTenDto = new ShowTopTenDto();
        showTopTenDto.setTimesSold(timesSold);

        /********************* convert shows ******************/
        if (!convertedClasses.contains(Show.class) && show.getRoom() != null) {
            showTopTenDto.setShowDto(convert(show, convertedClasses));
        }
        return showTopTenDto;
    }

    /**
     * Converts a list of ticket entities to show DTOs
     *
     * @param tickets          the list of ticket entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of ticket DTOs
     */
    public static List<TicketDto> convertTickets(List<Ticket> tickets, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<TicketDto> ret = new ArrayList<>();
        if (null != tickets) {
            for (Ticket t : tickets) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                TicketDto dto = convert(t, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * converts a ticket entity to a ticket dto
     *
     * @param ticket           the ticket entity
     * @param convertedClasses a list that contains already converted classes
     * @return the ticket DTO
     */

    public static TicketDto convert(Ticket ticket, List<Class<?>> convertedClasses) {
        convertedClasses.add(Ticket.class);
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setUuid(ticket.getUuid());
        dto.setValid(ticket.getValid());

        if (ticket.getDescription() != null) {
            dto.setDescription(ticket.getDescription());
        }

        if (ticket.getCancellationReason() != null) {
            dto.setCancellationReason(ticket.getCancellationReason());
        }

        if (ticket.getDescription() != null) {
            dto.setDescription(ticket.getDescription());
        }

        /********************* convert show ******************/

        if (!convertedClasses.contains(Show.class) && ticket.getShow() != null) {
            dto.setShow(convert(ticket.getShow(), convertedClasses));
        }
        /********************* convert seat ******************/
        if (!convertedClasses.contains(Seat.class) && ticket.getSeat() != null) {
            dto.setSeat(convert(ticket.getSeat(), convertedClasses));
        }

        /********************* convert area ******************/

        if (!convertedClasses.contains(Area.class) && ticket.getArea() != null) {
            dto.setArea(convert(ticket.getArea(), convertedClasses));
        }

        /********************* convert reservation ******************/
        if (!convertedClasses.contains(Reservation.class) && ticket.getReservation() != null) {
            dto.setReservation(convert(ticket.getReservation(), convertedClasses));
        }

        return dto;
    }

    /**
     * Converts a seat entity to a seat DTO.
     *
     * @param seat             the seats
     * @param convertedClasses a list that contains already converted classes
     * @return the seats DTO
     */
    public static SeatDto convert(Seat seat, List<Class<?>> convertedClasses) {
        convertedClasses.add(Seat.class);
        SeatDto dto = new SeatDto();
        dto.setId(seat.getId());
        dto.setOrder(seat.getOrder());
        if (seat.getName() != null) {
            dto.setName(seat.getName());
        }

        if (seat.getDescription() != null) {
            dto.setDescription(seat.getDescription());
        }

        return dto;
    }


    /**
     * converts a list of seat entities to seat dtos
     *
     * @param seats            the list of seat entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of seat DTOs
     */
    public static List<SeatDto> convertSeats(List<Seat> seats, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<SeatDto> ret = new ArrayList<>();
        if (null != seats) {
            for (Seat s : seats) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                SeatDto dto = convert(s, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a reservation entity to a reservation DTO.
     *
     * @param reservation      the reservation
     * @param convertedClasses a list that contains already converted classes
     * @return the reservation DTO
     */
    public static ReservationDto convert(Reservation reservation, List<Class<?>> convertedClasses) {
        convertedClasses.add(Reservation.class);
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setReservationNumber(reservation.getReservationNumber());

        /********************* convert customers ******************/
        if (!convertedClasses.contains(Customer.class) && reservation.getCustomer() != null) {
            dto.setCustomer(convert(reservation.getCustomer(), convertedClasses));
        }
        /********************* convert employees ******************/
        if (!convertedClasses.contains(Employee.class) && reservation.getEmployee() != null) {
            dto.setEmployee(convert(reservation.getEmployee(), convertedClasses));
        }

        /********************* convert tickets ******************/
        if (!convertedClasses.contains(Ticket.class) && reservation.getTickets() != null) {
            dto.setTickets(convertTickets(reservation.getTickets(), convertedClasses));
        }

        return dto;
    }

    /**
     * converts a list of reservation entities to reservation dtos
     *
     * @param reservations     the list of reservation entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of reservation DTOs
     */
    public static List<ReservationDto> convertReservations(List<Reservation> reservations, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<ReservationDto> ret = new ArrayList<>();
        if (null != reservations) {
            for (Reservation r : reservations) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ReservationDto dto = convert(r, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a room entity to a room DTO.
     *
     * @param room             the room
     * @param convertedClasses a list that contains already converted classes
     * @return the room DTO
     */
    public static RoomDto convert(Room room, List<Class<?>> convertedClasses) {
        convertedClasses.add(Room.class);
        RoomDto dto = new RoomDto();

        dto.setId(room.getId());
        if (room.getSeatChoice() != null) {
            dto.setSeatChoice(room.getSeatChoice());
        }

        if (room.getName() != null) {
            dto.setName(room.getName());
        }

        if (room.getDescription() != null) {
            dto.setDescription(room.getDescription());
        }

        /********************* convert location ******************/

        if (!convertedClasses.contains(Location.class) && room.getLocation() != null) {
            dto.setLocation(convert(room.getLocation(), convertedClasses));
        }

        /********************* convert shows ******************/
        /*
         if(!convertedClasses.contains(Show.class) && room.getShows() != null) {
            dto.setShows(convertShows(room.getShows(), convertedClasses));
        }

        /*
         * deleted this from model and dto, because of bidirectional issues.
         * to get a list of rows - join tables and make a custom query
         /********************* convert rows ******************/
        /*
         if(!convertedClasses.contains(Row.class) && room.getRows() != null) {
         dto.setRows(convertRows(room.getRows(), convertedClasses));
         }
         if(!convertedClasses.contains(Area.class) && room.getAreas() != null) {
         dto.setAreas(convertAreas(room.getAreas(), convertedClasses));
         }
         */

        return dto;
    }

    /**
     * converts a list of room entitis to room dtos
     *
     * @param rooms            the list of rooms entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of room DTOs
     */
    public static List<RoomDto> convertRooms(List<Room> rooms, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<RoomDto> ret = new ArrayList<>();
        if (null != rooms) {
            for (Room r : rooms) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                RoomDto dto = convert(r, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a row entity to a row DTO.
     *
     * @param row              the row
     * @param convertedClasses a list that contains already converted classes
     * @return the row DTO
     */
    public static RowDto convert(Row row, List<Class<?>> convertedClasses) {
        convertedClasses.add(Row.class);
        RowDto dto = new RowDto();
        dto.setId(row.getId());
        dto.setPrice(row.getPrice());

        dto.setId(row.getId());

        if (row.getName() != null) {
            dto.setName(row.getName());
        }

        if (row.getDescription() != null) {
            dto.setDescription(row.getDescription());
        }

        return dto;
    }

    /**
     * converts a list of row entities to row dtos
     *
     * @param rows             the list of rows entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of row DTOs
     */
    public static List<RowDto> convertRows(List<Row> rows, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<RowDto> ret = new ArrayList<>();
        if (null != rows) {
            for (Row r : rows) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                RowDto dto = convert(r, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a area entity to a area DTO.
     *
     * @param area             the area
     * @param convertedClasses a list that contains already converted classes
     * @return the area DTO
     */
    public static AreaDto convert(Area area, List<Class<?>> convertedClasses) {
        convertedClasses.add(Area.class);
        AreaDto dto = new AreaDto();
        dto.setId(area.getId());
        dto.setTicketAmount(area.getTicketAmount());
        dto.setPrice(area.getPrice());
        if (area.getAreaType() != null) {
            if ("BARRIER_FREE".equals(area.getAreaType().name())) {
                dto.setAreaType(AreaTypeDto.BARRIER_FREE);
            } else if ("VIP".equals(area.getAreaType().name())) {
                dto.setAreaType(AreaTypeDto.VIP);
            } else if ("STANCE".equals(area.getAreaType().name())) {
                dto.setAreaType(AreaTypeDto.STANCE);
            } else if ("FREE_SEATING_CHOICE".equals(area.getAreaType().name())) {
                dto.setAreaType(AreaTypeDto.FREE_SEATING_CHOICE);
            }
        }
        /********************* convert room ******************/
        if (!convertedClasses.contains(Room.class) && area.getRoom() != null) {
            dto.setRoom(convert(area.getRoom(), convertedClasses));
        }

        return dto;
    }

    /**
     * converts a list of area entities to area dtos
     *
     * @param areas            the list of areas entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of area DTOs
     */
    public static List<AreaDto> convertAreas(List<Area> areas, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<AreaDto> ret = new ArrayList<>();
        if (null != areas) {
            for (Area a : areas) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                AreaDto dto = convert(a, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a location entity to a location DTO.
     *
     * @param location         the location
     * @param convertedClasses a list that contains already converted classes
     * @return the location DTO
     */
    public static LocationDto convert(Location location, List<Class<?>> convertedClasses) {
        convertedClasses.add(Location.class);
        LocationDto dto = new LocationDto();

        dto.setId(location.getId());
        dto.setName(location.getName());
        dto.setDescription(location.getDescription());
        dto.setOwner(location.getOwner());

        /********************* convert address ******************/
        if (!convertedClasses.contains(Address.class) && location.getAddress() != null) {
            dto.setAddress(convert(location.getAddress(), convertedClasses));
        }

        /********************* convert rooms ******************/
        if (!convertedClasses.contains(Room.class) && location.getRooms() != null) {
            dto.setRooms(convertRooms(location.getRooms(), convertedClasses));
        }

        return dto;
    }

    /**
     * converts a list of location entities to location dtos
     *
     * @param locations        the list of locations entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of location DTOs
     */
    public static List<LocationDto> convertLocations(List<Location> locations, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<LocationDto> ret = new ArrayList<>();
        if (null != locations) {
            for (Location l : locations) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                LocationDto dto = convert(l, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts a list of receipt entities to receipt DTOs
     *
     * @param receipts         the list of receipt entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of receipt DTOs
     */
    public static List<ReceiptDto> convertReceipts(List<Receipt> receipts, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<ReceiptDto> ret = new ArrayList<>();
        if (null != receipts) {
            for (Receipt r : receipts) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ReceiptDto dto = convert(r, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * converts a receipt entity to a receipt dto
     *
     * @param receipt          the receipt entity
     * @param convertedClasses a list that contains already converted classes
     * @return the receipt DTO
     */

    public static ReceiptDto convert(Receipt receipt, List<Class<?>> convertedClasses) {
        convertedClasses.add(Receipt.class);
        ReceiptDto dto = new ReceiptDto();

        dto.setId(receipt.getId());
        dto.setTransactionDate(receipt.getTransactionDate());
        dto.setPerformanceName(receipt.getPerformanceName());

        if (receipt.getTransactionState() != null) {
            if ("ORDERED".equals(receipt.getTransactionState().name())) {
                dto.setTransactionState(TransactionStateDto.ORDERED);
            } else if ("CANCELED".equals(receipt.getTransactionState().name())) {
                dto.setTransactionState(TransactionStateDto.CANCELED);
            } else if ("IN_PROCESS".equals(receipt.getTransactionState().name())) {
                dto.setTransactionState(TransactionStateDto.IN_PROCESS);
            } else if ("PAID".equals(receipt.getTransactionState().name())) {
                dto.setTransactionState(TransactionStateDto.PAID);
            } else if ("CANCELLED_POSITIONS".equals(receipt.getTransactionState().name())) {
                dto.setTransactionState(TransactionStateDto.CANCELLED_POSITIONS);
            }

        }

        if (!convertedClasses.contains(Customer.class) && receipt.getCustomer() != null) {
            dto.setCustomerDto(convert(receipt.getCustomer(), convertedClasses));
        }

        if (!convertedClasses.contains(Employee.class) && receipt.getEmployee() != null) {
            dto.setEmployeeDto(convert(receipt.getEmployee(), convertedClasses));
        }

        if (!convertedClasses.contains(MethodOfPayment.class) && receipt.getMethodOfPayment() != null) {
            dto.setMethodOfPaymentDto(convert(receipt.getMethodOfPayment(), convertedClasses));
        }

        if (!convertedClasses.contains(ReceiptEntry.class) && receipt.getReceiptEntries() != null) {
            dto.setReceiptEntryDtos(convertReceiptEntries(receipt.getReceiptEntries(), convertedClasses));
        }
        return dto;
    }

    /**
     * Converts a list of receipt entry entities to receipt DTOs
     *
     * @param receiptEntries   the list of receipt entry entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of receipt entry DTOs
     */
    public static List<ReceiptEntryDto> convertReceiptEntries(List<ReceiptEntry> receiptEntries, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<ReceiptEntryDto> ret = new ArrayList<>();
        if (null != receiptEntries) {
            for (ReceiptEntry r : receiptEntries) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                ReceiptEntryDto dto = convert(r, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * converts a receipt entry entity to a receipt entry dto
     *
     * @param receiptEntry     the receipt entry entity
     * @param convertedClasses a list that contains already converted classes
     * @return the receipt entry DTO
     */

    public static ReceiptEntryDto convert(ReceiptEntry receiptEntry, List<Class<?>> convertedClasses) {
        convertedClasses.add(ReceiptEntry.class);
        ReceiptEntryDto dto = new ReceiptEntryDto();
        dto.setId(receiptEntry.getId());
        dto.setAmount(receiptEntry.getAmount());
        dto.setPosition(receiptEntry.getPosition());
        dto.setUnitPrice(receiptEntry.getUnitPrice());

        if (!convertedClasses.contains(Receipt.class) && receiptEntry.getReceipt() != null) {
            dto.setReceiptDto(convert(receiptEntry.getReceipt(), convertedClasses));
        }
        if (!convertedClasses.contains(Ticket.class) && receiptEntry.getTicket() != null) {
            //dto.setTicketDto(convert(receiptEntry.getTicket(), convertedClasses));
            dto.setTicketId(receiptEntry.getTicket().getId());
        }

        return dto;
    }

    /**
     * converts a MethodOfPayment entity to a MethodOfPayment  dto
     *
     * @param methodOfPayment  the methodOfPayment  entity
     * @param convertedClasses a list that contains already converted classes
     * @return the Method Of Payment DTO
     */

    public static MethodOfPaymentDto convert(MethodOfPayment methodOfPayment, List<Class<?>> convertedClasses) {
        convertedClasses.add(MethodOfPayment.class);
        MethodOfPaymentDto dto = new MethodOfPaymentDto();
        dto.setId(methodOfPayment.getId());

        if (methodOfPayment instanceof StripePayment)
            dto.setStripeDto(new StripeDto());

        return dto;
    }


    /**
     * converts a list of employee entities to employee dtos
     *
     * @param employees        the list of employees entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of employees DTOs
     */
    public static List<EmployeeDto> convertEmployees(List<Employee> employees, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<EmployeeDto> ret = new ArrayList<>();
        if (null != employees) {
            for (Employee e : employees) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                EmployeeDto dto = convert(e, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts an employee entity to an employee DTO
     *
     * @param employee         the employee entity
     * @param convertedClasses a list that contains already converted classes
     * @return the employee DTO
     */
    public static EmployeeDto convert(Employee employee, List<Class<?>> convertedClasses) {
        convertedClasses.add(Employee.class);
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(employee.getFirstname());
        dto.setLastName(employee.getLastname());
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setEmail(employee.getEmail());
        dto.setEmployedSince(employee.getEmployedSince());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setInsuranceNumber(employee.getInsuranceNumber());

        /********************* convert addresses ******************/
        if (!convertedClasses.contains(Address.class) && employee.getAddress() != null) {
            dto.setAddress(convert(employee.getAddress(), convertedClasses));
        }


        if (employee.getPermission() == Permission.ROLE_ADMINISTRATOR)
            dto.setRole(EmployeeRoles.ADMIN);
        else
            dto.setRole(EmployeeRoles.STANDARD);

        if (employee.getGender() == Gender.MALE)
            dto.setGender(GenderDto.MALE);
        else
            dto.setGender(GenderDto.FEMALE);

        if (employee.getLoginFailcount() > 5)
            dto.setBlocked(true);
        else
            dto.setBlocked(false);

        return dto;
    }

    /**
     * converts an address entity to an address dto
     *
     * @param address          the adress entity
     * @param convertedClasses a list that contains already converted classes
     * @return the address dto
     */

    public static AddressDto convert(Address address, List<Class<?>> convertedClasses) {
        convertedClasses.add(Address.class);
        AddressDto dto = new AddressDto();
        dto.setCity(address.getCity());
        dto.setStreet(address.getStreet());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    /**
     * converts a list of customer entities to customer dtos
     *
     * @param customers        the list of customer entities
     * @param convertedClasses a list that contains already converted classes
     * @return the list of customer DTOs
     */
    public static List<CustomerDto> convertCustomers(List<Customer> customers, List<Class<?>> convertedClasses) {
        List<Class<?>> saveClasses = new ArrayList<>();
        saveClasses.addAll(convertedClasses);
        List<CustomerDto> ret = new ArrayList<>();
        if (null != customers) {
            for (Customer c : customers) {
                convertedClasses.clear();
                convertedClasses.addAll(saveClasses);
                CustomerDto dto = convert(c, convertedClasses);
                ret.add(dto);
            }
        }
        return ret;
    }

    /**
     * Converts the page count to DTO
     *
     * @param pageCount number of possible pages
     * @return converted DTO
     */
    public static PageCountDto convertPageCount(Integer pageCount) {
        PageCountDto pageCountDto = new PageCountDto();
        pageCountDto.setPageCount(pageCount);
        return pageCountDto;
    }

    /**
     * Converts an customer entity to an customer DTO
     *
     * @param customer         the customer entity
     * @param convertedClasses a list that contains already converted classes
     * @return the customer DTO
     */
    public static CustomerDto convert(Customer customer, List<Class<?>> convertedClasses) {
        convertedClasses.add(Customer.class);
        CustomerDto dto = new CustomerDto();
        dto.setFirstName(customer.getFirstname());
        dto.setLastName(customer.getLastname());
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setDateOfBirth(customer.getDateOfBirth());

        /********************* convert addresses ******************/
        if (!convertedClasses.contains(Address.class) && customer.getAddress() != null) {
            dto.setAddress(convert(customer.getAddress(), convertedClasses));
        }

        if (customer.getCustomerStatus() == CustomerStatus.DELETED)
            dto.setCustomerStatus(CustomerStatusDto.DELETED);
        else
            dto.setCustomerStatus(CustomerStatusDto.VALID);

        if (customer.getGender() == Gender.MALE)
            dto.setGender(GenderDto.MALE);
        else
            dto.setGender(GenderDto.FEMALE);

        return dto;
    }

}
