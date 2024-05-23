# Patika Tourism Agency System

![Vacation](secreenshots/vacation.jpg)

In this tourism agency system application developed within the scope of the Patika Full Stack Web Development Bootcamp, basic level hotel management, season management, room management, and reservation processes can be performed.
## Technologies
* Java SE 8
* Swing Framework
* PostgreSQL 16
* IntelliJ IDEA Community Edition 2023.3.3 

## Installation

1. Make sure that at least JDK 1.8 is installed on your system.
```bash
java --version
```
2. To clone the project repository, run the following command in the terminal:
```bash
   git clone https://github.com/ikaraozdemir/turizmAgency.git
   ```
3. Follow these steps to import the PostgreSQL backup file located inside the project folder:
   *  Navigate to the project folder in the terminal.
   * Import the PostgreSQL backup file:
   ```bash
   psql -U username -d database_name -f backup_file.sql
   ```
4. Before establishing the database connection, update the username and password in database.properties.

## Secreenshots
![login](secreenshots/login.png) ![user_table](secreenshots/userView.png)
![new_user](secreenshots/addNewUser.png) ![hotel_table](secreenshots/hotelTable.png)
![new_hotel](secreenshots/hotelAdd.png) ![hotel_update](secreenshots/hotelUpdate.png)
![season_table](secreenshots/seasonTable.png) ![room_table](secreenshots/roomTable.png) 
![new_room](secreenshots/roomAdd.png) ![filtered_rooms_table](secreenshots/filteredRoomsTable.png)
![reservation_table](secreenshots/reservationTable.png) ![new_reservation](secreenshots/reservationAdd.png)


The link to the video where I briefly explain the project: https://www.youtube.com/watch?v=_3v1Rv55Rl8

------------------------------------------------------------------------------------------------------
