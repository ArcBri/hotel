Hotel Reservation System (HoRS) made for IS2103 Enterprise System Server-Side design and development module.

All software elements constituting the HoRS are to be developed in Java using the Java Platform, Enterprise Edition (Java EE). In particular, Enterprise JavaBeans (EJB) and Java Persistence API (JPA) technologies are to be used in conjunction with a suitable Relational Database Management System (RDBMS) such as MySQL. Only Command-line Interface (CLI) client applications are utilised.

Business Use Cases:
S/N Use Case        Description/Business Rules
1 Employee Login    • Allows an employee to login to the system and assume the
                      preconfigured user role.
                    • May only be performed if employee is not currently login to
                      the system.
                    • Employee must be currently login to the system to perform
                      all other use cases.
                    • A default system administrator account should be created as
                      part of data initialisation.
                      
2 Employee Logout   • Logout the employee.
                    • May only be performed if employee is currently login to the
                    system.
                    
3 Create New Employee • Creates a new employee record with the required
                    credentials and user role (corresponds to use case actor).
                    • It is not necessary to support update and delete of employee
                    records.
                    
4 View All Employees • Display a list of all employee records in the system.

5 Create New Partner • Creates a new partner record with the required credentials.
                      • All employees of the same partner organisation are assumed
                      to login to HoRS remotely using a single account.
                      • It is not necessary to support update and delete of partner
                      records.
                      
6 View All Partners   • Display a list of all partner records in the system

7 Create New Room Type • Creates a new room type record with basic attributes such
                      as name, description, size, bed, capacity and amenities.
                      
8 View Room Type Details • View the details of a particular room type record.

9 Update Room Type • Update the details of a particular room type record.

10 Delete Room Type • Delete a particular room type record.
                    • A room type record can only be deleted if it is not used.
                    • Otherwise, it should be marked as disabled and no new
                      room should be created for disabled room type.
                      
11 View All Room Types • Display a list of all room type records in the system.

12 Create New Room • Creates a new room record with basic attributes such as
                    room type, room number and room status.
                    
13 Update Room • Update the details of a particular room record.
                • Room status is also updated via this use case.
                
14 Delete Room • Delete a particular room record.
                • A room record can only be deleted if it is not used.
                • Otherwise, it should be marked as disabled, excluded from
                the hotel room inventory for that particular room type and
                  should not be allocated to a new reservation.
                  
15 View All Rooms • Display a list of all room records in the system.

16 View Room Allocation Exception Report
                    • Generate a report showing two types of room allocation
                    exception.
                  • First type is no available room for reserved room type but
                  upgrade to next higher room type is available – A room is
                  automatically allocated by the system.
                  • Second type is no available room for reserved room type
                  and no upgrade to next higher room type is available – No
                  room is automatically allocated by the system.
                  
17 Create New Room Rate • Creates a new room rate record with basic attributes such as
                        name, room type, rate type, rate per night, validity period (if
                        applicable)
                        
18 View Room Rate Details • View the details of a particular room rate record.

19 Update Room Rate • Update the details of a particular room rate record.

20 Delete Room Rate • Delete a particular room rate record.
                    • A room rate record can only be deleted if it is not used.
                    • Otherwise, it should be marked as disabled and new
                    reservation should not be made with the disabled room rate.
                    
21 View All Room Rates • Display a list of all room rate records in the system.

22 Allocate Room to Current Day Reservations
                      • Retrieve a list of all reservations for check-in on the current
                      date and allocate an available room for the reserved room
                      type.
                      • If an available room for the reserved room type is not
                      available, raise an exception in the exception report (see use
                      case 16).
                      
23 Walk-in Search Room • Search an available room across all room types offered by
                        the hotel according to the check-in date and check-out date.
                        • The reservation amount should be calculated based on the
                        prevailing published rate of that particular room type.
                        • The system needs to ensure that the hotel has sufficient
                        room inventory to fulfil the new reservation.
                        
24 Walk-in Reserve Room • Reserve a room offered in the search results (see use case
23).
                      • It is possible for a walk-in guest to reserve more than one
                      room.
                      • A walk-in guest does not need to be a registered guest of the
                      hotel.
                      
25 Check-in Guest • Check-in a guest by informing him/her of the allocated room(s).
                    • Room allocation exception needs to be handled manually.
                    
26 Check-out Guest • Check-out a guest to indicate the end of his/her visit to the
                        hotel.
