package at.ac.tuwien.inso.ticketline.server.util;

import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides static converter methods from DTOs to entities
 *
 * @author Alexander Poschenreithner
 */
public class DtoToEntity {

    /**
     * Converts a news DTO to a news entity.
     *
     * @param newsDto the news DTO
     * @return the news
     */
    public static News convert(NewsDto newsDto) {
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setNewsText(newsDto.getNewsText());
        return news;
    }


    public static Address convert(AddressDto addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setStreet(addressDto.getStreet());
        return address;
    }

    /**
     * Converts a TicketDto to Ticket
     *
     * @param ticketDto to be converted
     * @return ticket object
     */
    public static Ticket convert(TicketDto ticketDto) {
        Ticket t = new Ticket();
        //t.setId(ticketDto.getId());
        t.setDescription(ticketDto.getDescription());
        t.setCancellationReason(ticketDto.getCancellationReason());
        t.setPrice(ticketDto.getPrice());
        t.setReservation((ticketDto.getReservation() != null) ? convert(ticketDto.getReservation()) : null);
        t.setSeat((ticketDto.getSeat() != null) ? convert(ticketDto.getSeat()) : null);
        t.setArea((ticketDto.getArea() != null) ? convert(ticketDto.getArea()) : null);
        t.setShow((ticketDto.getShow() != null) ? convert(ticketDto.getShow()) : null);
        t.setUuid(ticketDto.getUuid());
        t.setValid(ticketDto.getValid());
        return t;
    }

    /**
     * Converts a list of TicketDto to a list of Tickets
     *
     * @param ticketDtos List to be converted
     * @return List with converted Tickets
     */
    public static ArrayList<Ticket> convertTickets(List<TicketDto> ticketDtos) {
        return (null == ticketDtos) ? null : ticketDtos.stream().map(DtoToEntity::convert).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Convert a ShowDto to Show
     *
     * @param showDto to be converted
     * @return Show object
     */
    public static Show convert(ShowDto showDto) {
        Show s = new Show();
        s.setId(showDto.getId());
        s.setCanceled(showDto.getCanceled());
        s.setDateOfPerformance(showDto.getDateOfPerformance());
        s.setPerformance(convert(showDto.getPerformance()));
        s.setRoom(convert(showDto.getRoom()));
        return s;
    }

    /**
     * Converts a list of ShowDto to a list of Shows
     *
     * @param showDtos List to be converted
     * @return List with converted Shows
     */
    public static ArrayList<Show> convertShows(List<ShowDto> showDtos) {
        return (null == showDtos) ? null : showDtos.stream().map(DtoToEntity::convert).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Artist convert(ArtistDto artistDto) {
        Artist a = new Artist();
        a.setId(artistDto.getId());
        a.setFirstname(artistDto.getFirstname());
        a.setLastname(artistDto.getLastname());
        a.setDescription(artistDto.getDescription());
        a.setParticipations(convertParticipations(artistDto.getParticipations()));
        return a;
    }

    public static Participation convert(ParticipationDto participationDto) {
        Participation p = new Participation();
        //p.setId(participationDto.getId());
        p.setDescription(participationDto.getDescription());
        p.setArtist(convert(participationDto.getArtist()));
        p.setArtistRole(participationDto.getArtistRole());
        p.setPerformance(convert(participationDto.getPerformance()));
        return p;
    }

    /**
     * Converts a list of ParticipationDto to a list of Participations
     *
     * @param participationDtos List to be converted
     * @return List with converted Participation
     */
    public static ArrayList<Participation> convertParticipations(List<ParticipationDto> participationDtos) {
        return (null == participationDtos) ? null : participationDtos.stream().map(DtoToEntity::convert).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Performance convert(PerformanceDto performanceDto) {
        if (null == performanceDto) return null;

        Performance p = new Performance();
        p.setId(performanceDto.getId());
        p.setDescription(performanceDto.getDescription());
        p.setDuration(performanceDto.getDuration());
        p.setPerformanceType(convert(performanceDto.getPerformanceType()));
        p.setName(performanceDto.getName());
        p.setParticipations(convertParticipations(performanceDto.getParticipations()));
        p.setShows(convertShows(performanceDto.getShows()));
        return p;
    }

    /**
     * Converts a LocationDto to Location
     *
     * @param locationDto to be converted
     * @return Location Object
     */
    public static Location convert(LocationDto locationDto) {
        Location l = new Location();
        l.setId(locationDto.getId());
        l.setName(locationDto.getName());
        l.setId(locationDto.getId());
        l.setAddress(convert(locationDto.getAddress()));
        l.setOwner(locationDto.getOwner());
        l.setRooms(convert(locationDto.getRooms()));
        return l;
    }

    /**
     * Convert a RoomDto to Room
     *
     * @param roomDto to be converted
     * @return Room object
     */
    public static Room convert(RoomDto roomDto) {
        Room r = new Room();
        r.setId(roomDto.getId());
        r.setName(roomDto.getName());
        r.setDescription(roomDto.getDescription());
        r.setLocation(convert(roomDto.getLocation()));
        r.setSeatChoice(roomDto.getSeatChoice());
        return r;
    }

    /**
     * Converts a list of RoomDtos to a list of Rooms
     *
     * @param rooms List to be converted
     * @return List with converted Rooms
     */
    public static ArrayList<Room> convert(List<RoomDto> rooms) {
        return (null == rooms) ? null : rooms.stream().map(DtoToEntity::convert).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Convert a SeatDto to Seat
     *
     * @param seatDto to be converted
     * @return Seat object
     */
    public static Seat convert(SeatDto seatDto) {
        Seat s = new Seat();
        s.setDescription(seatDto.getDescription());
        s.setName(seatDto.getName());
        s.setOrder(seatDto.getOrder());
        //s.setRow(convert(seatDto.get));
        return s;
    }

    /**
     * Convert an AreaDto to Area
     *
     * @param areaDto to be converted
     * @return area entity
     */
    public static Area convert(AreaDto areaDto) {
        Area a = new Area();

        //a.setId(areaDto.getId());
        a.setPrice(areaDto.getPrice());
        a.setTicketAmount(areaDto.getTicketAmount());
        a.setRoom(convert(areaDto.getRoom()));
        if (areaDto.getAreaType().name().equals("BARRIER_FREE")) {
            a.setAreaType(AreaType.BARRIER_FREE);
        } else if (areaDto.getAreaType().name().equals("VIP")) {
            a.setAreaType(AreaType.VIP);
        } else if (areaDto.getAreaType().name().equals("STANCE")) {
            a.setAreaType(AreaType.STANCE);
        } else if (areaDto.getAreaType().name().equals("FREE_SEATING_CHOICE")) {
            a.setAreaType(AreaType.FREE_SEATING_CHOICE);
        }
        return a;
    }

    /**
     * Convert a ReservationDto to Reservation
     *
     * @param reservationDto to be converted
     * @return Reservation object
     */
    public static Reservation convert(ReservationDto reservationDto) {
        Reservation r = new Reservation();
        r.setCustomer((reservationDto.getCustomer() != null) ? convert(reservationDto.getCustomer()) : null);
        r.setEmployee((reservationDto.getEmployee() != null) ? convert(reservationDto.getEmployee()) : null);
        r.setTickets((reservationDto.getTickets() != null) ? convertTickets(reservationDto.getTickets()) : null);
        return r;
    }


    public static Employee convert(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstname(employeeDto.getFirstName());
        employee.setLastname(employeeDto.getLastName());
        employee.setUsername(employeeDto.getUsername());
        employee.setPasswordHash(employeeDto.getPasswordHash());
        employee.setEmployedSince(employeeDto.getEmployedSince());
        employee.setInsuranceNumber(employeeDto.getInsuranceNumber());

        if (employeeDto.getAddress() != null)
            employee.setAddress(convert(employeeDto.getAddress()));

        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setEmail(employeeDto.getEmail());

        if (employeeDto.getGender() == GenderDto.MALE)
            employee.setGender(Gender.MALE);
        else
            employee.setGender(Gender.FEMALE);

        if (employeeDto.isBlocked())
            employee.setLoginFailcount(6);
        else
            employee.setLoginFailcount(0);  // set Failcount to 0, also when user has a Failcount of 3 or so (but not blocked)

        if (employeeDto.getRole() == EmployeeRoles.ADMIN)
            employee.setPermission(Permission.ROLE_ADMINISTRATOR);
        else
            employee.setPermission(Permission.ROLE_USER);

        return employee;
    }

    public static Customer convert(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstname(customerDto.getFirstName());
        customer.setLastname(customerDto.getLastName());

        if (customerDto.getAddress() != null)
            customer.setAddress(convert(customerDto.getAddress()));

        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setEmail(customerDto.getEmail());

        if (customerDto.getGender() == GenderDto.MALE)
            customer.setGender(Gender.MALE);
        else
            customer.setGender(Gender.FEMALE);

        if (customerDto.getCustomerStatus() == CustomerStatusDto.DELETED)
            customer.setCustomerStatus(CustomerStatus.DELETED);
        else
            customer.setCustomerStatus(CustomerStatus.VALID);


        return customer;
    }

    public static PerformanceType convert(PerformanceTypeDto performanceTypeDto) {
        if ("CONCERT".equals(performanceTypeDto.name()))
            return PerformanceType.CONCERT;
        if ("FESTIVAL".equals(performanceTypeDto.name()))
            return PerformanceType.FESTIVAL;
        if ("MOVIE".equals(performanceTypeDto.name()))
            return PerformanceType.MOVIE;
        if ("MUSICAL".equals(performanceTypeDto.name()))
            return PerformanceType.MUSICAL;
        if ("OPERA".equals(performanceTypeDto.name()))
            return PerformanceType.OPERA;
        if ("THEATER".equals(performanceTypeDto.name()))
            return PerformanceType.THEATER;

        return null;
    }


    /**
     * Converts a TicketPublishDto to Ticket
     * IMPORTANT: Related Objects like Seat, Reservation aso. are ignored
     *
     * @param ticketDto to be converted
     * @return Ticket without related objects
     */
    public static Ticket convert(TicketPublishDto ticketDto) {
        Ticket t = new Ticket();
        t.setDescription(ticketDto.getDescription());
        t.setPrice(ticketDto.getPrice());
        return t;
    }

    /**
     * Converts a ReservationPublishDto to Reservation
     * IMPORTANT: Related Objects like Seat, Reservation aso. are ignored
     *
     * @param reservationDto to be converted
     * @return Reservation without related objects
     */
    public static Reservation convert(ReservationPublishDto reservationDto) {
        Reservation r = new Reservation();
        return r;
    }

    /**
     * @param receiptDto to be converted
     * @param tickets    to be set in the receipt
     * @return receipt object
     */
    public static Receipt convert(ReceiptDto receiptDto, HashMap<Integer, Ticket> tickets) {
        Receipt r = new Receipt();
        r.setCustomer(convert(receiptDto.getCustomerDto()));

        if (receiptDto.getMethodOfPaymentDto().getStripeDto() != null) {
            StripeDto stripeDto = receiptDto.getMethodOfPaymentDto().getStripeDto();
            StripePayment stripe = new StripePayment((stripeDto.getToken()));
            r.setMethodOfPayment(stripe);

        } else {
            r.setMethodOfPayment(new Cash());
        }


        r.setPerformanceName(receiptDto.getPerformanceName());

        List<ReceiptEntry> receiptEntries = new ArrayList<>();

        for (ReceiptEntryDto receiptEntryDto : receiptDto.getReceiptEntryDtos()) {
            receiptEntries.add(convert(receiptEntryDto, tickets.get(receiptEntryDto.getTicketId())));
        }
        for (ReceiptEntry receiptEntry : receiptEntries) {
            receiptEntry.setReceipt(r);
        }
        r.setReceiptEntries(receiptEntries);
        r.setTransactionDate(receiptDto.getTransactionDate());

        if (receiptDto.getTransactionState().equals(TransactionStateDto.CANCELED))
            r.setTransactionState(TransactionState.CANCELED);
        else if (receiptDto.getTransactionState().equals(TransactionStateDto.IN_PROCESS))
            r.setTransactionState(TransactionState.IN_PROCESS);
        else if (receiptDto.getTransactionState().equals(TransactionStateDto.ORDERED))
            r.setTransactionState(TransactionState.ORDERED);
        else if (receiptDto.getTransactionState().equals(TransactionStateDto.PAID))
            r.setTransactionState(TransactionState.PAID);
        else if (receiptDto.getTransactionState().equals(TransactionStateDto.CANCELLED_POSITIONS))
            r.setTransactionState(TransactionState.CANCELLED_POSITIONS);


        return r;
    }

    /**
     * Convert a ReceiptEntryDto into ReceiptEntry
     *
     * @param receiptEntryDto to be converted
     * @return ReceiptEntry object
     */
    private static ReceiptEntry convert(ReceiptEntryDto receiptEntryDto, Ticket ticket) {
        ReceiptEntry r = new ReceiptEntry();
        r.setPosition(receiptEntryDto.getPosition());
        r.setAmount(receiptEntryDto.getAmount());
        r.setUnitPrice(receiptEntryDto.getUnitPrice());
        r.setTicket(ticket);
        return r;
    }
}
