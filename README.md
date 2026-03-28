# DESIGN NOTES
## No. 1 
Foreign Keys are nullable by default such that things can exist without
references to other fields. This was done to simplify working with raw SQL.
I honestly don't know any better way to handle things right now. --enrico

## No. 2
The `TimeSlotBean` class uses LocalTime on the start and end fields
to represent an apppointment slot at a clinic, but the JDBC requires a Time value,
and MySQL uses a TIME datatype formatted as "hhh:mm:ss". 

To use, convert LocalTime to Time like so: 
```java
// create a LocalTime instance. Note that
// the TimeSlotBean does not currently check// to see if the start time comes before the
// end time or vice versa.
LocalTime start = LocalTime.parse("09:00");

// convert to time for use in prepared statements to insert into database ...
Time forQuery = Time.valueOf(start);

// ...and back to local time when fetching from database
forQuery.toLocalTime();
```
